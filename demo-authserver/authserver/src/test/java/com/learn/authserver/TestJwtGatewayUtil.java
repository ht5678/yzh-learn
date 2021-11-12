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
	 * refresh  jwt  token
	 * 
	 */
	@Test
	public void testRefreshJwtToken(){
		Map<String, String> headers = new HashMap<String, String>();
//		headers.put("Content-Type", "application/json");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		//portal_app:portal_app
		headers.put("Authorization", "Basic cG9ydGFsX2FwcDpwb3J0YWxfYXBw");	// 格式    client_id:password   然后base64编码
		
		//order_app:order_app  ,  返回结果response为空的场景 , 需要换一个base64编码器
		//db模式 ， 需要修改db中的client secret字段
//		headers.put("Authorization", "Basic b3JkZXJfYXBwOm9yZGVyX2FwcA==");	//
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("refresh_token", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXV0aG9yaXplLXNlcnZlciIsImFwaS1nYXRld2F5IiwicHJvZHVjdC1zZXJ2aWNlIiwib3JkZXItc2VydmljZSJdLCJwaG9uZSI6bnVsbCwidXNlcl9uYW1lIjoiemhhbmdzYW4iLCJzY29wZSI6WyJyZWFkIl0sImF0aSI6ImQwYTUxOTgwLTEzMTctNGRjYS05MmY5LTVjYWE2MmUyOGM3NCIsImV4cCI6MTYzNjcwNjIwOSwidXNlcklkIjoyLCJhdXRob3JpdGllcyI6WyIvb3JkZXIvc2VsZWN0T3JkZXJJbmZvQnlJZEFuZFVzZXJOYW1lIiwiL3Byb2R1Y3Qvc2VsZWN0UHJvZHVjdEluZm9CeUlkIl0sImp0aSI6IjM5NjZlN2NjLTk5MTItNDhjOC1hMWQxLTk1ZTEzYTI3MGEyNyIsImVtYWlsIjoiemhhbmdzYW5AMTI2LmNvbSIsImNsaWVudF9pZCI6InBvcnRhbF9hcHAifQ.F46Ou3f5fT8X1iOJDihP5nt_VDZ0XQz80QhDE0dWZ33X3x6i6YBfo8zcm6zJyLSoGFZ2A5JOJ6VlOE3eedEUeAP61y9Bu70xZbGpRJievDH9IpLQPT_2e9hiRTLOd-hcCpZFgFkiXZUYVlfwlpADHXd_pMNJkdn-4WzTl3nTQt9vKqQpc8UMeg9v_8OfbzFKTUVyLNBnpi4ckrShFnuht5WG4tgmjlByHFPeu6F3z240XJz3jjmVFW52Uz7K93iHYRJ_JdzabusgfeEbtzr2LkkLAv6puGqQWMq-fUk__ASlWJFXTgYFslnm-OsrxICuUMPNt5kaUdFTqmTdb6s05g");//refresh token
		params.put("grant_type", "refresh_token");
		
		String response = HTTPCLIENT.post("http://localhost:9999/oauth/token", params, headers, "UTF8");
		System.out.println(response);
	}
	
	
	
	
	
	/**
	 * 
	 * 请求gateway , 然后网关进行转发
	 * 
	 */
	@Test
	public void testGetProductResource(){
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		//token
		String jwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXV0aG9yaXplLXNlcnZlciIsImFwaS1nYXRld2F5IiwicHJvZHVjdC1zZXJ2aWNlIiwib3JkZXItc2VydmljZSJdLCJwaG9uZSI6bnVsbCwidXNlcl9uYW1lIjoiemhhbmdzYW4iLCJzY29wZSI6WyJyZWFkIl0sImV4cCI6MTYzNjcwNDQwOSwidXNlcklkIjoyLCJhdXRob3JpdGllcyI6WyIvb3JkZXIvc2VsZWN0T3JkZXJJbmZvQnlJZEFuZFVzZXJOYW1lIiwiL3Byb2R1Y3Qvc2VsZWN0UHJvZHVjdEluZm9CeUlkIl0sImp0aSI6ImQwYTUxOTgwLTEzMTctNGRjYS05MmY5LTVjYWE2MmUyOGM3NCIsImVtYWlsIjoiemhhbmdzYW5AMTI2LmNvbSIsImNsaWVudF9pZCI6InBvcnRhbF9hcHAifQ.LDImx3DzLw55VxRD9zVj2QvINHeRVu11T5eH7vcOWK-vjeFW6YUyzNT4Rtd5HnM7C4WD5jELyhHDSv-ic6gGgskVJU3Zd-EHyyAWtq1oyPme_pDnNSCOdqCfiD3qFAdLCv6NcEOkb_VoaMYMWFuhddE2fO-xI03Zphiot-QT2rFNE7jnUfzh7No3TlS1MYahJe7D7XwghwOurt_UqlEAGN1mXU5vyZeE_do51h2Bi-mtc3ok8PuQBJC2amFabYR6FhoUIkvufQ0FBKQMUF5YsK5PWOFUAAJqUk8TF4zojW17x3kkMMt-wdp62-13r64hNuPBHsece9PUvg3oIjmXdA";
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
