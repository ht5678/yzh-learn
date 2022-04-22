package org.demo.netty.im.simple.v4.im.server;


import org.demo.netty.im.fake.boot.Server;
import org.demo.netty.im.fake.im.bs.config.Configuration;
import org.demo.netty.im.fake.im.config.SocketConfig;
import org.demo.netty.im.simple.v4.im.initializer.BsChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

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
		startAsync().syncUninterruptibly();
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
		
		applyConnectionOptions(boot);
		//
		return boot.bind(config.getPort()).addListener(new FutureListener<Void>() {

			@Override
			public void operationComplete(io.netty.util.concurrent.Future<Void> future) throws Exception {
				if(future.isSuccess()) {
				    log.info("B/S 服务启动成功  监听端口: {}", config.getPort());
				    System.out.println("B/S 服务启动成功  监听端口: "+ config.getPort());
                } else {
                    log.error("B/S 服务启动失败  监听端口: {}!", config.getPort());
                }
			}
			
		});
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
	
	
	/**
	 * 设置应用连接选项
	 */
	protected void applyConnectionOptions(ServerBootstrap bootstrap) {
		SocketConfig socketConfig = config.getSocketConfig();
		bootstrap.childOption(ChannelOption.TCP_NODELAY, socketConfig.isTcpNoDelay());
		//
		if(socketConfig.getTcpSendBufferSize() != -1) {
			bootstrap.childOption(ChannelOption.SO_SNDBUF, socketConfig.getTcpSendBufferSize());
		}
		//
		if(socketConfig.getTcpReceiveBufferSize() != -1) {
			bootstrap.childOption(ChannelOption.SO_RCVBUF, socketConfig.getTcpReceiveBufferSize());
			bootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(socketConfig.getTcpReceiveBufferSize()));
		}
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, socketConfig.isTcpKeepAlive());
		bootstrap.childOption(ChannelOption.SO_LINGER, socketConfig.getSoLinger());
		//
		bootstrap.childOption(ChannelOption.SO_REUSEADDR, socketConfig.isReuseAddress());
		bootstrap.childOption(ChannelOption.SO_BACKLOG, socketConfig.getAcceptBackLog());
	}
	
	
	/**
	 * 停止B/S
	 */
	@Override
	public void stop() {
		bossGroup.shutdownGracefully().syncUninterruptibly();
		workerGroup.shutdownGracefully().syncUninterruptibly();
		log.info("B/S is stopped");
	}

	@Override
	public int getPort() {
		return config.getPort();
	}

	@Override
	public String getHostName() {
		return config.getHostname();
	}

	
	
}
