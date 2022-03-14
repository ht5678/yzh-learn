package org.demo.netty.exception;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午10:01:48
 */
public class DefaultExceptionListener implements ExceptionListener{
	
	private final static Logger log = LoggerFactory.getLogger(DefaultExceptionListener.class);
	
	public DefaultExceptionListener() {}

	@Override
	public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		Channel channel = ctx.channel();
		Session session = getCurrentSession(channel);
		if (null == session) {
			log.warn("未知用户强制关闭连接， 异常信息为:{}.", cause);
			return true;
		}
		log.warn("uid:{}, 当前用户强制关闭连接， 异常信息为：{}.", session.getUid(), cause);
		return true;
	}
	
	@Override
	public boolean exceptionCaught(ChannelHandlerContext ctx, String errorMsg) {
		
		Channel channel = ctx.channel();
		Session session = getCurrentSession(channel);
		if (null == session) {
			return false;
		}
		
		session.disconnect();
		channel.close();
		
		log.error("uid:{}, 连接发生异常:{}.", session.getUid(), errorMsg);
		return true;
	}
	
	private Session getCurrentSession(Channel channel) {
		return channel.attr(Session.CLIENT_SESSION).get();
	}
}
