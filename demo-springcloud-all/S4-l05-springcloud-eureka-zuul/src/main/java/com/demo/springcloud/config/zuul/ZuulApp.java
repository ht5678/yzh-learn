package com.demo.springcloud.config.zuul;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月6日 下午6:19:58
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulApp {

	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new SpringApplicationBuilder(ZuulApp.class).run(args);
	}
}
