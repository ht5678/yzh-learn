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
				Thread.sleep(1000);
				latch.countDown();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
