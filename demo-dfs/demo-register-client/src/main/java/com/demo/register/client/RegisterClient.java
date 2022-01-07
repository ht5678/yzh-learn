package com.demo.register.client;

import java.util.UUID;

/**
 * 在服务上被创建和启动 , 负责跟register-server进行通信
 * @author yuezh2@lenovo.com
 *	@date 2022年1月4日下午6:26:45
 */
public class RegisterClient {
	
	private String serviceInstanceId;
	
	public RegisterClient() {
		this.serviceInstanceId = UUID.randomUUID().toString().replace("-", "");
	}
	
	public void start() {
		//一旦启动了这个组件后 , 负责在服务上干两个事情 
		//第一个事情 , 就是开启一个线程向register-server发送请求 , 注册这个服务
		//第二个事情 , 就是在注册成功后 , 就会开启另外一个线程去发送心跳
		
		//简化模型
		//register-client只开启一个线程
		//这个线程启动后的第一件事就是注册
		//注册完成后 , 进入一个while true死循环
		//每隔30s发送请求进行心跳
		new RegisterClientWorker(serviceInstanceId).start();
	}
	
	
	
	/**
	 * 
	 * @author yuezh2@lenovo.com
	 *	@date 2022年1月4日下午8:31:23
	 */
	private class RegisterClientWorker  extends Thread{

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
//					throw new Exception("服务注册失败");
				}
				
			}
			
			//如果注册成功了 , 就进入while true死循环
			if(finishedRegister) {
				HeartbeatRequest heartbeatRequest = new HeartbeatRequest();
				heartbeatRequest.setServiceInstanceId(serviceInstanceId);
				heartbeatRequest.setServiceName(SERVICE_NAME);
				
				HeartbeatResponse heartbeatResponse =null;
				while(true) {
					heartbeatResponse = httpSender.heartbeat(heartbeatRequest);
					System.out.println("心跳的结果为: "+heartbeatResponse.getStatus()+"......");
					try {
						Thread.sleep(30 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}


}
