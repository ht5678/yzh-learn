package com.demo.register.client;



/**
 * 在服务上被创建和启动 , 负责跟register-server进行通信
 * @author yuezh2@lenovo.com
 *	@date 2022年1月4日下午6:26:45
 */
public class RegisterClient {
	
	
	
	public void start() {
		//一旦启动了这个组件后 , 负责在服务上干两个事情 
		//第一个事情 , 就是开启一个线程向register-server发送请求 , 注册这个服务
		//第二个事情 , 就是在注册成功后 , 就会开启另外一个线程去发送心跳
		
		new RegisterWorker().start();
	}

}
