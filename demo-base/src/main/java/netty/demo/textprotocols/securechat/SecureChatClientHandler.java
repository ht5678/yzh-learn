package netty.demo.textprotocols.securechat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;




/**
 * 
 * @author yuezh2   2016年10月18日 下午5:11:57
 *
 */
public class SecureChatClientHandler extends SimpleChannelInboundHandler<String> {
	
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.err.println(msg);
	}

	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
        ctx.close();
	}
	
	
	

}
