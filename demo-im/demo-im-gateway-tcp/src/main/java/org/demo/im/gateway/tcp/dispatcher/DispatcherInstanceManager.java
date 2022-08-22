package org.demo.im.gateway.tcp.dispatcher;

import java.util.ArrayList;
import java.util.List;

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
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 分发系统实例的管理组件
 * @author yue
 *
 */
public class DispatcherInstanceManager {

	/*
	 * 分发系统实例地址列表
	 */
	private static List<DispatcherInstanceAddress> dispatcherInstanceAddresses = 
				new ArrayList<DispatcherInstanceAddress>();
	
	
	
	/*
	 * 静态化分发系统实例地址列表
	 */
	static {
		dispatcherInstanceAddresses.add(new DispatcherInstanceAddress("localhost", "127.0.0.1", 8090));
	}
	
	/**
	 * 
	 */
	private DispatcherInstanceManager(){
		
	}
	
	
	/*
	 * 单例
	 */
	static class Singleton { 
		static DispatcherInstanceManager instance = new DispatcherInstanceManager();
	}
	
	
	/**
	 * 分发系统实例
	 * @return
	 */
	public static DispatcherInstanceManager getInstance() {
		return Singleton.instance;
	}
	
	
	/*
	 * 分发系统实例
	 */
	private List<SocketChannel> dispatcherInstances = new ArrayList<>();
	
	
	
	/**
	 * 初始化组件
	 */
	public void init() {
		//主动跟一批分发系统建立长链接
		for(DispatcherInstanceAddress dispatcherInstanceAddress : dispatcherInstanceAddresses) {
			try{
				connectDispatcherInstance(dispatcherInstanceAddress);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 连接一个分发系统实例
	 */
	private void connectDispatcherInstance(DispatcherInstanceAddress dispatcherInstanceAddress) throws Exception {
		final EventLoopGroup threadGroup = new NioEventLoopGroup();
		
		Bootstrap client = new Bootstrap();
		
		client.group(threadGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
					socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
					
					socketChannel.pipeline().addLast(new StringDecoder());
					socketChannel.pipeline().addLast(new DispatcherInstanceHandler());
				}
			});
		
		System.out.println("完成netty客户端配置");
		
		ChannelFuture channelFuture = client.connect(dispatcherInstanceAddress.getHost() , dispatcherInstanceAddress.getPort());	//尝试发起连接
		System.out.println("发起对tcp接入系统的连接");

		
		channelFuture.addListener(new ChannelFutureListener() {		//给异步化的连接请求加入监听器
			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				if(channelFuture.isSuccess()){
					dispatcherInstances.add((SocketChannel)channelFuture.channel());
					System.out.println("跟TCP接入系统完成长链接的建立 .");
				}else{
					channelFuture.channel().close();
					threadGroup.shutdownGracefully();
				}
			}
		});
		
		channelFuture.sync();
		
	}
	
	
	
}
