package org.sso.auth.jwt.server.config.indb;

import java.security.KeyPair;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
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
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.sso.auth.jwt.server.config.DemoUserDetailService;

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
	private DemoUserDetailService demoUserDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	
	/**
	 * 认证服务器能够给哪些  客户端颁发token , 我们需要把客户端的配置存储到db中
	 * 可以基于内存存储和db存储	
	 * 
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
		//return redisTokenStore;
		
		//db
//		return new JdbcTokenStore(dataSource);
		
		//jwt
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		//jwt
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setKeyPair(keyPair());
//		converter.setSigningKey("123456");
		return converter;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public KeyPair keyPair() {
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
		return keyStoreKeyFactory.getKeyPair("jwt","123456".toCharArray());
	}
	
	
	

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//第三方客户端校验token需要代入clientId 和 clientSecret来校验
		security.checkTokenAccess("isAuthenticated()")
						.tokenKeyAccess("isAuthenticated()");//来获取我们的token key需要带入clientId,clientSecret
		security.allowFormAuthenticationForClients();
	}


	/**
	 * 授权服务器 , 针对用户颁发的token的存储方式
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(demoTokenEnhancer(),jwtAccessTokenConverter()));
		
		endpoints.tokenStore(tokenStore())		//授权服务器颁发的token怎么存储的
					.tokenEnhancer(tokenEnhancerChain)
					.userDetailsService(demoUserDetailService)		//用来获取token的时候需要进行账号密码
					.authenticationManager(authenticationManager);
		
	}
	
	
	
	@Bean
	public DemoTokenEnhancer demoTokenEnhancer() {
		return new DemoTokenEnhancer();
	}
	
	
}
