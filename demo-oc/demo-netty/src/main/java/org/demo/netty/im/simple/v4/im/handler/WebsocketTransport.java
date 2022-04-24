package org.demo.netty.im.simple.v4.im.handler;

import java.util.concurrent.TimeUnit;

import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.PacketType;
import org.demo.netty.im.fake.domain.Transport;
import org.demo.netty.im.fake.domain.Waiter;
import org.demo.netty.im.fake.im.auth.WaiterAuthCoder;
import org.demo.netty.im.fake.im.bs.config.Configuration;
import org.demo.netty.im.fake.im.chain.BindChain;
import org.demo.netty.im.fake.im.chain.CloseChatChain;
import org.demo.netty.im.fake.im.chain.ReceptionChain;
import org.demo.netty.im.fake.im.chain.RevocationChain;
import org.demo.netty.im.fake.im.chain.TransferChain;
import org.demo.netty.im.fake.im.chain.WaiterStatusChain;
import org.demo.netty.im.fake.im.coder.PacketEncoder;
import org.demo.netty.im.fake.im.cs.heart.HeartDetector;
import org.demo.netty.im.fake.session.CustomerSession;
import org.demo.netty.im.fake.session.Session;
import org.demo.netty.im.fake.session.WaiterSession;
import org.demo.netty.im.fake.util.PacketDecoder;
import org.demo.netty.im.simple.v4.im.cs.chain.PacketChain;
import org.demo.netty.im.simple.v4.im.initializer.BsChannelInitializer;
import org.demo.netty.im.simple.v4.im.session.LocalCustomerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月13日 下午6:17:11
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
		
		System.out.println("WebsocketTransport"+obj);
//		ReferenceCountUtil.release(obj);
//		ctx.fireChannelRead(obj);
		
		Channel channel = ctx.channel();
		Session session = getChannelSession(channel);//从local  cache中的channel获取 session
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
				resolverTextWebSocketFrame(session, channel, frame.text());
			} finally {
				frame.release();
			}
		}else if(obj instanceof FullHttpRequest) {
			FullHttpRequest request = (FullHttpRequest)obj;
			try {
				if(session.getTransport() == TRANSPORT) {
					upgradeWebSocket(ctx, session, request);
				}else {
					ctx.channel().close();
					throw new IllegalAccessException("不支持的通讯协议: " + session.getTransport());
				}
			}finally {
				request.release();
			}
		}else {
			ctx.fireChannelRead(obj);
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
					TransferChain.transfer(packet);
					break;
				case CLOSE_CHAT:
					CloseChatChain.closeDispatcher(packet);
					break;
				case RECEPTION:
					ReceptionChain.directReception(waiterSession, packet);
					break;
				case CHANGE_STATUS:
					WaiterStatusChain.changeStatus(waiterSession, packet);
					break;
				case BIND:
					BindChain.waiterBind(waiterSession, channel, encoder);
					break;
				case REVOCATION:
					RevocationChain.revocation(waiterSession, packet);
					break;
				default:
					PacketChain.dispatcherWaiterPacket(waiterSession, packet);
					break;
				}
			}else if(session instanceof CustomerSession) {
				log.info("客户消息 packet : {}" , packet);
				CustomerSession customerSession = (CustomerSession)session;
				PacketChain.dispatcherCustomerPacket(customerSession, packet);
			}else {
				log.error("无法分发的消息 : {}" , packet);
			}
		}catch(Exception e) {
			log.warn("packet : {}  解析数据发生错误" , packet);
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 * 唯一的一次http请求。
	 * 该方法用于处理websocket握手请求
	 * 
	 * 
	 * *1*
	 * netty的websocket协议是在HTTP协议基础之上完成的，要使用WebSocket协议，需要将HTTP请求头中添加Upgrade:WebSocket
	 * 
	 * *2*
	 * WebSocket相关的编解码是在handshaker.handshake(ctx.channel(), req);中添加进去的。handshaker是WebSocketServerHandshaker的对象。
	 * handshake方法中建握手响应消息返回给客户端。同时将WebSocket相关编解码类添加到ChannelPipeline中。
	 * 
	 * 
	 * http升级  web socket
	 */
	private void upgradeWebSocket(ChannelHandlerContext ctx , Session session , FullHttpRequest req) {
		//如果HTTP解码失败，返回异常。要求Upgrade为websocket，过滤掉get/Post
		log.info("web socket upgrade header : "+req.headers().get("Upgrade"));
		System.out.println("web socket upgrade header : "+req.headers().get("Upgrade"));
		final Channel channel = ctx.channel();
		// 构造握手响应返回，本机测试
		WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(getWebsocketUrl(req) , null , true , config.getMaxFramePayloadLength());
		//通过工厂来创建WebSocketServerHandshaker实例
		WebSocketServerHandshaker handshake = factory.newHandshaker(req);
		
		
		 /*
        通过WebSocketServerHandshaker来构建握手响应消息返回给客户端。
        同时将WebSocket相关编解码类添加到ChannelPipeline中，该功能需要阅读handshake的源码。
         */
		if(handshake != null) {
			handshake.handshake(channel, req , getResponseHeaders(session) , ctx.newPromise()).addListener((ChannelFutureListener) future -> {
					if(future.isSuccess()) {
						channel.pipeline().addFirst(HEART , new IdleStateHandler(60, 0, 0 , TimeUnit.SECONDS));
						channel.pipeline().addBefore(BsChannelInitializer.WEB_SOCKET_TRANSPORT, 
								BsChannelInitializer.WEB_SOCKET_AGGREGATOR, 
								new WebSocketFrameAggregator(config.getMaxFramePayloadLength()));
					}else {
						log.error("uid : {}  握手失败 : {} " , session.getUid() , future.cause());
					}
			});
		}else {//若header upgrade不是websocket方式，则创建BAD_REQUEST（400）的req，返回给客户端
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel);
		}
		
	}
	
	
	/**
	 * 获取协议
	 * @return
	 */
	private String getWebsocketUrl(HttpRequest req) {
		String protocol = "ws://";
		if(isSsl) {
			protocol = "wss://";
		}
		return protocol+req.headers().get(HttpHeaderNames.HOST) + req.uri();
	}
	
	
	
	/**
	 * 设置 cookies
	 * @return
	 */
	private HttpHeaders getResponseHeaders(Session session) {
		if(session instanceof WaiterSession) {
			WaiterSession waiterSession = (WaiterSession)session;
			Waiter waiter = waiterSession.getWaiter();
			String sign = WaiterAuthCoder.encode(waiter);
			DefaultCookie cookie = new DefaultCookie(WaiterAuthCoder.SERVER_COOKIE_NAME, sign);
			cookie.setPath("/");
			cookie.setHttpOnly(false);
			cookie.setSecure(false);
			final DefaultHttpHeaders httpHeaders = new DefaultHttpHeaders();
			httpHeaders.add(HttpHeaderNames.SET_COOKIE , ServerCookieEncoder.LAX.encode(cookie));
			return httpHeaders;
		}
		return new DefaultHttpHeaders();
	}
	
	
	
	
	/**
	 * 获取当前session
	 * @return
	 */
	private Session getChannelSession(Channel channel) {
		return channel.attr(Session.CLIENT_SESSION).get();
	}
	
	
}
