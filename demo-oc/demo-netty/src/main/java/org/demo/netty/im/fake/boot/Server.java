package org.demo.netty.im.fake.boot;


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
