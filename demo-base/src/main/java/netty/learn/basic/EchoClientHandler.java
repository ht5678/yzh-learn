package netty.learn.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年3月15日 下午3:44:12  
 *
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.copiedBuffer("这是一个netty示例程序!\n",CharsetUtil.UTF_8));
	}
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("客户端接收到消息: "+ msg.toString(CharsetUtil.UTF_8));
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	
}
