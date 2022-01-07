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
		
		//一般来说像register-server这种 , 不会只有一个main线程作为工作线程
		//一般是web工程 , 部署在一个web服务器里
		//核心的工作线程 , 是专门用于接收和处理register-client发送过来的请求的哪些工作线程
		//正常来说 ,他只要有工作线程 , 是不会随便退出的
		//当然如果说工作线程都停止了, 那么daemon线程就会跟着jvm进程一块退出
		
		//我们暂时这个项目没有网络这块东西
		//暂时就是在main方法里加while true , 让main工作线程不要退出 , 
		//可以保证服务一直运行 , daemon线程会跟着jvm进程一起退出 
		
		while(true) {
			Thread.sleep(30 * 1000);
		}
	}
	
}
