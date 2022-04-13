package org.demo.netty.im.simple.v2.im.handler;

import org.demo.netty.im.fake.domain.Transport;
import org.demo.netty.im.fake.im.bs.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

//import java.util.concurrent.TimeUnit;
//
//import org.demo.netty.im.fake.domain.AddressFrom;
//import org.demo.netty.im.fake.domain.Packet;
//import org.demo.netty.im.fake.domain.PacketType;
//import org.demo.netty.im.fake.domain.Transport;
//import org.demo.netty.im.fake.exception.BSAuthorizeException;
//import org.demo.netty.im.fake.im.OCIMServer;
//import org.demo.netty.im.fake.im.bs.config.Configuration;
//import org.demo.netty.im.fake.im.bs.message.OutPacketMessage;
//import org.demo.netty.im.fake.im.bs.message.PollOkMessage;
//import org.demo.netty.im.fake.im.bs.message.PongPacketMessage;
//import org.demo.netty.im.fake.im.cs.chain.PacketChain;
//import org.demo.netty.im.fake.scheduler.SchedulerKey;
//import org.demo.netty.im.fake.scheduler.SchedulerKey.SchedulerType;
//import org.demo.netty.im.fake.session.CustomerSession;
//import org.demo.netty.im.fake.util.HttpResponses;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.handler.codec.http.DefaultHttpResponse;
//import io.netty.handler.codec.http.HttpResponse;
//import io.netty.handler.codec.http.HttpResponseStatus;
//import io.netty.handler.codec.http.HttpVersion;
//import io.netty.util.ReferenceCountUtil;

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
		System.out.println("PollingTransport"+msg);
		ReferenceCountUtil.release(msg);
		ctx.fireChannelRead(msg);
		
//		Channel channel = ctx.channel();
//		if(msg instanceof Packet) {
//			Packet packet = (Packet)msg;
//			Transport ts = packet.getTs();
//			
//			if(TRANSPORT == ts) {
//				try {
//					switch (packet.getType()) {
//					case POLL:
//						onPoller(channel, packet);
//						break;
//					case MESSAGE:
//						onSender(channel, packet);
//						break;
//					case PING:
//						pong(channel, packet);
//						break;
//					case CLOSE:
//						close(channel, packet);
//						break;
//					default:
//						HttpResponses.sendBadRequestError(channel);
//						log.warn("客户发送的消息 , 不符合规范 . packet : {} " , packet);
//					}
//				}catch(Exception e) {
//					log.error("Http deal message error : {}" , e.getMessage());
//					e.printStackTrace();
//					HttpResponses.sendBadRequestError(channel);
//				}finally {
//					ReferenceCountUtil.release(msg);
//				}
//			}else {
//				//传递到下一个handler , handler是链式的 , 可能有多个 , 然后原有的消息需要释放, ReferenceCountUtil.release(msg);
//				//A处理器把msg透传给了B，当然了，A不一定直接透传，也可以传递处理过的消息。				
//				ctx.fireChannelRead(msg);
//			}
//		}else {
//			//传递到下一个handler , handler是链式的 , 可能有多个 , 然后原有的消息需要释放, ReferenceCountUtil.release(msg);
//			//A处理器把msg透传给了B，当然了，A不一定直接透传，也可以传递处理过的消息。
//			ctx.fireChannelRead(msg);
//		}
	}
	
	
	
//	/**
//	 * 
//	 * @param channel
//	 * @param packet
//	 */
//	private void close(Channel channel , Packet packet) {
//		AddressFrom from = packet.getFrom();
//		CustomerSession customerSession = OCIMServer.getInst().getRoutingTable().getLocalCustomerSession(from.getUid());
//		if(null == customerSession) {
//			log.info("客户 :{} , 已经离开不需要再次处理" , from.getUid());
//		}else {
//			customerSession.disconnect();
//			channel.close();
//			log.info("客户: {} , Http-close 直接关闭会话" , from.getUid());
//		}
//	}
//	
//	
//	
//	/**
//	 * 
//	 */
//	private void onSender(Channel channel , Packet packet) throws Exception {
//		AddressFrom from = packet.getFrom();
//		CustomerSession customerSession = OCIMServer.getInst().getRoutingTable().getLocalCustomerSession(from.getUid());
//		if(customerSession == null) {
//			sendError(channel);
//			log.error("检测用户 : {} , 会话不存在" , packet.getCid());
//			return;
//		}
//		channel.writeAndFlush(new PollOkMessage());
//		PacketChain.dispatcherCustomerPacket(customerSession, packet);
//	}
//	
//	
//	
//	/**
//	 * 
//	 */
//	private void sendError(Channel channel) {
//		HttpResponse resp = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
//		channel.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
//	}
//	
//	
//	
//	
//	/**
//	 * 
//	 */
//	private void onPoller(Channel channel , Packet packet) throws BSAuthorizeException {
//		AddressFrom from = packet.getFrom();
//		CustomerSession session = OCIMServer.getInst().getRoutingTable().getLocalCustomerSession(from.getUid());
//		if(null == session) {
//			throw new BSAuthorizeException("未获取到当前用户状态");
//		}
//		
//		session.bindChannel(channel);
//		//保证在重连过程中,消息有缓存
//		channel.writeAndFlush(new OutPacketMessage(session));
//		SchedulerKey key = new SchedulerKey(SchedulerType.PING_TIMEOUT , channel);
//		OCIMServer.getInst().getScheduler().cancel(key);
//		OCIMServer.getInst().getScheduler().scheduler(key, () -> channel.writeAndFlush(new PollOkMessage()), POLL_CONNECT_MILLISECOND , TimeUnit.MILLISECONDS);
//	}
//	
//	
//	
//	/**
//	 * 
//	 * @param channel
//	 * @param packet
//	 */
//	private void pong(Channel channel, Packet packet) {
//		Packet pongPacket = new Packet(PacketType.PONG);
//		pongPacket.setFrom(packet.getFrom());
//		channel.writeAndFlush(new PongPacketMessage(pongPacket));
//	}
	
}
