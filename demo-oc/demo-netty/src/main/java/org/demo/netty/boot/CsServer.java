package org.demo.netty.boot;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.util.concurrent.Future;

/**
 * 
 * @author yue
 *
 */
public class CsServer implements Server{
	
	private static Logger log = LoggerFactory.getLogger(CsServer.class);
	
	private CsConfiguration config;
	
	
	/**
	 * 
	 * @throws UnknownHostException
	 */
	public CsServer() throws UnknownHostException {
		this(new CsConfiguration(InetAddress.getLocalHost().getCanonicalHostName().toLowerCase()));
	}
	
	/**
	 * 
	 * @param config
	 */
	public CsServer(CsConfiguration config) {
		this.config = config;
	}
	
	
	
	public Future<Void> startAsync(){
		
		return null;
	}




	@Override
	public void start() {
		startAsync().syncUninterruptibly();
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
