package com.demo.register.server;


/**
 * 心跳请求
 * @author yuezh2@lenovo.com
 *	@date 2022年1月4日下午8:36:20
 */
public class HeartbeatRequest {

	private String serviceInstanceId;
	
	private String serviceName;

	
	public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	
}
