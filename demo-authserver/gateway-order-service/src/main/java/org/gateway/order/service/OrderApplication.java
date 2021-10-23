package org.gateway.order.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月19日下午3:16:59
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages={"org.simple","org.gateway"})
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
	
}
