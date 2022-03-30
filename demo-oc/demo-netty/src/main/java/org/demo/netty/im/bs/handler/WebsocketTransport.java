package org.demo.netty.im.bs.handler;

import org.demo.netty.domain.Packet;
import org.demo.netty.domain.PacketType;
import org.demo.netty.domain.Transport;
import org.demo.netty.im.bs.config.Configuration;
import org.demo.netty.im.cs.heart.HeartDetector;
import org.demo.netty.session.LocalCustomerSession;
import org.demo.netty.session.Session;
import org.demo.netty.session.WaiterSession;
import org.demo.netty.util.PacketDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.nio.tcp.PacketEncoder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCountUtil;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月30日 下午3:31:43
 */
public class WebsocketTransport extends ChannelInboundHandlerAdapter{

	private final static Logger log = LoggerFactory.getLogger(WebsocketTransport.class);
	
	private static final String HEART = "heart";
	private final static Transport TRANSPORT = Transport.WEBSOCKET;
	
	private final boolean isSsl;
	private final Configuration config;
	private PacketDecoder decoder;
	private PacketEncoder encoder;
	
	
	/**
	 * 
	 */
	public WebsocketTransport(Configuration config , boolean isSsl , PacketEncoder encoder , PacketDecoder decoder) {
		this.isSsl = isSsl;
		this.config = config;
		this.encoder = encoder;
		this.decoder = decoder;
	}


	
	
	/**
	 * 
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
		Channel channel = ctx.channel();
		Session session = getChannelSession(channel);
		if(null == session) {
			ReferenceCountUtil.release(obj);
			ctx.channel().close();
			throw new IllegalAccessError("无效的客户端连接,服务器关闭和当前客户端连接");
		}
		
		//
		if(obj instanceof CloseWebSocketFrame) {
			if(session instanceof LocalCustomerSession) {
				log.info("客户主动关闭当前会话 Uid : {}" , session.getUid());
			}else {
				log.info("客服退出登录Uid : {}" , session.getUid());
			}
			ReferenceCountUtil.release(obj);
		}else if(obj instanceof TextWebSocketFrame) {
			TextWebSocketFrame frame = (TextWebSocketFrame)obj;
			try {
				resolve
			} finally {
				
			}
		}
	}
	
	
	
	/**
	 * 处理消息
	 */
	private void resolverTextWebSocketFrame(Session session , Channel channel , String message) {
		Packet packet = null;
		try {
			packet = decoder.decodePackets(message, Packet.class);
			if(packet.getType() == PacketType.PING) {
				HeartDetector.pongBs(session);
				return;
			}
			
			//客服消息
			if(session instanceof WaiterSession) {
				log.info("客服消息 packet : {}" , packet);
				WaiterSession waiterSession = (WaiterSession)session;
				switch (packet.getType()) {
				case TRANSFER:
					
					break;

				default:
					break;
				}
			}
		}catch(Exception e) {
			log.warn("packet : {}  解析数据发生错误" , packet);
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 获取当前session
	 * @return
	 */
	private Session getChannelSession(Channel channel) {
		return channel.attr(Session.CLIENT_SESSION).get();
	}
	
	
}
