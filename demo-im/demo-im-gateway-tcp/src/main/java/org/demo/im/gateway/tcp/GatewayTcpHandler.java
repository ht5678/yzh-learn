package org.demo.im.gateway.tcp;


import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 
 * @author yue
 *
 */
public class GatewayTcpHandler extends ChannelInboundHandlerAdapter{

	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("跟客户端完成连接: "+ctx.channel().remoteAddress().toString());
	}
	
	
	/**
	 * 跟某个客户端的连接断开了
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}
	
	
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		//使用了StringDecoder后就废弃了
//		ByteBuf messagebuffer = (ByteBuf)msg;
//		System.out.println("接收到一条消息: "+messagebuffer.toString(CharsetUtil.UTF_8));
		
		
		String message = (String)msg;
		System.out.println("接收到一条消息: "+message);
		
		
//		String message = (String)msg;
//		System.out.println("接收到一条消息: "+message);
	}
	
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	
	
}
