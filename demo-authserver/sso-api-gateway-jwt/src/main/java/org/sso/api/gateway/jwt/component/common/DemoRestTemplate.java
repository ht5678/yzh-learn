package org.sso.api.gateway.jwt.component.common;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 根据rest template特性自己改造
 * @author yuezh2@lenovo.com
 *	@date 2021年11月10日下午4:10:46
 */
public class DemoRestTemplate extends RestTemplate{
	
	private DiscoveryClient discoveryClient;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoRestTemplate.class);
	
	
	
	public DemoRestTemplate(DiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}
	
	
	/**
	 * 
	 */
	protected <T> T doExecute(URI url  , @Nullable HttpMethod method , @Nullable RequestCallback requestCallback , 
										@Nullable ResponseExtractor<T>  responseExtractor)throws RestClientException {
		Assert.notNull(url , "URI is required");
		Assert.notNull(method , "HttpMethod is required");
		
		ClientHttpResponse response = null;
		try {
			//判断url的拦截路径 , 然后去redis(作为注册中心)获取地址随机选取一个
			LOGGER.info("请求url的路径为 : {}" , url);
			url = replaceUrl(url);
			LOGGER.info("替换后的路径 : {}"  ,  url);
			ClientHttpRequest request = createRequest(url, method);
			if(requestCallback!=null) {
				requestCallback.doWithRequest(request);
			}
			response = request.execute();
			handleResponse(url, method, response);
			
			return (responseExtractor != null ? responseExtractor.extractData(response) : null); 
			
		}catch(IOException e) {
			String resource = url.toString();
			String query = url.getRawQuery();
			resource = (query!=null ? resource.substring(0 , resource.indexOf('?')) : resource);
			throw new ResourceAccessException("I/O error on " + method.name() + " request for\""+ resource + "\": " + e.getMessage() ,e);
		} finally {
			if(response != null) {
				response.close();
			}
		}
	}
	
	
	/**
	 * 把服务实例替换成ip : 端口
	 * @param url
	 * @return
	 */
	private URI replaceUrl(URI url) {
		//解析我们的微服务名称
		String sourceUrl = url.toString();
		String[] httpUrl = sourceUrl.split("//");
		int index = httpUrl[1].replaceFirst("/", "@").indexOf("@");
		String serviceName = httpUrl[1].substring(0 , index);
		
		//通过微服务名称去nacos服务端获取  对应的实例列表
		List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(serviceName);
		if(serviceInstanceList.isEmpty()) {
			throw new RuntimeException("没有可用的微服务实例列表 : "+serviceName);
		}
		
		//采取随机的获取一个
		Random random = new Random();
		Integer randomIndex = random.nextInt(serviceInstanceList.size());
		LOGGER.info("随机下标: {}" , randomIndex);
		String serviceIp = serviceInstanceList.get(randomIndex).getUri().toString();
		LOGGER.info("随机选举的服务ip:{}" , serviceIp);
		String targetSource = httpUrl[1].replace(serviceName, serviceIp);
		try {
			return new URI(targetSource);
		}catch(URISyntaxException e) {
			e.printStackTrace();
		}
		return url;
	}

}
