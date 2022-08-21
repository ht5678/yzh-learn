package org.demo.dispatcher;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 分发系统的启动类
 * @author yue
 *
 */
public class DispatcherServer {
	
	public static void main(String[] args) throws Exception{
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
						
					}
					
				});
		}catch(Exception e){
			
		}
		
		
	}

}
