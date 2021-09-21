package com.learn.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 
 * 1 . auth server , get code 
 * http://localhost:9999/oauth/authorize?response_type=code&client_id=portal_app&redirect_uri=http://www.baidu.com&state=abc
 * 
 * 
 * @author yue
 *
 */
@SpringBootApplication
public class Demo09MsAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Demo09MsAuthServerApplication.class, args);
	}

}
