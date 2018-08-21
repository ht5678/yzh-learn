package com.demo.springcloud.first;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.ZoneAwareLoadBalancer;


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
		String json = tpl.getForObject("http://ek-police/call/1", String.class);
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
	
	
	
	
	@Autowired
	private LoadBalancerClient client;
	
	
	/**
	 * 失败的话请多次请求，会成功的 ， 根源在于负载的8081端口没有启动实力
	 * @return
	 */
	@GetMapping(value="lb")
	@ResponseBody
	public ServiceInstance lb(){
		ServiceInstance si = client.choose("ek-police");
		System.out.println(si.getUri());
		return si;
	}
	
	
	
	@Autowired
	private SpringClientFactory factory;
	
	@RequestMapping(value="fa",method=RequestMethod.GET)
	@ResponseBody
	public String factory(){
		ZoneAwareLoadBalancer lb = (ZoneAwareLoadBalancer)factory.getLoadBalancer("default");
		System.out.println(lb.getRule().getClass().getName());
		
		ZoneAwareLoadBalancer lb2 = (ZoneAwareLoadBalancer)factory.getLoadBalancer("ek-police");
		System.out.println(lb2.getRule().getClass().getName());
		return "";
	}
	
	
}
