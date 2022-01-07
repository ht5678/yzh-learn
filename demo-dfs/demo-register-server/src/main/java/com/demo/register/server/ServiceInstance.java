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
	
	//判断服务实例 , 不再存活的周期
	private static final Long NOT_ALIVE_PERIOD = 90 * 1000L;
	
	

	private String serviceName;
	
	private String ip;
	
	private String hostname;
	
	private String serviceInstanceId;
	
	private int port;

	private Lease lease;
	
	
	/**
	 * 
	 */
	public ServiceInstance() {
		this.lease = new Lease();
	}
	
	
	public void renew() {
		this.lease.renew();
	}
	
	
	public Boolean isAlive() {
		return this.lease.isAlive();
	}
	
	
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
	
	
	
	
	
	/**
	 * 契约:
	 * 		维护了一个服务实例和当前注册中心的联系
	 * 		包括心跳的时间 , 创建时间 , 等等
	 * 		
	 * 
	 * @author yuezh2@lenovo.com
	 *	@date 2022年1月6日下午8:37:06
	 */
	private class Lease {
		
		//最近一次心跳时间
		private Long latestHeartbeatTime = System.currentTimeMillis();
		
		
		/**
		 * 续约, 只要发送一次心跳 , 就相当于把register-client和register-server之间维护的一个契约
		 * 进行了续约 , 我还存活着 , 我们俩的契约可以维持着
		 * 
		 * @param latestHeartbeatTime
		 */
		public void renew() {
			this.latestHeartbeatTime = System.currentTimeMillis();
			System.out.println("服务实例:"+serviceInstanceId+" , 进行续约 : "+this.latestHeartbeatTime);
		}
		
		
		/**
		 * 判断当前服务实例的契约是否存活
		 * @return
		 */
		public Boolean isAlive() {
			Long currentTime = System.currentTimeMillis();
			if(currentTime - latestHeartbeatTime > NOT_ALIVE_PERIOD) {
				System.out.println("服务实例:"+serviceInstanceId+" , 不再存活");
				return false;
			}
			System.out.println("服务实例:"+serviceInstanceId+" , 保持存活");
			return true;
		}

	}
}
