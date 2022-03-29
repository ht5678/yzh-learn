package org.demo.netty.im.bs.handler;

import java.util.concurrent.TimeUnit;

import org.demo.netty.domain.AddressFrom;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.PacketType;
import org.demo.netty.domain.Transport;
import org.demo.netty.exception.BSAuthorizeException;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.im.bs.config.Configuration;
import org.demo.netty.im.bs.message.OutPacketMessage;
import org.demo.netty.im.bs.message.PollOkMessage;
import org.demo.netty.im.bs.message.PongPacketMessage;
import org.demo.netty.im.cs.chain.PacketChain;
import org.demo.netty.scheduler.SchedulerKey;
import org.demo.netty.scheduler.SchedulerKey.SchedulerType;
import org.demo.netty.session.CustomerSession;
import org.demo.netty.util.HttpResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.ReferenceCountUtil;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午10:08:02
 */
public class PollingTransport extends ChannelInboundHandlerAdapter{

	private Logger log = LoggerFactory.getLogger(PollingTransport.class);
	

	public final static Transport TRANSPORT = Transport.POLLING;
	private final Configuration config;
	
	private final static Long POLL_CONNECT_MILLISECOND = 60 * 1000L;
	
	
	/**
	 * 
	 * @param config
	 */
	public PollingTransport(Configuration config) {
		this.config = config;
	}


	
	
	/**
	 * 
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel channel = ctx.channel();
		if(msg instanceof Packet) {
			Packet packet = (Packet)msg;
			Transport ts = packet.getTs();
			
			if(TRANSPORT == ts) {
				try {
					switch (packet.getType()) {
					case POLL:
						onPoller(channel, packet);
						break;
					case MESSAGE:
						onSender(channel, packet);
						break;
					case PING:
						pong(channel, packet);
						break;

					default:
						break;
					}
				}catch(Exception e) {
					log.error("Http deal message error : {}" , e.getMessage());
					e.printStackTrace();
					HttpResponses.sendBadRequestError(channel);
				}finally {
					ReferenceCountUtil.release(msg);
				}
			}else {
				ctx.fireChannelRead(msg);
			}
		}else {
			ctx.fireChannelRead(msg);
		}
	}
	
	
	/**
	 * 
	 */
	private void onSender(Channel channel , Packet packet) throws Exception {
		AddressFrom from = packet.getFrom();
		CustomerSession customerSession = OCIMServer.getInst().getRoutingTable().getLocalCustomerSession(from.getUid());
		if(customerSession == null) {
			sendError(channel);
			log.error("检测用户 : {} , 会话不存在" , packet.getCid());
			return;
		}
		channel.writeAndFlush(new PollOkMessage());
		PacketChain.dispatcherCustomerPacket(customerSession, packet);
	}
	
	
	
	/**
	 * 
	 */
	private void sendError(Channel channel) {
		HttpResponse resp = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		channel.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
	}
	
	
	
	
	/**
	 * 
	 */
	private void onPoller(Channel channel , Packet packet) throws BSAuthorizeException {
		AddressFrom from = packet.getFrom();
		CustomerSession session = OCIMServer.getInst().getRoutingTable().getLocalCustomerSession(from.getUid());
		if(null == session) {
			throw new BSAuthorizeException("未获取到当前用户状态");
		}
		
		session.bindChannel(channel);
		//保证在重连过程中,消息有缓存
		channel.writeAndFlush(new OutPacketMessage(session));
		SchedulerKey key = new SchedulerKey(SchedulerType.PING_TIMEOUT , channel);
		OCIMServer.getInst().getScheduler().cancel(key);
		OCIMServer.getInst().getScheduler().scheduler(key, () -> channel.writeAndFlush(new PollOkMessage()), POLL_CONNECT_MILLISECOND , TimeUnit.MILLISECONDS);
	}
	
	
	
	/**
	 * 
	 * @param channel
	 * @param packet
	 */
	private void pong(Channel channel, Packet packet) {
		Packet pongPacket = new Packet(PacketType.PONG);
		pongPacket.setFrom(packet.getFrom());
		channel.writeAndFlush(new PongPacketMessage(pongPacket));
	}
	
}
