package com.demo.dfs.datanode.server;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年12月21日下午9:15:42
 */
public class NameNodeServiceActor {

	
	/**
	 * 向自己负责通信的那个NameNode进行扫描
	 * @param latch
	 */
	public void register(CountDownLatch latch) {
		Thread registerThread = new RegisterThread(latch);
		registerThread.start();
	}
	
	
	/**
	 * 负责注册的线程
	 * @author yuezh2@lenovo.com
	 *	@date 2021年12月21日下午9:31:10
	 */
	class RegisterThread extends Thread{
		CountDownLatch latch;
		
		public RegisterThread(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				//发送rpc接口请求到NameNode进行注册
				System.out.println("发送请求到NameNode进行注册 ... ... ");
				
				//在这里进行注册的时候会提供哪些信息 ? 
				//比如说当前机器的ip , hostname , 这两个东西要写到配置文件里的
				//主要在本地运行测试的 , 
				String ip = "127.0.0.1";
				String hostname = "dfs-data-01";
				//通过rpc接口发送到NameNode的注册接口上
				
				
				
				Thread.sleep(1000);
				latch.countDown();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
