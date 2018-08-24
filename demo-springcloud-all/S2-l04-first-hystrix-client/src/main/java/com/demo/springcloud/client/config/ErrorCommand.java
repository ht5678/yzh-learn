package com.demo.springcloud.client.config;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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
 * 
 * @author yuezh2   2018年8月23日 下午8:53:39
 *
 */
public class ErrorCommand extends HystrixCommand<String>{

	
	/**
	 * 
	 * @param group
	 */
	protected ErrorCommand() {
		super(HystrixCommandGroupKey.Factory.asKey("TestGroup"));
	}

	
	
	/**
	 * 
	 */
	@Override
	protected String run() throws Exception {
		String url = "http://localhost:8080/errorHello";
		HttpGet httpget = new HttpGet(url);
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpResponse response = httpclient.execute(httpget);
		String result = EntityUtils.toString(response.getEntity());
		return result;
	}


	/**
	 * 
	 */
	@Override
	protected String getFallback() {
		System.out.println("fallback method");
		return "fallback hello";
	}
	
	

}
