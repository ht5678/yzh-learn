package com.demo.register.server;


/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2022年1月4日下午8:39:10
 */
public class HeartbeatResponse {
	
	
	public static final String SUCCESS = "success";
	
	public static final String FAILTURE = "failure";
	
	
	

	private String status;

	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}

