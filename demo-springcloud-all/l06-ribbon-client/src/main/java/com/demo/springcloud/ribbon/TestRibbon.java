package com.demo.springcloud.ribbon;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.niws.client.http.RestClient;

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
 * @author yuezh2   2018年8月21日 上午11:00:51
 *
 */
public class TestRibbon {

	
	/**
	 * 测试的时候要启动 ribbon_service 里边的serviceApp , 端口号是8080和8081
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		//配置文件配置
		ConfigurationManager.loadPropertiesFromResources("sample-client.properties");  // 1
	    System.out.println(ConfigurationManager.getConfigInstance().getProperty("sample-client.ribbon.listOfServers"));
	    
	    //硬编码配置
		ConfigurationManager.getConfigInstance().setProperty("my-client.ribbon.listOfServers", "localhost:8080,localhost:8081");
		RestClient client = (RestClient)ClientFactory.getNamedClient("my-client");
		HttpRequest request = HttpRequest.newBuilder().uri("/person").build();
		
		for(int i = 0 ; i < 10 ; i ++){
			HttpResponse response = client.executeWithLoadBalancer(request);
			String json = response.getEntity(String.class);
			System.out.println(json);
		}
	}
	
	
}
