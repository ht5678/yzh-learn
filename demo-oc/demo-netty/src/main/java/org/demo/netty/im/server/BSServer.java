package org.demo.netty.im.server;

import org.apache.logging.log4j.core.lookup.EventLookup;
import org.demo.netty.im.bs.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.EventLoopGroup;

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
	
	private bschannel
	
	
	

	@Override
	public void start() {
		
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
