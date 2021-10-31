package org.sso.portal.code.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.sso.portal.code.interceptor.CookieLoginInterceptor;
import org.sso.portal.code.interceptor.LoginInterceptor;

/**
 * web安全配置
 * @author yue
 *
 */
@Configuration
public class PortalWebConfig implements WebMvcConfigurer{
	
	//redis  session
	@Autowired
	private LoginInterceptor loginInterceptor;
	
	//cookie  session
//	@Autowired
//	private CookieLoginInterceptor cookieLoginInterceptor;
	

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
						.excludePathPatterns("/home.html","/login","/logout")
						.addPathPatterns("/order/createOrder/**","/product/**");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//配置不经过controller直接跳转到url
		registry.addViewController("/home.html").setViewName("home");
	}

	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
}
