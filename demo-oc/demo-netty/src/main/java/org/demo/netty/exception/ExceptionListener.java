package org.demo.netty.exception;

import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午9:54:37
 */
public interface ExceptionListener {

	/**
	 * 捕获连接异常
	 * @param ctx
	 * @param cause
	 * @return
	 */
	boolean exceptionCaught(ChannelHandlerContext ctx, Throwable cause);
	
	public boolean exceptionCaught(ChannelHandlerContext ctx, String errorMsg);
	
}