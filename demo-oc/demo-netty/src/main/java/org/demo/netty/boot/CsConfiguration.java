package org.demo.netty.boot;

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
	
	
	/**
	 * 
	 */
	public CsConfiguration(String hostName){
		jsonSupport = new JacksonJsonSupport();
		this.hostName = hostName;
	}


	public JsonSupport getJsonSupport() {
		return jsonSupport;
	}


	public String getHostName() {
		return hostName;
	}
	

}
