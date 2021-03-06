package netty.demo.http.cors;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * 
 * 
 * A simple handler which will simple return a successful Http
 * response for any request.
 * 
 * @author yuezh2   2016年11月15日 下午5:37:13
 *
 */
public class OkResponseHandler extends SimpleChannelInboundHandler<Object>{

	
	
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		final FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		
		response.headers().set("custom-response-header","Some value");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	

}
