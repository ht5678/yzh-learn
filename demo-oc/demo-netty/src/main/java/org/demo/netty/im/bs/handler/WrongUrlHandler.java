package org.demo.netty.im.bs.handler;

import org.demo.netty.util.HttpResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月7日 下午10:20:45
 */
public class WrongUrlHandler extends ChannelInboundHandlerAdapter{

	private static final Logger log = LoggerFactory.getLogger(WrongUrlHandler.class);
	
	
	/**
	 * 
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof FullHttpRequest) {
			FullHttpRequest req = (FullHttpRequest)msg;
			try {
				Channel channel = ctx.channel();
				QueryStringDecoder queryDecoder = new QueryStringDecoder(req.uri());
				HttpResponses.sendBadRequestError(channel);
                log.warn("Blocked wrong socket.io-context request! url: {}, params: {}, ip: {}", queryDecoder.path(), queryDecoder.parameters(), channel.remoteAddress());				
			} finally {
				((FullHttpRequest)msg).release();
			}
		}else {
			super.channelRead(ctx, msg);
		}
	}
	
	
	
}
