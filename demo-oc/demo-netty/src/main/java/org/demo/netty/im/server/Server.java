package org.demo.netty.im.server;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午9:56:31
 */
public interface Server {

	public void start();
	
	public void stop();
	
	public int getPort();
	
	public String getHostName();
	
}
