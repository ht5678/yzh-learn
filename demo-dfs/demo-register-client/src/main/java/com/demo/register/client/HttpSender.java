package com.demo.register.client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 负责发送各种http请求的组件
 * @author yuezh2@lenovo.com
 *	@date 2022年1月4日下午6:29:43
 */
public class HttpSender {

	
	/**
	 * 发送注册的请求
	 * @param request
	 * @return
	 */
	public RegisterResponse register(RegisterRequest request) {
		//类似于HttpClient这种开源的网络包
		//可以构造一个请求 , 里面放入这个服务实例的信息 , 比如服务名称,  ip地址 , 端口号
		//然后通过请求发送过去
		System.out.println("发送请求进行注册......");
		
		//收到register-server响应后 , 封装response对象
		RegisterResponse response = new RegisterResponse();
		response.setStatus(RegisterResponse.SUCCESS);
		
		return response;
	} 
	
	
	/**
	 * 发送心跳
	 * @param request
	 * @return
	 */
	public HeartbeatResponse heartbeat(HeartbeatRequest request) {
		System.out.println("发送请求进行心跳 : "+request.getServiceInstanceId() + " .......");
		
		HeartbeatResponse response = new HeartbeatResponse();
		response.setStatus(RegisterResponse.SUCCESS);
		
		return response;
	} 
	
	
	
	
	/**
	 * 拉取服务注册表
	 * @return
	 */
	public Map<String, Map<String , ServiceInstance>> fetchServiceRegistry(){
		Map<String, Map<String , ServiceInstance>> registry = new ConcurrentHashMap<>();
		
		ServiceInstance serviceInstance = new ServiceInstance();
		serviceInstance.setHostname("finance-service-01");
		serviceInstance.setIp("192.168.31.109");
		serviceInstance.setPort(9000);
		serviceInstance.setServiceInstanceId("FINANCE-SERVICE-192.168.31.207:9000");
		serviceInstance.setServiceName("FINANCE-SERVICE");
		
		Map<String , ServiceInstance> serviceInstances = new HashMap<>();
		serviceInstances.put("FINANCE-SERVICE-192.168.31.207:9000", serviceInstance);
		
		registry.put("FINANCE-SERVICE", serviceInstances);
		
		System.out.println("拉取注册表 : "+registry);
		
		return registry;
	}
	
	
}
