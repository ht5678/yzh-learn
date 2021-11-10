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
		headers.put("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXV0aG9yaXplLXNlcnZlciIsImFwaS1nYXRld2F5IiwicHJvZHVjdC1zZXJ2aWNlIiwib3JkZXItc2VydmljZSJdLCJwaG9uZSI6bnVsbCwidXNlcl9uYW1lIjoiemhhbmdzYW4iLCJzY29wZSI6WyJyZWFkIl0sImV4cCI6MTYzNjUzOTQ5OSwidXNlcklkIjoyLCJhdXRob3JpdGllcyI6WyIvb3JkZXIvc2VsZWN0T3JkZXJJbmZvQnlJZEFuZFVzZXJOYW1lIiwiL3Byb2R1Y3Qvc2VsZWN0UHJvZHVjdEluZm9CeUlkIl0sImp0aSI6Ijc3ZTg3NDhkLWZiMGYtNDFhYy04MjdmLTYxOTEwMTcwZjliOCIsImVtYWlsIjoiemhhbmdzYW5AMTI2LmNvbSIsImNsaWVudF9pZCI6InBvcnRhbF9hcHAifQ.KfQLHrpnlNP44hWXAtE9JGgQjAnWpe3ZOD8KZyInNsN2t-ozn-6jnuITTcduDiQRAf9vw_MtWrGY6l1oQ3Qkl6dOjyFuusJQmWnYq9FU3aeXRKHb8rf11qSQr9nl8ukAnEoiAfKQBNVTM7g5fpxPbL8pLKk2X-CMTOBUFbvNG7PkCNB5O9b8nLWoPeHa2z0-9qBY0_4IKMkFkVbQFCNYdsw0DplnCQu0_8UD1rriFJD-9SZr1tmPsio45HxmKfWgWNNV-gDbKvr4GHEttT3WTZl3M7x0t4_3keiThd_XyUuzbj1Jp3fsIomsMrrSxPlpY-3zNxtP2ofi8Z9TuP5ZmQ\",\"token_type\":\"bearer\",\"refresh_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXV0aG9yaXplLXNlcnZlciIsImFwaS1nYXRld2F5IiwicHJvZHVjdC1zZXJ2aWNlIiwib3JkZXItc2VydmljZSJdLCJwaG9uZSI6bnVsbCwidXNlcl9uYW1lIjoiemhhbmdzYW4iLCJzY29wZSI6WyJyZWFkIl0sImF0aSI6Ijc3ZTg3NDhkLWZiMGYtNDFhYy04MjdmLTYxOTEwMTcwZjliOCIsImV4cCI6MTYzNjU0MTI5OSwidXNlcklkIjoyLCJhdXRob3JpdGllcyI6WyIvb3JkZXIvc2VsZWN0T3JkZXJJbmZvQnlJZEFuZFVzZXJOYW1lIiwiL3Byb2R1Y3Qvc2VsZWN0UHJvZHVjdEluZm9CeUlkIl0sImp0aSI6IjVkZjY5MmM4LTYxNTEtNDAyMC05ZmZlLWQwN2U5NDUwYzYyYyIsImVtYWlsIjoiemhhbmdzYW5AMTI2LmNvbSIsImNsaWVudF9pZCI6InBvcnRhbF9hcHAifQ.S57e7wxlMi_QoB1cErYBfzThI_MPBU8e1IU0SlCt46vfBufStu_faHjNgnloXtveluG5Y4irK74zrAru-6MdwzZfWSmB04AYWCKgoftteFsjYWr-4_fGiVrG5Fd1fSLU4YmhwZHu3UbOv1zX-QwVljeeQBo2NXInBrjhhL8lN4tCEv4De8M0rGvtPb8eTgWNwILWvSaaDV8WOfRk_iNB3A2AOzlq1z4c0p-Yv_x1j9OeU52c25awE6bCSyxl69htJEahdWHT1fT4MmRQmuoRmMsUo-yzGiOrrblAUwc6oYCxpBfR47vns6UtX5lSuc2eDTnfUSHx709n8V02P-kE0A");	// token
//		headers.put("userName", "zhangsan");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		//gateway request
		String response = HTTPCLIENT.post("http://localhost:8877/product/selectProductInfoById/1", params, headers, "UTF8");
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
