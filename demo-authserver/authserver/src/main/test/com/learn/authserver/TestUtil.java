package com.learn.authserver;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.learn.common.http.ApacheHttpClient;

/**
 * 
 * @author yue
 *
 */
public class TestUtil {
	
	public static final ApacheHttpClient HTTPCLIENT = new ApacheHttpClient(10, 5000, 30000, 5 * 1024 * 1024 * 1024, 16, 16);
	
	
	/**
	 * 授权码模式
	 * 
	 * 不需要校验
	 * 
	 */
	@Test
	public void test5(){
		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Content-Type", "application/json");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		//token
		headers.put("Authorization", "beare 9a4c188b-b5f9-4976-b046-734b4db08377");	// token
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String response = HTTPCLIENT.post("http://localhost:9998/xixi", params, headers, "UTF8");
		System.out.println(response);
	}
	
	
	
	/**
	 * 授权码模式
	 * 
	 * 1.http://localhost:9999/oauth/authorize?response_type=code&client_id=portal_app&redirect_uri=http://www.baidu.com&state=abc
	 * 2.用code换token
	 * 3.请求资源服务器
	 * 
	 */
	@Test
	public void test4(){
		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Content-Type", "application/json");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		//token
		headers.put("Authorization", "bearer 67b74bb4-85f2-4839-8ed0-b1c2fbb4a417");	// token
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String response = HTTPCLIENT.post("http://localhost:9998/order/selectOrderInfoById/222333", params, headers, "UTF8");
		System.out.println(response);
	}
	
	
	
	/**
	 * 授权码模式
	 * 
	 * 1.http://localhost:9999/oauth/authorize?response_type=code&client_id=portal_app&redirect_uri=http://www.baidu.com&state=abc
	 * 2.用code换token
	 * 3.请求资源服务器
	 * 
	 */
	@Test
	public void test3(){
		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Content-Type", "application/json");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		//token
		headers.put("Authorization", "beare 67b74bb4-85f2-4839-8ed0-b1c2fbb4a417");	// token
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String response = HTTPCLIENT.post("http://localhost:8080/selectOneOrderById/1", params, headers, "UTF8");
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
	
	
	
	/**
	 * 
	 * password模式
	 * 
	 * 用户名密码换token
	 * 
	 */
	@Test
	public void test(){
		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Content-Type", "application/json");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		//portal_app:portal_app
//		headers.put("Authorization", "Basic cG9ydGFsX2FwcDpwb3J0YWxfYXBw");	// 格式    client_id:password   然后base64编码
		
		//order_app:order_app  ,  返回结果response为空的场景 , 需要换一个base64编码器
		//db模式 ， 需要修改db中的client secret字段
		headers.put("Authorization", "Basic b3JkZXJfYXBwOm9yZGVyX2FwcA==");	//
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", "zhangsan");
		params.put("password", "123456");
		params.put("grant_type", "password");
		params.put("scope", "read");
		
		String response = HTTPCLIENT.post("http://localhost:9999/oauth/token", params, headers, "UTF8");
		System.out.println(response);
	}
	
}
