package org.simple.order.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 
 * @author yue
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ResourceServerOrderApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ResourceServerOrderApplication.class, args);
	}
	
}
