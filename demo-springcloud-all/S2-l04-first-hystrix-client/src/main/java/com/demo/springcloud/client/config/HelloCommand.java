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
 * @author yuezh2   2018年8月23日 下午8:05:11
 *
 */
public class HelloCommand extends HystrixCommand<String>{
	
	private String message;
	
	
	/**
	 * 
	 */
	protected HelloCommand(String message) {
		super(HystrixCommandGroupKey.Factory.asKey("TestGroup"));
		this.message = message;
	}

	
	
	/**
	 * 
	 */
	@Override
	protected String run() throws Exception {
		String url = "http://localhost:8080/normalHello";
		HttpGet httpget = new HttpGet(url);
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpResponse response = httpclient.execute(httpget);
		String result = EntityUtils.toString(response.getEntity());
		return result;
	}

	
	
}
