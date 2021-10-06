package org.simple.base.dao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * 
 * @author yue
 *
 */
@SpringBootApplication
public class DemoDaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDaoApplication.class, args);
	}
	
}
