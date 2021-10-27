package org.gateway.product.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年9月27日下午5:37:05
 */
//@Configuration
//@EnableWebSecurity
public class NoAuthWebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//所有请求均放过, spring security失效
		//anyRequest() 限定任意的请求
		//permitAll()  无条件允许访问
		http.authorizeRequests().anyRequest().permitAll();
	}
	
	
	

}
