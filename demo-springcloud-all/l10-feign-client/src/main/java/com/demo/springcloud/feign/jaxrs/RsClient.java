package com.demo.springcloud.feign.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public interface RsClient {
	
	
	/**
	 * 
	 * @return
	 */
	@GET
	@Path("/hello")
	public String hello();
	
	
	

}
