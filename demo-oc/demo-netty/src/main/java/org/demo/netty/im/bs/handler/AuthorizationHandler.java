package org.demo.netty.im.bs.handler;

import org.demo.netty.im.bs.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.nio.tcp.PacketDecoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
	private certificationc
	
	

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
	}


}
