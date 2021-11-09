package org.sso.api.gateway.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月9日下午3:17:16
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SsoApiGateWayJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoApiGateWayJwtApplication.class, args);
	}
	
}
