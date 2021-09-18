package com.learn.authserver.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class Demo09MsAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Demo09MsAuthServerApplication.class, args);
	}

}
