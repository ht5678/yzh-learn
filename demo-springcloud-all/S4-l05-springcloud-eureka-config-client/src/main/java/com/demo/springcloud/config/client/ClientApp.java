package com.demo.springcloud.config.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 
 * 
 * @author yuezh2   2018年9月6日 下午4:44:58
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class ClientApp {

	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new SpringApplicationBuilder(ClientApp.class).run(args);
	}
	
	
}
