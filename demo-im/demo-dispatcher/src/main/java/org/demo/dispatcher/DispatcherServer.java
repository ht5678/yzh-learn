package org.demo.dispatcher;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 分发系统的启动类
 * @author yue
 *
 */
public class DispatcherServer {
	
	
	private static int PORT = 8080;
	
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		
		//我们到底是让接入系统主动跟分发系统建立连接?
		//还是让分发系统主动跟接入系统建立连接?
		//按照层和层的关系来说 , 应该是接入系统主动分发系统去建立连接
		//应该是让分发系统使用的是netty服务端的代码,监听一个端口 , 等待人家跟他建立连接
		//但凡建立连接之后 , 可以吧接入系统的长链接缓存在这个组件里
		
		
		
//		GatewayManager gatewayManager = new GatewayManager();
//		gatewayManager.init();
//		
//		while(true){
//			Thread.sleep(1000);
//		}
		
		EventLoopGroup connectionThreadGroup = new NioEventLoopGroup();
		EventLoopGroup ioThreadGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap server = new ServerBootstrap();
			
			server.group(connectionThreadGroup , ioThreadGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
						socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
						
						socketChannel.pipeline().addLast(new StringDecoder());
						socketChannel.pipeline().addLast(new DispatcherHandler());
					}
					
				});
			
			//
			ChannelFuture channelFuture = server.bind(PORT).sync();
			
			channelFuture.channel().closeFuture().sync();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			connectionThreadGroup.shutdownGracefully();
			ioThreadGroup.shutdownGracefully();
		}
		
		
	}

}
