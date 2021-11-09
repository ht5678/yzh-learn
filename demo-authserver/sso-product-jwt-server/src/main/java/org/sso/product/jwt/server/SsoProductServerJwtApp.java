package org.sso.product.jwt.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月9日下午5:58:56
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class SsoProductServerJwtApp {
	
	
    public static void main( String[] args ){
        System.out.println( "Hello World!" );
    }
    
    
}
