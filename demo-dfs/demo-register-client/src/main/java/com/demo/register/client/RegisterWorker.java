package com.demo.register.client;



/**
 * 负责向register - server 发起注册申请的线程
 * @author yuezh2@lenovo.com
 *	@date 2022年1月4日下午6:28:42
 */
public class RegisterWorker extends Thread{

	public static final String SERVICE_NAME = "inventory-service";
	public static final String IP = "192.168.0.1";
	public static final int PORT = 9000;
	
	private HttpSender httpSender;
	
	public RegisterWorker() {
		httpSender = new HttpSender();
	}
	
	
	@Override
	public void run() {
		//获取当前机器的信息 , ip , hostname , 监听的端口号
		//从配置文件可以拿到
		RegisterRequest request = new RegisterRequest();
		request.setServiceName(SERVICE_NAME);
		request.setIp(IP);
		request.setPort(PORT);
		
		RegisterResponse response = httpSender.register(request);
		System.out.println("服务注册的结果是:" + response.getStatus());
	}
	
}
