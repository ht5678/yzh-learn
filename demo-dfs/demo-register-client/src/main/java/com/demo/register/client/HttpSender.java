package com.demo.register.client;


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
		System.out.println("发送请求进行心跳......");
		
		HeartbeatResponse response = new HeartbeatResponse();
		response.setStatus(RegisterResponse.SUCCESS);
		
		return response;
	} 
	
	
}
