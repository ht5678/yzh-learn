package org.sso.auth.code.server.config.indb;

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

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月19日下午2:57:45
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerInDbConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	
	/**
	 * 第三方客户端存储到sql中
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}
	
	
	/**
	 * 把token存储到redis中
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		//生产上 , 需要把token存储到redis中或者使用jwt
		//return new RedisTokenStore(redisConnectionFactory);
		return new JdbcTokenStore(dataSource);
		//return redisTokenStore;
	}
	
	
	

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("isAuthenticated()");
	}


	/**
	 * 授权服务器 , 针对用户颁发的token的存储方式
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
	}
	
	
	
	
	
}
