package com.demo.springcloud.config.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 
 * 
 * @author yuezh2   2018年8月17日 下午3:17:11
 *
 */
@SpringBootApplication
@EnableEurekaServer
public class ServerApp {
	
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ServerApp.class).web(true).run(args);
	}

}
