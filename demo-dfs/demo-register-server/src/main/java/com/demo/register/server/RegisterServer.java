package com.demo.register.server;

import java.util.UUID;

/**
 * 代表了服务注册中心
 * @author yuezh2@lenovo.com
 *	@date 2022年1月6日下午8:52:33
 */
public class RegisterServer {

	
	
	public static void main(String[] args) throws Exception{
		
		RegisterServerController controller = new RegisterServerController();
		
		String serviceInstanceId = UUID.randomUUID().toString().replace("-", "");
		
		//模拟发起一个服务注册的请求
		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setHostname("inventory-service-01");
		registerRequest.setIp("192.168.22.33");
		registerRequest.setPort(9000);
		registerRequest.setServiceInstanceId(serviceInstanceId);
		registerRequest.setServiceName("inventory-service");
		
		controller.register(registerRequest);
		
		
		//模拟一次心跳
		HeartbeatRequest heartbeatRequest = new HeartbeatRequest();
		heartbeatRequest.setServiceInstanceId(serviceInstanceId);
		heartbeatRequest.setServiceName("inventory-service");
		HeartbeatResponse heartbeatResponse = controller.heartbeat(heartbeatRequest);
		System.out.println(heartbeatResponse.getStatus());
		
		
		//开启一个后台线程 , 检查微服务的存活状态
		//被摘除的一个原因是 , 心跳只模拟了一次 , 没有再次进行续约
		ServiceAliveMonitor serviceAliveMonitor = new ServiceAliveMonitor();
		serviceAliveMonitor.start();
		
		while(true) {
			Thread.sleep(30 * 1000);
		}
	}
	
}
