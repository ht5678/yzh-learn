package org.demo.netty.im.cs.handler;

import org.demo.netty.domain.Packet;
import org.demo.netty.exception.ExceptionListener;
import org.demo.netty.im.cs.chain.PacketChain;
import org.demo.netty.im.cs.heart.HeartDetector;
import org.demo.netty.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月22日 下午4:37:48
 */
public class SocketHandler extends SimpleChannelInboundHandler<Packet>{

	private static Logger log = LoggerFactory.getLogger(SocketHandler.class);
	
	private ExceptionListener exceptionListener;
	
	
	/**
	 * 
	 * @param exceptionListener
	 */
	public SocketHandler(ExceptionListener exceptionListener) {
		this.exceptionListener = exceptionListener;
	}
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
		try {
			Session session = null;
			switch (packet.getType()) {
			case PING:
				HeartDetector.pongCs(ctx.channel());
				break;
			case MESSAGE:
				session = ctx.channel().attr(Session.CLIENT_SESSION).get();
				PacketChain.dispatcher
				break;

			default:
				break;
			}
		}finally {
			ReferenceCountUtil.release(packet);
		}
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(!exceptionListener.exceptionCaught(ctx, cause)) {
			super.exceptionCaught(ctx, cause);
		}
	}

	
	
}