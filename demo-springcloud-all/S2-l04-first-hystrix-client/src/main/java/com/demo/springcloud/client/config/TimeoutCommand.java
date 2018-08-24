package com.demo.springcloud.client.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
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
 * @author yuezh2   2018年8月23日 下午10:09:29
 *
 */
public class TimeoutCommand extends HystrixCommand<String>{

	
	/**
	 * 
	 */
	protected TimeoutCommand() {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
						.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
								.withExecutionTimeoutInMilliseconds(2000)));
	}

	
	
	/**
	 * 
	 */
	@Override
	protected String run() throws Exception {
		Thread.sleep(3000);
		System.out.println("执行命令");
		return "success";
	}


	
	/**
	 * 
	 */
	@Override
	protected String getFallback() {
		System.out.println("执行回退命令");
		return "fall back";
	}

	
	
}
