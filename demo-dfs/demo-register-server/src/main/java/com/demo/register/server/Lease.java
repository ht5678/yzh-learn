package com.demo.register.server;


/**
 * 契约:
 * 		维护了一个服务实例和当前注册中心的联系
 * 		包括心跳的时间 , 创建时间 , 等等
 * 		
 * 
 * @author yuezh2@lenovo.com
 *	@date 2022年1月6日下午8:37:06
 */
public class Lease {
	
	//最近一次心跳时间
	private Long latestHeartbeatTime = System.currentTimeMillis();
	
	
	
	

}
