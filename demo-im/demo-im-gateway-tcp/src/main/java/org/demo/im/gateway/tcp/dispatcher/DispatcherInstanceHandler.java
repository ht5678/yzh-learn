package org.demo.im.gateway.tcp.dispatcher;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 作为分发系统通信的客户端的事件处理组件
 * @author yue
 *
 */
public class DispatcherInstanceHandler extends ChannelInboundHandlerAdapter{
	
	/**
	 * 
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//服务端发送过来的消息就是在这里收到的
		String message = (String)msg;
		System.out.println("收到TCP接入系统发送的消息 : "+message);
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
