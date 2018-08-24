package com.demo.springcloud.client.cb;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixCommand.Setter;

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
 * @author yuezh2   2018年8月24日 下午2:10:01
 *
 */
public class FallbackMain {
	
	
	/**
	 * 打开断路器以后, 总会返回fallback 
	 * @param args
	 */
	public static void main(String[] args) {
		FallbackCommand c = new FallbackCommand();
		String result = c.execute();
		System.out.println(result);
	}
	
	
	
	static class FallbackCommand extends HystrixCommand<String>{

		/**
		 * 
		 */
		protected FallbackCommand() {
			super(Setter.withGroupKey(
					HystrixCommandGroupKey.Factory.asKey("TestGroupKey"))			//组名
					.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerForceOpen(true))//打开断路器
					);
		}

		
		/**
		 * 
		 */
		@Override
		protected String run() throws Exception {
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
