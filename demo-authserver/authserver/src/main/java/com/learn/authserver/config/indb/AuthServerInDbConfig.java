package com.learn.authserver.config.indb;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月11日上午10:03:17
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerInDbConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private DataSource datasource;
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	/**
	 * 把第三方客户端存储到db中
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(datasource);
	}
	
	
	@Bean
	public TokenStore tokenStore() {
		//生产上 , 需要把token存储到redis中或使用jwt
//		return new RedisTokenStore(redisConnectionFactory);
		return new JdbcTokenStore(datasource);
	}
	

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()");
	}



	/**
	 * 根据SpringSecurity默认的token存储模式 , 改成jwt的
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
//		endpoints.authenticationManager(authenticationManager);
	}
	
	
	
	
}
