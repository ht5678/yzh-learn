package org.simple.order.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年9月27日下午5:49:21
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private RestTemplate restTemplate;
	
	
	
	/**
	 * 资源服务器   要去资源服务器校验
	 * @return
	 */
//	@Bean
//	public ResourceServerTokenServices resourceServerTokenServices() {
//		RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//		
//	}
	
}
