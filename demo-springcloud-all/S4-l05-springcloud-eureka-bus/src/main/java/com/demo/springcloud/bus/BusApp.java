package com.demo.springcloud.bus;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月6日 下午9:09:16
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class BusApp {

	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new SpringApplicationBuilder(BusApp.class).run(args);
	}
	
}
