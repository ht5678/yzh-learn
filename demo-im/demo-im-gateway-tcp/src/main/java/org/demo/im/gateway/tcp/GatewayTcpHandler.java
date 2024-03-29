package org.demo.im.gateway.tcp;


import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
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
		SocketChannel socketChannel = (SocketChannel)ctx.channel();
		
		ClientManager clientManager = ClientManager.getInstance();
		clientManager.removeChannel(socketChannel);
		
		System.out.println("检测到客户端的连接断开 , 删除其连接缓存 : "+socketChannel.remoteAddress().toString());
	}
	
	
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//ctx.channel() -> 对应客户端的socketchannel
		//一旦token认证完毕后 , 就应该把这个客户端的socketchannel给缓存起来
		//后面如果有需要对这个客户端推到一条消息过去 , 直接从缓存里面找到这个SocketChannel , 进行推送就可以了
		//此时服务端就可以主动把消息发送到客户端了
		ClientManager clientManager = ClientManager.getInstance();
		
		//使用了StringDecoder后就废弃了
//		ByteBuf messagebuffer = (ByteBuf)msg;
//		System.out.println("接收到一条消息: "+messagebuffer.toString(CharsetUtil.UTF_8));
		
		String message = (String)msg;
		System.out.println("接收到一条消息: "+message);
		
		
		if(message.startsWith("发起用户认证")) {
			String token = message.split("\\|")[2];
			//使用token去找sso单点登录系统进行认证 , 看这个用户是否合法的登录用户
			
			//如果认证成功的话 , 就可以把这个链接缓存起来了
			String userId = message.split("\\|")[1];
			clientManager.addChannel(userId, (SocketChannel)ctx.channel());
			
			System.out.println("对用户发起的认证确认完毕 , 缓存客户端长链接 , userId =" + userId);
		}else{
			String userId = message.split("\\|")[1];
			
//			if(!clientManager.existChannel(userId)){
			if(!clientManager.isCientConnection(userId)){
				System.out.println("未认证用户 , 不能处理请求 . ");
				
				byte[] responseBytes = "未认证用户 , 不能处理请求 $_".getBytes();
				ByteBuf responseBuffer = Unpooled.buffer(responseBytes.length);
				responseBuffer.writeBytes(responseBytes);
				ctx.writeAndFlush(responseBuffer);
			}else{
				System.out.println("将消息发送到kafka中: "+message);
			}
		}
		
		
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
