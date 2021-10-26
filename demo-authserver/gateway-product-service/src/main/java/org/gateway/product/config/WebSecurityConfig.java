package org.gateway.product.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月26日下午3:16:52
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private RestTemplate restTemplate;
	
	
	@Bean
	public ResourceServerTokenServices resourceServerTokenServices() {
		RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
		remoteTokenServices.setClientId("product_app");
		remoteTokenServices.setClientSecret("product_app");
		remoteTokenServices.setCheckTokenEndpointUrl("http://auth-server/oauth/check_token");
		remoteTokenServices.setRestTemplate(restTemplate);
		
		return remoteTokenServices;
	}
	
	
	
	@Bean
	public AuthenticationManager authenticationManager() {
		OAuth2AuthenticationManager manager = new OAuth2AuthenticationManager();
		manager.setTokenServices(resourceServerTokenServices());
		return manager;
	}
	
}
