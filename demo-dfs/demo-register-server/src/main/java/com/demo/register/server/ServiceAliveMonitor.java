package com.demo.register.server;

import java.util.Map;

/**
 * 微服务存活状态监控组件
 * @author yuezh2@lenovo.com
 *	@date 2022年1月6日下午8:23:11
 */
public class ServiceAliveMonitor {
	
	//检查服务实例是否存活的间隔
	private static final Long CHECK_ALIVE_INTERVAL = 60 * 1000L;
	

	/**
	 * 负责监控微服务存活状态的后台线程
	 */
	private Daemon daemon = new Daemon();
	
	
	/**
	 * 启动后台线程
	 */
	public void start() {
		daemon.start();
	}
	
	
	
	/**
	 * 负责监控微服务存活状态的后台线程
	 * @author yuezh2@lenovo.com
	 *	@date 2022年1月6日下午8:23:52
	 */
	private class Daemon extends Thread{
		
		private Registry registry = Registry.getInstance();
		
		@Override
		public void run() {
			while(true) {
				try {
					Map<String, Map<String , ServiceInstance>> registryMap = registry.getRegistry();
					
					for(String serviceName : registryMap.keySet()) {
						Map<String, ServiceInstance> serviceInstanceMap = registryMap.get(serviceName);
						
						for(ServiceInstance serviceInstance : serviceInstanceMap.values()) {
							//说明服务实例距离上次发送心跳超过90s了
							//认为这个服务死了
							//从注册表移除该实例
							if(!serviceInstance.isAlive()) {
								registry.remove(serviceName, serviceInstance.getServiceInstanceId());
							}
						}
					}
					
					Thread.sleep(CHECK_ALIVE_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
