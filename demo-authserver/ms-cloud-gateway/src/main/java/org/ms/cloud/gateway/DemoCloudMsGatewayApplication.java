package org.ms.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月19日下午6:17:00
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DemoCloudMsGatewayApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(DemoCloudMsGatewayApplication.class, args);
	}
	
}
