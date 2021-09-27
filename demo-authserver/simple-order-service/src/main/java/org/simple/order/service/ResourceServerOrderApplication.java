package org.simple.order.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author yue
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages="org.simple")
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })// or   @EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class ResourceServerOrderApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ResourceServerOrderApplication.class, args);
	}
	
}
