package com.learn.authserver;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.learn.common.http.ApacheHttpClient;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月10日下午5:46:36
 */
public class TestJwtGatewayUtil {

	
	
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
		String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXV0aG9yaXplLXNlcnZlciIsImFwaS1nYXRld2F5IiwicHJvZHVjdC1zZXJ2aWNlIiwib3JkZXItc2VydmljZSJdLCJwaG9uZSI6bnVsbCwidXNlcl9uYW1lIjoiemhhbmdzYW4iLCJzY29wZSI6WyJyZWFkIl0sImV4cCI6MTYzNjU0MTMzNywidXNlcklkIjoyLCJhdXRob3JpdGllcyI6WyIvb3JkZXIvc2VsZWN0T3JkZXJJbmZvQnlJZEFuZFVzZXJOYW1lIiwiL3Byb2R1Y3Qvc2VsZWN0UHJvZHVjdEluZm9CeUlkIl0sImp0aSI6ImZlNTc5MTljLTdkMjYtNDNjOS05NDdiLTRlNzBiNDg5NDg1OCIsImVtYWlsIjoiemhhbmdzYW5AMTI2LmNvbSIsImNsaWVudF9pZCI6InBvcnRhbF9hcHAifQ.m7zjfIEG3gwiienV_eOz5G10_FdLIksT7i1V2qFrAZb1-ton2TF9oBpjR9x0C2UtUtTGn_2grVZIu6Vj0-8if_7Br0MR9peczKjPkXSfnPgehRRllt5EZSR7qY5ZGQ395mwog3F7WE70E39Lfa195M6461JrfhvbCemr3Q3NJziRxmqgI_WqsyCzgKH23C4bzT1BUTo-MM-DwKfkv6PlcZKXnlt6QNrtcsRhBBFEC3IKCaMQ9s_Lw4RtJaqdDUhV3Td0xeb_3hDAug9ouVwdMBgYhsUCoNzQakdHsSIJNpyQ3ov0c05om3AhR4I2GFF3pA9Su4tibrNhJ6sMt0Zokg\",\"token_type\":\"bearer\",\"refresh_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXV0aG9yaXplLXNlcnZlciIsImFwaS1nYXRld2F5IiwicHJvZHVjdC1zZXJ2aWNlIiwib3JkZXItc2VydmljZSJdLCJwaG9uZSI6bnVsbCwidXNlcl9uYW1lIjoiemhhbmdzYW4iLCJzY29wZSI6WyJyZWFkIl0sImF0aSI6ImZlNTc5MTljLTdkMjYtNDNjOS05NDdiLTRlNzBiNDg5NDg1OCIsImV4cCI6MTYzNjU0MzEzNywidXNlcklkIjoyLCJhdXRob3JpdGllcyI6WyIvb3JkZXIvc2VsZWN0T3JkZXJJbmZvQnlJZEFuZFVzZXJOYW1lIiwiL3Byb2R1Y3Qvc2VsZWN0UHJvZHVjdEluZm9CeUlkIl0sImp0aSI6IjhhN2M2ZjM3LTMwMTEtNDQ0NS1hNDI4LTliOGExMTdkNzQ0NiIsImVtYWlsIjoiemhhbmdzYW5AMTI2LmNvbSIsImNsaWVudF9pZCI6InBvcnRhbF9hcHAifQ.RmxmWuvE3s1-KlsGe_l1XE5drjC8CU77ispoHN-EusU_z8nsN07zhQfHi0cnPbsEb6LBTt0eeVxEv5gDRRjRac4MpZW7zZL8vgYE4T94KpakJDYeiUxglubXpf4_2Jh6Q7ZxLoTyKYC0cBG6tuDbD-jPffZ-moUzhxwnVHbHOwEi6auRhU_DgbMvTkpMpvTuZLqD5jbeShl-ksVh79i6HqTvbtuPmygx-W6Wa4tIr3wbhar4jRbJHhKzLXsL994vlp6L8EEeSP0-sHAsrMyyXB8Vf2MRNHPQpaQhxvW-wfp3tVdkZOdy-1Tanv9ugcSdK2R_AAsY-o_h0TUVROH29Q";
		headers.put("Authorization", "bearer "+jwt);	// token
//		headers.put("userName", "zhangsan");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		//gateway request
		String response = HTTPCLIENT.post("http://localhost:8866/product/selectProductInfoById/1", params, headers, "UTF8");
		System.out.println(response);
	}
	
	
	
	
	
	
	
	/**
	 *	get token 
	 */
	@Test
	public void test() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		//portal_app:portal_app
		headers.put("Authorization", "Basic cG9ydGFsX2FwcDpwb3J0YWxfYXBw");	// 格式    client_id:password   然后base64编码
		
		//order_app:order_app  ,  返回结果response为空的场景 , 需要换一个base64编码器
		//db模式 ， 需要修改db中的client secret字段
//		headers.put("Authorization", "Basic b3JkZXJfYXBwOm9yZGVyX2FwcA==");	//
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", "zhangsan");
		params.put("password", "123456");
		params.put("grant_type", "password");
		params.put("scope", "read");
		
		String response = HTTPCLIENT.post("http://localhost:9999/oauth/token", params, headers, "UTF8");
		System.out.println(response);
	}
	
	
}
