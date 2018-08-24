package com.demo.springcloud.client.isolation;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
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
 * @author yuezh2   2018年8月24日 下午3:35:38
 *
 */
public class MyCommand extends HystrixCommand<String>{

	private int index;
	
	
	/**
	 * 
	 * @param group
	 */
	protected MyCommand(int index) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")));
		this.index = index;
	}

	
	/**
	 * 
	 */
	@Override
	protected String run() throws Exception {
		Thread.sleep(500);
		System.out.println("执行run方法,当前索引:"+index);
		return "";
	}


	/**
	 * 
	 */
	@Override
	protected String getFallback() {
		System.out.println("执行fallback方法,当前索引:"+index);
		return "fallback";
	}
	
	
}
