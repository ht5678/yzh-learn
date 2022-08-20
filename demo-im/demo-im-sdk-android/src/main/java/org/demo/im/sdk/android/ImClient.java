package org.demo.im.sdk.android;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 
 * 如果你的APP需要跟	一台机器建立一个连接
 * 此时就可以新建一个ImClient , 这个client就代表跟一台机器的一个连接就好了
 * 
 * @author yue
 *
 */
public class ImClient {

	//代表的是netty客户端中的线程池
	private EventLoopGroup threadGroup;
	//代表的是netty客户端
	private Bootstrap client;
	//代表的是客户端的APP跟TCP接入系统的某台机器的长链接
	private SocketChannel socketChannel;
	
	
	
	/**
	 * 跟机器建立连接
	 */
	public void connect(String host , int port) throws Exception{
		this.threadGroup = new NioEventLoopGroup();
		
		this.client = new Bootstrap();
		
		client.group(threadGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(new StringDecoder());
					socketChannel.pipeline().addLast(new ImClientHandler());
				}
				
			});
		
		System.out.println("完成netty客户端的配置");
		
		ChannelFuture channelFuture = client.connect(host, port);		//尝试发起连接
		System.out.println("发起对TCP接入系统的连接");
		
		
		channelFuture.addListener(new ChannelFutureListener() {		//给异步化的连接请求加入监听器
			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				if(channelFuture.isSuccess()){
					socketChannel = (SocketChannel)channelFuture.channel();
					System.out.println("跟TCP接入系统完成长链接的建立 .");
				}else{
					channelFuture.channel().close();
					threadGroup.shutdownGracefully();
				}
			}
		});
		
		channelFuture.sync();
	}
	
	
	
	/**
	 * 向机器发送消息过去
	 */
	public void send(String userId , String message)throws Exception{
		byte[] messageBytes = (message + "|" + userId).getBytes();
		ByteBuf messageBuffer = Unpooled.buffer(messageBytes.length);
		messageBuffer.writeBytes(messageBytes);

		socketChannel.isWritable();
		socketChannel.writeAndFlush(messageBuffer);
		
		System.out.println("向TCP接入系统发送第一条消息 , 推送给test002用户");
	}
	
	
	
	
	/**
	 * 关闭跟机器的连接
	 */
	public void close()throws Exception{
		this.socketChannel.close();
		this.threadGroup.shutdownGracefully();
	}
}
