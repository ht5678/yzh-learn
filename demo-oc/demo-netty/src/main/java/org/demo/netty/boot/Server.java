package org.demo.netty.boot;


/**
 * 
 * @author yue
 *
 */
public interface Server {
	public void start();
	
	public void stop();
	
	public int getPort();
	
	public String getHostName();
}
