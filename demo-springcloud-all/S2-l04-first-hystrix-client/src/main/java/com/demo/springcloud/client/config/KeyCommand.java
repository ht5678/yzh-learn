package com.demo.springcloud.client.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;


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
 * @author yuezh2   2018年8月24日 下午2:02:15
 *
 */
public class KeyCommand extends HystrixCommand<String> {

	
	
	/**
	 * 
	 * @param group
	 */
	protected KeyCommand() {
		super(Setter.withGroupKey(
				HystrixCommandGroupKey.Factory.asKey("TestGroupKey"))			//组名
				.andThreadPoolKey(
						HystrixThreadPoolKey.Factory.asKey("PoolKey"))					//线程key
				.andCommandKey(HystrixCommandKey.Factory.asKey("CommandKey"))	//命令名称
				);
	}

	
	
	/**
	 * 
	 */
	@Override
	protected String run() throws Exception {
		return null;
	}

}
