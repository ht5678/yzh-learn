package org.sso.product.service;

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
@ComponentScan(basePackages={"org.simple","org.sso"})
public class SsoProductServerApp {

	public static void main(String[] args) {
		SpringApplication.run(SsoProductServerApp.class,args);
	}
	
}
