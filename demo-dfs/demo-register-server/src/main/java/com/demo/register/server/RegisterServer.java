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
		
		
		
		while(true) {
			Thread.sleep(30 * 1000);
		}
	}
	
}
