package com.demo.register.client;

import java.util.UUID;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2022年1月4日下午8:31:23
 */
public class RegisterClientWorker  extends Thread{

	public static final String SERVICE_NAME = "inventory-service";
	public static final String IP = "192.168.0.1";
	public static final int PORT = 9000;
	
	private HttpSender httpSender;
	
	//是否完成注册
	private Boolean finishedRegister;
	
	
	private String serviceInstanceId;
	
	
	public RegisterClientWorker(String serviceInstanceId) {
		httpSender = new HttpSender();
		this.finishedRegister = false;
		this.serviceInstanceId = serviceInstanceId;
	}
	
	
	@Override
	public void run() {
		
		if(!finishedRegister) {
			//获取当前机器的信息 , ip , hostname , 监听的端口号
			//从配置文件可以拿到
			RegisterRequest registerRequest = new RegisterRequest();
			registerRequest.setServiceName(SERVICE_NAME);
			registerRequest.setIp(IP);
			registerRequest.setPort(PORT);
			registerRequest.setServiceInstanceId(UUID.randomUUID().toString().replace("-", ""));
			
			RegisterResponse registerResponse = httpSender.register(registerRequest);
			System.out.println("服务注册的结果是:" + registerResponse.getStatus());
			
			//如果注册成功
			if(RegisterResponse.SUCCESS.equals(registerResponse.getStatus())) {
				this.finishedRegister = true;
			}else {
				return;
//				throw new Exception("服务注册失败");
			}
			
		}
		
		//如果注册成功了 , 就进入while true死循环
		if(finishedRegister) {
			HeartbeatRequest heartbeatRequest = new HeartbeatRequest();
			heartbeatRequest.setServiceInstanceId(serviceInstanceId);
			
			HeartbeatResponse heartbeatResponse =null;
			while(true) {
				heartbeatResponse = httpSender.heartbeat(heartbeatRequest);
				System.out.println("心跳的结果为: "+heartbeatResponse.getStatus()+"......");
			}
		}
	}
	
}
