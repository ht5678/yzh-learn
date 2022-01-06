package com.demo.register.client;



/**
 * 微服务存活状态监控组件
 * @author yuezh2@lenovo.com
 *	@date 2022年1月6日下午8:23:11
 */
public class ServiceAliveMonitorDaemon {

	/**
	 * 负责监控微服务存活状态的后台线程
	 */
	private Daemon daemon = new Daemon();
	
	
	public void start() {
		daemon.start();
	}
	
	
	
	/**
	 * 负责监控微服务存活状态的后台线程
	 * @author yuezh2@lenovo.com
	 *	@date 2022年1月6日下午8:23:52
	 */
	private class Daemon extends Thread{
		
//		private registry
		
		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
