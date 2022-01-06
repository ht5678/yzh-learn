package com.demo.register.server;



/**
 * 代表一个服务实例
 * 里面包含一个服务实例的所有信息
 * 比如服务名称, ip地址,  hostname , 端口号 , 服务实例id
 * 还有契约信息(Lease)
 * 
 * @author yuezh2@lenovo.com
 *	@date 2022年1月6日下午8:36:57
 */
public class ServiceInstance {

	private String serviceName;
	
	private String ip;
	
	private String hostname;
	
	private String serviceInstanceId;
	
	private int port;

	private Lease lease;
	
	
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

	public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Lease getLease() {
		return lease;
	}

	public void setLease(Lease lease) {
		this.lease = lease;
	}

	@Override
	public String toString() {
		return "ServiceInstance [serviceName=" + serviceName + ", ip=" + ip + ", hostname=" + hostname
				+ ", serviceInstanceId=" + serviceInstanceId + ", port=" + port + ", lease=" + lease + "]";
	}
	
	
	
}
