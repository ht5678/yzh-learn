package org.demo.netty.im.cs.config;

import org.demo.netty.exception.DefaultExceptionListener;
import org.demo.netty.exception.ExceptionListener;
import org.demo.netty.util.JacksonJsonSupport;
import org.demo.netty.util.JsonSupport;

/**
 * 
 * @author yue
 *
 */
public class CsConfiguration {
	
	private JsonSupport jsonSupport;
	private String hostName;
	private ExceptionListener exceptionListener;
	
	
	/**
	 * 
	 */
	public CsConfiguration(String hostName){
		exceptionListener = new DefaultExceptionListener();
		jsonSupport = new JacksonJsonSupport();
		this.hostName = hostName;
	}


	public JsonSupport getJsonSupport() {
		return jsonSupport;
	}


	public String getHostName() {
		return hostName;
	}


	public ExceptionListener getExceptionListener() {
		return exceptionListener;
	}


	public void setExceptionListener(ExceptionListener exceptionListener) {
		this.exceptionListener = exceptionListener;
	}
	
	

}
