package com.demo.springcloud.jdbc.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月18日 下午5:28:52
 *
 */
@SpringBootApplication(scanBasePackages={"com.demo.springcloud.jdbc"})
@EnableEurekaServer
public class ServerApp {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ServerApp.class, args);
	}

}
