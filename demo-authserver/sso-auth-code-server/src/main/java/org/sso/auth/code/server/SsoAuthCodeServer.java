package org.sso.auth.code.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author yue
 *
 */
@SpringBootApplication
public class SsoAuthCodeServer {

	public static void main(String[] args) {
		SpringApplication.run(SsoAuthCodeServer.class, args);
	}
	
}
