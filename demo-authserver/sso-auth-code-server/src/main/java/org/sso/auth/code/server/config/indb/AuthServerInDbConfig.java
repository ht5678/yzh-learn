package org.sso.auth.code.server.config.indb;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.sso.auth.code.server.config.DemoUserDetailService;

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
	private DemoUserDetailService demoUserDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	
	/**
	 * 第三方客户端存储到sql中
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetails());
	}
	
	
	
	/**
	 * 用于查找我们第三方客户端组件 , 主要用于查找 数据库表  oauth_client_details
	 * @return
	 */
	@Bean
	public ClientDetailsService clientDetails(){
		return new JdbcClientDetailsService(dataSource);
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
		//第三方客户端校验token需要代入clientId 和 clientSecret来校验
		security.checkTokenAccess("isAuthenticated()");
		security.allowFormAuthenticationForClients();
	}


	/**
	 * 授权服务器 , 针对用户颁发的token的存储方式
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore())		//授权服务器颁发的token怎么存储的
					.userDetailsService(demoUserDetailService)		//用来获取token的时候需要进行账号密码
					.authenticationManager(authenticationManager);
		
		//用户通过accessToken来获取用户信息的
		endpoints.tokenServices(defaultTokenServices());
		
	}
	
	
	/**
	 * 认证服务器和资源服务器分离 (就是把用户微服务作为资源服务器的话 , 可以看这篇文章)
	 * 
	 * https://stackoverflow.com/questions/35056169/how-to-get-custom-user-info-from-oauth2-authorization-server-user-endpoint/35092561
	 * 
	 * @return
	 */
	@Primary
	@Bean
	public DefaultTokenServices defaultTokenServices(){
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setClientDetailsService(clientDetails());
		tokenServices.setAccessTokenValiditySeconds(60 * 60 *12);//token有效期自定义配置 , 默认12小时
		tokenServices.setRefreshTokenValiditySeconds(60*60*24*7);
		return tokenServices;
	}
	
	
	
	
}
