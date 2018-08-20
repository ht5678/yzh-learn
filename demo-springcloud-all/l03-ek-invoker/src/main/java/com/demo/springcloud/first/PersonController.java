package com.demo.springcloud.first;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


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
 * @author yuezh2   2018年8月17日 下午4:40:22
 *
 */
@Controller
@Configuration
public class PersonController {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	
	
	/**
	 * 或者
	 * 
	 * 
	 *	@Autowired
	 *	private RestTemplate tempalte; 
	 * 
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
	
	@GetMapping(value="list")
	@ResponseBody
	public String serviceCount(){
		List<String> names = discoveryClient.getServices();
		for(String serviceId : names){
			List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
			System.out.println(serviceId+": "+instances.size());
		}
		return "";
	}
	
	
	
	@GetMapping(value="router")
	@ResponseBody
	public String router(){
		RestTemplate tpl = getRestTemplate();
		//host:first-police是在police项目里的application.yml设置的
		String json = tpl.getForObject("http://first-police/call/1", String.class);
		return json;
	}
	
	
	@GetMapping(value="meta")
	@ResponseBody
	public String getMetaData(){
		List<ServiceInstance> instances = discoveryClient.getInstances("ek-police");
		for(ServiceInstance ins : instances){
			String name = ins.getMetadata().get("company-name");
			System.out.println(ins.getPort()+"---"+name);
		}
		return "";
	}
	
}
