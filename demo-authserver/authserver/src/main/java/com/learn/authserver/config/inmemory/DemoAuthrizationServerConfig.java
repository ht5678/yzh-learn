package com.learn.authserver.config.inmemory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
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
//@Configuration
//@EnableAuthorizationServer
public class DemoAuthrizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passcodeEncoder;
	
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
	 * 
	 * 表示的资源服务器 , 校验token的时候需要干什么 (这里表示需要带入自己的appid 和 app secret ) 来进行验证
	 * 
	 * @param security
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//获取token key需要登录
		security.checkTokenAccess("isAuthenticated()");
	}

	/**
	 * 方法实现说明 : 作为授权服务器 , 必须知道有哪些第三方app来向我们授权服务器索取令牌 , 
	 * 所以这个方法就是配置 , 哪些第三方app可以来获取我们的令牌 . 
	 * 
	 * @param clients			第三方详情信息
	 * @throws Exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/*
		 * 配置解析:	授权服务器指定客户端(第三方应用) , 能访问授权服务器
		 * 
		 *  为第三方应用颁发客户端 id 为 , 密码为 xxxx
		 *  支持的授权类型为   密码模式(有四种模式 , 后面说)
		 *   颁发的令牌有效期为1小时
		 *   通过该令牌可以访问  哪些资源服务器 (order-service) 可以配置多个
		 *   访问资源服务器的read write权限
		 * 
		 */
		
		clients.inMemory()
							.withClient("portal_app")
							.secret(passcodeEncoder.encode("portal_app"))
							.authorizedGrantTypes("password", "authorization_code")
							.scopes("read")
							.accessTokenValiditySeconds(3600)
							.resourceIds("order-service","product-service")
							.redirectUris("http://www.baidu.com")
						.and()
							.withClient("order_app")
							.secret(passcodeEncoder.encode("order_app"))
							.authorizedGrantTypes("password")
							.scopes("read")
							.accessTokenValiditySeconds(1800)
							.resourceIds("order-service")
						.and()
							.withClient("product_app")
							.secret(passcodeEncoder.encode("product_app"))
							.authorizedGrantTypes("password")
							.scopes("read")
							.accessTokenValiditySeconds(1800)
							.resourceIds("product-service");							
							
	}

	
	
}
