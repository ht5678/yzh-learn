package org.demo.netty.im.server;

import java.util.concurrent.Future;

import org.apache.logging.log4j.core.lookup.EventLookup;
import org.demo.netty.im.bs.config.Configuration;
import org.demo.netty.im.initializer.BsChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午9:57:29
 */
public class BSServer implements Server{
	
	private final static Logger log = LoggerFactory.getLogger(BSServer.class);
	
	
	private Configuration config;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	
	private BsChannelInitializer channelInitializer;
	
	
	
	/**
	 * 
	 */
	public BSServer() {
		this(new Configuration());
	}
	
	
	/**
	 * 
	 */
	public BSServer(Configuration config) {
		this.config = config;
	}
	
	
	
	
	/**
	 * 启动b/s
	 */
	@Override
	public void start() {
		start
	}
	
	
	/**
	 * 同步开启 B/S
	 * @return
	 */
	private Future<Void> startAsync() {
		initGroups();
		
		channelInitializer = new BsChannelInitializer(config);
		
		Class<? extends ServerChannel> channelClass = NioServerSocketChannel.class;
		if(config.isUseLinuxNativeEpoll()) {
			channelClass = EpollServerSocketChannel.class;
		}
		
		//
		ServerBootstrap boot = new ServerBootstrap();
		boot.group(bossGroup , workerGroup)
			.channel(channelClass)
			.childHandler(channelInitializer);
		
		apply
		
	}
	
	
	
	/**
	 * 初始化 团队信息boss监听连接 worker具体工作者
	 */
	private void initGroups() {
		if(config.isUseLinuxNativeEpoll()) {
			bossGroup = new EpollEventLoopGroup(config.getBossThreads());
			workerGroup = new EpollEventLoopGroup(config.getWorkerThreads());
		} else {
			bossGroup = new NioEventLoopGroup(config.getBossThreads());
			workerGroup = new NioEventLoopGroup(config.getWorkerThreads());
		}
	}
	
	

	@Override
	public void stop() {
		
	}

	@Override
	public int getPort() {
		return 0;
	}

	@Override
	public String getHostName() {
		return null;
	}

	
	
}
