package com.demo.springcloud.client.circuitbreaker;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

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
 * @author yuezh2   2018年8月24日 下午2:30:34
 *
 */
public class OpenMain {
	
	
	/**
	 * 断路器开启的条件:
	 *		整个链路达到一定的阈值,默认情况下 , 10s钟内产生超过20次请求,则符合第一个条件
	 *		满足第一个条件的情况下,如果请求的错误百分比大于阈值,则会打开断路器,默认为50%
	 * @param args
	 */
	public static void main(String[] args) {
		ConfigurationManager.getConfigInstance()
				.setProperty("hystrix.command.default.circuitBreaker.requestVolumeThreshold", 10);
		
		//
		for(int i = 0 ; i < 30 ; i++){
			ErrorCommand c = new ErrorCommand();
			c.execute();
			//判断是否开启断路器
			System.out.println(c.isCircuitBreakerOpen());
		}
		
	}
	
	
	
	static class ErrorCommand extends HystrixCommand<String>{

		/**
		 * 
		 */
		protected ErrorCommand() {
			super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
						.andCommandPropertiesDefaults(
								HystrixCommandProperties.Setter()
									.withExecutionTimeoutInMilliseconds(200)));
		}

		
		/**
		 * 
		 */
		@Override
		protected String run() throws Exception {
			Thread.sleep(300);
			return "success";
		}


		/**
		 * 
		 */
		@Override
		protected String getFallback() {
			return "fallback";
		}
		
		
	}
	

}
