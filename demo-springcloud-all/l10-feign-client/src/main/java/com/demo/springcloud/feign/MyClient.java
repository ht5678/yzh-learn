package com.demo.springcloud.feign;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import feign.Client;
import feign.Request;
import feign.Request.Options;
import feign.Response;

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
 * @author yuezh2   2018年8月22日 下午4:13:32
 *
 */
public class MyClient implements Client{

	
	
	@Override
	public Response execute(Request request, Options options) throws IOException {
		System.out.println("this is my client");
		
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			final String method = request.method();
			
			HttpRequestBase httpRequest = new HttpRequestBase() {
				
				@Override
				public String getMethod() {
					return method;
				}
			};
			
			
			httpRequest.setURI(new URI(request.url()));
			
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			byte[] body = EntityUtils.toByteArray(httpResponse.getEntity());
			
			Response response = Response.builder()
					.body(body)
					.headers(new HashMap<String,Collection<String>>())
					.status(httpResponse.getStatusLine().getStatusCode())
					.build();
			
			return response;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	
}
