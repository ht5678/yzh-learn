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
	private Daemon daemon;
	
	
	
	
	public ServiceAliveMonitor() {
		ThreadGroup daemonThreadGroup = new ThreadGroup("daemon");
		
		
		this.daemon = new Daemon(); 
		//只要设置了这个标志位 , 代表该线程是一个daemon线程 , 后台线程
		//非daemon线程 , 我们一般叫做工作线程
		//如果工作线程(main线程)都结束了 , daemon线程是不会阻止jvm线程结束的
		//
		daemon.setDaemon(true);
		daemon.setName("ServiceAliveMonitor");
	}
	
	
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
		
		private ServiceRegistry registry = ServiceRegistry.getInstance();
		
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
								
								//更新自我保护的阈值
								synchronized (SelfProtectionPolicy.class) {
									SelfProtectionPolicy selfProtectionPolicy = SelfProtectionPolicy.getInstance();
									selfProtectionPolicy.setExpectedHeartbeatRate(selfProtectionPolicy.getExpectedHeartbeatRate() - 2);
									selfProtectionPolicy.setExpectedHeartbeatThreshold((long)(selfProtectionPolicy.getExpectedHeartbeatRate()*0.85));
								}
								
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
