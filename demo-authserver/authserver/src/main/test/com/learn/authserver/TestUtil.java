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
	

	@Test
	public void test(){
		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Content-Type", "application/json");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		//portal_app:portal_app
		headers.put("Authorization", "Basic cG9ydGFsX2FwcDpwb3J0YWxfYXBw");	// 格式    client_id:password   然后base64编码
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", "zhangsan");
		params.put("password", "123456");
		params.put("grant_type", "password");
		params.put("scope", "read");
		
		String response = HTTPCLIENT.post("http://localhost:9999/oauth/token", params, headers, "UTF8");
		System.out.println(response);
	}
	
}
