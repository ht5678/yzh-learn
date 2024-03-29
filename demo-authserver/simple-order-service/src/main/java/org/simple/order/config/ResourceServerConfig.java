package org.simple.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 
 * @enableResourceServer  该注解标识 , 我们的order服务是一个资源服务器
 * 并且加上一个过滤器 , Oauth2AuthenticationProcessingFilter  该过滤器就会请求头中解析我们的token
 * 
 * @author yue
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	
	/**
	 * 资源服务器的安全配置
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/order/selectOrderInfoById/**").access("#oauth2.hasScope('read')")//表示资源服务器的该请求, 需要令牌有读权限
				.and()
				.authorizeRequests().antMatchers("/order/saveOrder").access("#oauth2.hasScope('write')")//标识资源服务器的该请求, 需要;令牌有写权限
				.and()
				.authorizeRequests().antMatchers("xixi").permitAll();//表示不需要令牌也可以访问
	}

	
	/**
	 * 资源服务器的标识 , 需要跟授权服务的配置的资源名称一样
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("order-service");
	}

	
	
}
