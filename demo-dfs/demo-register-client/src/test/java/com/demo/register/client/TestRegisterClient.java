package com.demo.register.client;



/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2022年1月4日下午9:03:24
 */
public class TestRegisterClient {

	public static void main(String[] args) throws Exception{
		RegisterClient registerClient = new RegisterClient();
		registerClient.start();
		
		Thread.sleep(5 * 1000);
		
		registerClient.shutdown();
	}
	
}
