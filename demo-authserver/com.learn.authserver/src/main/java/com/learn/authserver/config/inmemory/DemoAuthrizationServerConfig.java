package com.learn.authserver.config.inmemory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年9月17日下午5:41:15
 */
@Configuration
@EnableAuthorizationServer
public class DemoAuthrizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	/**
	 * 方法实现说明 : 认证服务器 , 需要知道 , 是哪个用户来访问资源服务器 , 
	 * 	所以该方法是用来验证用户信息的 . 
	 * 
	 * @param endpoints		认证服务器的 , 识别用户信息的配置 , 
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//这里 , 认证服务器委托一个authenticationManager 来验证我们的信息
		//authenticationManager是怎么来的
		endpoints.authenticationManager(authenticationManager);
	}

	/**
	 * 方法实现说明 :
	 * @param security
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// TODO Auto-generated method stub
		super.configure(security);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO Auto-generated method stub
		super.configure(clients);
	}

	
	
}
