package netty.demo.danmu;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 
 * 处理TextWebSocketFrame
 * 
 * @author yuezh2
 *
 * @date 2020年6月15日 下午4:17:19  
 *
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

	
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {//1
		Channel incoming = ctx.channel();
		for(Channel channel : channels) {
			if(channel!=incoming) {
				channel.writeAndFlush(new TextWebSocketFrame(msg.text()));
			}else {
				channel.writeAndFlush(new TextWebSocketFrame("我发送的"+msg.text()));
			}
		}
	}

	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {//2
		Channel incoming = ctx.channel();
		
		//broadcast a message to multiple channels
		channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - "+incoming.remoteAddress()+"加入"));
		
		channels.add(incoming);
		System.out.println("Client:"+incoming.remoteAddress()+"加入");
		
	}
	
	
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		
		//broadcast a message to multiple channels
		channels.writeAndFlush(new TextWebSocketFrame("[SERVER] - "+incoming.remoteAddress()+"离开"));
		
		System.err.println("Client:"+incoming.remoteAddress()+"离开");
	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		System.err.println("Client:"+incoming.remoteAddress()+"掉线");
	}

	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		System.err.println("Client:"+incoming.remoteAddress()+"异常");
		//出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
	
	
}
