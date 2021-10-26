package org.gateway.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月26日下午3:09:53
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DemoResourceServerProductApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(DemoResourceServerProductApplication.class, args);
	}
	
}
