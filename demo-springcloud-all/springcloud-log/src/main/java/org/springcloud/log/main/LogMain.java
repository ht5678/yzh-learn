package org.springcloud.log.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月28日 上午11:28:20
 *
 */
@SpringBootApplication(scanBasePackages={"org.springcloud.log"})
@EnableEurekaServer
public class LogMain {
	
	
	/**
	 * 
	 * @param args
	 */
    public static void main( String[] args ){
    	new SpringApplication(LogMain.class).run(args);
    }
    
    
}
