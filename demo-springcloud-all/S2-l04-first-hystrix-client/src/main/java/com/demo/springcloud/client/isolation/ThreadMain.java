package com.demo.springcloud.client.isolation;

import com.netflix.config.ConfigurationManager;

import freemarker.template.Configuration;

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
 * @author yuezh2   2018年8月24日 下午3:44:40
 *
 */
public class ThreadMain {
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		//设置线程池的数量3 , 默认是10
		ConfigurationManager.getConfigInstance().setProperty("hystrix.threadpool.default.coreSize", 3);
		
		for(int i = 0 ; i < 6 ; i++){
			MyCommand c = new MyCommand(i);
			c.queue();
		}
		Thread.sleep(5000);
	}
	
	

}
