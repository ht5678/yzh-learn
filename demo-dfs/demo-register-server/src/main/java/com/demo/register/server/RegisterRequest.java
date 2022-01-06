package com.demo.register.server;


/**
 * 注册请求
 * @author yuezh2@lenovo.com
 *	@date 2022年1月4日下午6:32:12
 */
public class RegisterRequest {

	private String serviceName;
	
	private String ip;
	
	private String hostname;
	
	private int port;

	private String serviceInstanceId;
	
	
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}
	
	
}
