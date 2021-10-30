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
public class TestRefreshTokenUtils {
	
	
	
	public static final ApacheHttpClient HTTPCLIENT = new ApacheHttpClient(10, 5000, 30000, 5 * 1024 * 1024 * 1024, 16, 16);
	
	
	
	/**
	 * 
	 */
	@Test
	public void test2(){
		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Content-Type", "application/json");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		//portal_app:portal_app
		headers.put("Authorization", "Basic cG9ydGFsX2FwcDpwb3J0YWxfYXBw");	// 格式    client_id:password   然后base64编码
		
		//order_app:order_app  ,  返回结果response为空的场景 , 需要换一个base64编码器
		//db模式 ， 需要修改db中的client secret字段
//		headers.put("Authorization", "Basic b3JkZXJfYXBwOm9yZGVyX2FwcA==");	//
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("refresh_token", "xxx");//refresh token
		params.put("grant_type", "refresh_token");
		
		String response = HTTPCLIENT.post("http://localhost:9999/oauth/token", params, headers, "UTF8");
		System.out.println(response);
	}
	
	
	
	
	/**
	 * 
	 * password模式  ,  获取token和 refresh token
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
