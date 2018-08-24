package com.demo.springcloud.client.circuitbreaker;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandMetrics.HealthCounts;
import com.netflix.hystrix.HystrixCommandProperties;

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
 * @author yuezh2   2018年8月24日 下午2:43:10
 *
 */
public class CloseMain {
	
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		ConfigurationManager.getConfigInstance()
				.setProperty("hystrix.command.default.circuitBreaker.requestVolumeThreshold", 3);
		
		boolean isTimeout = true;
		//
		for(int i = 0 ; i < 30 ; i++){
			TestCommand c = new TestCommand(isTimeout);
			c.execute();
			HealthCounts hc = c.getMetrics().getHealthCounts();
			System.out.println("断路器状态:"+c.isCircuitBreakerOpen()+" , 请求数量:"+hc.getTotalRequests());
			
			if(c.isCircuitBreakerOpen()){
				isTimeout = false;
				System.out.println("#######  断路器打开了 , 等待请求 ");
				Thread.sleep(6000);
			}
				
			//判断是否开启断路器
			System.out.println(c.isCircuitBreakerOpen());
		}
	}
	
	
	
	
	
	static class TestCommand extends HystrixCommand<String>{
		
		private boolean isTimeout;
		

		/**
		 * 
		 */
		protected TestCommand(boolean isTimeout) {
			super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
						.andCommandPropertiesDefaults(
								HystrixCommandProperties.Setter()
									.withExecutionTimeoutInMilliseconds(200)));
			
			this.isTimeout = isTimeout;
		}

		
		/**
		 * 
		 */
		@Override
		protected String run() throws Exception {
			if(isTimeout){
				Thread.sleep(300);
			}else{
				Thread.sleep(100);
			}
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
