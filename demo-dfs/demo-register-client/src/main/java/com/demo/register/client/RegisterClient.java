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

}
