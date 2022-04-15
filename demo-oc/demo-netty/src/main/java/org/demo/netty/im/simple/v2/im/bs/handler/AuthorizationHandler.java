package org.demo.netty.im.simple.v2.im.bs.handler;

import java.util.List;
import java.util.Map;

import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.PacketType;
import org.demo.netty.im.fake.domain.Transport;
import org.demo.netty.im.fake.exception.BSAuthorizeException;
import org.demo.netty.im.fake.im.bs.config.Configuration;
import org.demo.netty.im.fake.im.bs.handler.EncoderHandler;
import org.demo.netty.im.fake.scheduler.SchedulerKey;
import org.demo.netty.im.fake.scheduler.SchedulerKey.SchedulerType;
import org.demo.netty.im.fake.util.HttpRequests;
import org.demo.netty.im.fake.util.HttpResponses;
import org.demo.netty.im.fake.util.PacketDecoder;
import org.demo.netty.im.simple.v2.im.bs.oauth.CertificationCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.ReferenceCountUtil;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午10:29:35
 */
public class AuthorizationHandler extends ChannelInboundHandlerAdapter{
	
	
	private final static Logger log = LoggerFactory.getLogger(AuthorizationHandler.class);
	public final static String WEBSOCKET_IO = "/ws.io";
	public final static String POLLING_IO = "/poll.io";
	
	private final static String PARAM_PACKET = "packet";
	
	private final Configuration config;
	private final PacketDecoder decoder;
	private CertificationCenter certificationCenter;
	
	
	
	/**
	 * 
	 */
	public AuthorizationHandler(Configuration config , PacketDecoder decoder) {
		this.config = config;
		this.decoder = decoder;
		certificationCenter = new CertificationCenter(config);
	}
	

	
	/**
	 * 
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object object) throws Exception {
		//处理http poll 和 web socket 请求
		Channel channel = ctx.channel();
//		SchedulerKey key = new SchedulerKey(SchedulerType.PING_TIMEOUT , channel);
//		OCIMServer.getInst().getScheduler().cancel(key);
		
		//
		if(object instanceof FullHttpRequest) {
			Packet packet = null;
			FullHttpRequest req = (FullHttpRequest)object;
			try {
				//
				QueryStringDecoder queryDecoder = new QueryStringDecoder(req.uri());
				String origin = req.headers().get(HttpHeaderNames.ORIGIN);
				channel.attr(EncoderHandler.ORIGIN).set(origin);
				String userAgent = req.headers().get(HttpHeaderNames.USER_AGENT);
				channel.attr(EncoderHandler.USER_AGENT).set(userAgent);
				
				//
				Map<String, List<String>> parameters = queryDecoder.parameters();
				String path = queryDecoder.path();
				
				//
				if(config.isAllowCustomRequests()) {
					String msg = HttpRequests.getRequestParam(parameters, PARAM_PACKET);
					if(null != msg) {
						packet = decoder.decodePackets(msg, Packet.class);
						if(path.startsWith(WEBSOCKET_IO) || path.startsWith(POLLING_IO)) {
							if(packet.getType() == PacketType.AUTH) {
								Transport ts = packet.getTs();
								if(ts == Transport.POLLING) {
									certificationCenter.handlerHttp(ctx, packet, req);
								}else if(ts == Transport.WEBSOCKET) {
									certificationCenter.handlerWebSocket(ctx, packet, req);
								}else {
									throw new IllegalArgumentException("不支持当前的传输类型 : "+ts);
								}
							}else {
								ctx.fireChannelRead(packet);
								req.release();
							}
						}else {
							throw new IllegalArgumentException("不支持其他资源类型访问"+path);
						}
					}else {
						throw new IllegalArgumentException("无法解析当前请求 path : {"+path+"} , parameters  : { "+parameters+" }");
					}
				}
				
			}catch(BSAuthorizeException e) {
				log.warn("IP : {} , 认证失败 , {}" , req.headers().get("X-Real_IP") , e.getMessage());
				HttpResponses.sendUnauthorizedError(channel);
				if(null != req) {
					ReferenceCountUtil.release(req);
				}
			}catch(Exception e) {
				log.error("IP：{} Packet: {} B/S处理消息失败：{}", req.headers().get("X-Real-IP"),
						packet, e.getMessage());
				HttpResponses.sendUnauthorizedError(channel);
				if (null != req) {
					ReferenceCountUtil.release(req);
				}
			}
		}else {
			//transport websocket
			ctx.fireChannelRead(object);
		}
	}
	
	
	
	
	/**
	 * 
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		
		//如果处理事件超过FirstDataTimeout , 则关闭Channel
		Channel channel = ctx.channel();
		SchedulerKey key = new SchedulerKey(SchedulerType.PING_TIMEOUT , channel);
//		OCIMServer.getInst().getScheduler().scheduler(key, new Runnable() {
//			
//			@Override
//			public void run() {
//				log.error("当前请求处理业务逻辑 , 5s未完成 , 关闭通讯");
//				channel.close();
//			}
//		}, config.getFirstDataTimeout() , TimeUnit.MILLISECONDS);
	}


}
