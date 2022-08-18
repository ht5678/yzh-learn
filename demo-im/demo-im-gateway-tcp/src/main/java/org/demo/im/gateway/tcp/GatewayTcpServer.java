package org.demo.im.gateway.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @author yue
 *
 */
public class GatewayTcpServer {
	
	
	public static final int PORT = 8080;
	
	
	public static void main(String[] args) {
		
		EventLoopGroup connectionThreadGroup = new NioEventLoopGroup();
		EventLoopGroup ioThreadGroup = new NioEventLoopGroup();
		
		try{
			
			ServerBootstrap server = new ServerBootstrap();
			
			server.group(connectionThreadGroup , ioThreadGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast(new GatewayTcpHandler());
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
