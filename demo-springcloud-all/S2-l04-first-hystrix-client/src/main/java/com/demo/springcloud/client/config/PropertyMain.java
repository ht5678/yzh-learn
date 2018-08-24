package com.demo.springcloud.client.config;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

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
 * 全局设置超时时间
 * @author yuezh2   2018年8月24日 下午1:54:33
 *
 */
public class PropertyMain {

	
	public static void main(String[] args) {
		ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", 1000);
		PropertyCommand c = new PropertyCommand();
		String result = c.execute();
		System.out.println(result);
	}
	
	
	
	
	static class PropertyCommand extends HystrixCommand<String>{

		protected PropertyCommand() {
			super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")));
		}

		
		@Override
		protected String run() throws Exception {
			Thread.sleep(1500);
			return "success";
		}


		@Override
		protected String getFallback() {
			return "fallback";
		}
		
		
	}
	
	
}
