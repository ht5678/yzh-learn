package org.sso.portal.password.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.sso.portal.password.interceptor.LoginInterceptor;

/**
 * 
 * @author yue
 *
 */
@Configuration
public class PortalWebConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
						.excludePathPatterns("/home.html","/login","/logout")
						.addPathPatterns("/order/createOrder/**","/product/**");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//配置不经过controller直接跳转到url
		registry.addViewController("/home.html").setViewName("home");
		registry.addViewController("/login.html").setViewName("login");
	}

	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	
}
