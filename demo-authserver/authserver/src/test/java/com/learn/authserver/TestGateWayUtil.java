package com.learn.authserver;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.learn.common.http.ApacheHttpClient;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月21日下午3:21:52
 */
public class TestGateWayUtil {

	public static final ApacheHttpClient HTTPCLIENT = new ApacheHttpClient(10, 5000, 30000, 5 * 1024 * 1024 * 1024, 16, 16);
	
	
	
	/**
	 * 
	 * 请求gateway , 然后网关进行转发
	 * 
	 */
	@Test
	public void test4(){
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		//token
		headers.put("Authorization", "bearer 67b74bb4-85f2-4839-8ed0-b1c2fbb4a417");	// token
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String response = HTTPCLIENT.post("http://localhost:8888/order/selectOrderInfoById/222333", params, headers, "UTF8");
		System.out.println(response);
	}
	
	
	
	
	/**
	 * 授权码模式
	 * 
	 * 1.  http://localhost:9999/oauth/authorize?response_type=code&client_id=portal_app&redirect_uri=http://www.baidu.com&state=abc
	 * 2.  用code换token
	 * 
	 */
	@Test
	public void test2(){
		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Content-Type", "application/json");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		//portal_app:portal_app
		headers.put("Authorization", "Basic cG9ydGFsX2FwcDpwb3J0YWxfYXBw");	// 格式    appid:appsecret   然后base64编码
		
		//order_app:order_app  // 只支持password授权模式
//		headers.put("Authorization", "Basic b3JkZXJfYXBwJTNBb3JkZXJfYXBw");	//
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", "2q2YOe");
		params.put("grant_type", "authorization_code");
		params.put("redirect_uri", "http://www.baidu.com");
		
		
		String response = HTTPCLIENT.post("http://localhost:9999/oauth/token", params, headers, "UTF8");
		System.out.println(response);
	}
}
