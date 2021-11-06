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
public class TestGetUserUtil {

	
	public static final ApacheHttpClient HTTPCLIENT = new ApacheHttpClient(10, 5000, 30000, 5 * 1024 * 1024 * 1024, 16, 16);
	
	
	
	
	/**
	 * 
	 */
	@Test
	public void getGetUser(){
		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Content-Type", "application/json");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		//portal_app:portal_app
		headers.put("Authorization", "Basic cG9ydGFsX2FwcDpwb3J0YWxfYXBw");	// 格式    appid:appsecret   然后base64编码
		
		//order_app:order_app  // 只支持password授权模式
//		headers.put("Authorization", "Basic b3JkZXJfYXBwJTNBb3JkZXJfYXBw");	//
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		
		String accessToken = "8be0d847-74b5-4446-b651-8554c03bb0df";
		
		String response = HTTPCLIENT.post("http://localhost:9999/user/getCurrentUser?access_token="+accessToken, params, headers, "UTF8");
		System.out.println(response);
	}
	
	
}
