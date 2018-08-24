package com.demo.springcloud.client.isolation;

import com.netflix.config.ConfigurationManager;

/**
 * 
 * 
 *         ┏┓　　　┏┓
 *      ┏┛┻━━━┛┻┓
 *      ┃　　　　　　　┃ 　
 *      ┃　　　━　　　┃
 *      ┃　┳┛　┗┳　┃
 *      ┃　　　　　　　┃
 *      ┃　　　┻　　　┃
 *      ┃　　　　　　　┃
 *      ┗━┓　　　┏━┛
 *         ┃　　　┃　　　　　　　　　
 *         ┃　　　┃
 *         ┃　　　┗━━━┓
 *         ┃　　　　　　　┣┓
 *         ┃　　　　　　　┏┛
 *         ┗┓┓┏━┳┓┏┛
 *　　      ┃┫┫　┃┫┫
 *　        ┗┻┛　┗┻┛
 *
 *-------------------- 神兽保佑 永无bug --------------------
 * 
 * 
 * 
 * @author yuezh2   2018年8月24日 下午10:16:51
 *
 */
public class SemaphoreMain {

	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.execution.isolation.strategy", "SEMAPHORE");
		ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests", 2);
		
		for(int i = 0 ; i < 6; i++){
			final int index=i;
			Thread t = new  Thread(){
				@Override
				public void run() {
					MyCommand c = new MyCommand(index);
					c.execute();
				}
			};
			t.start();
		}
		Thread.sleep(6000);
	}
	
}
