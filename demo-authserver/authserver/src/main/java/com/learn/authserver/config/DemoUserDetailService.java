package com.learn.authserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 
 * 该类是基于内存 , 后面都会改成db , 需要去数据库查询
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年9月17日下午8:55:40
 */
@Component("userDetailsService")
public class DemoUserDetailService implements UserDetailsService{
	

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoUserDetailService.class);
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("当前的登录用户名为 : {}" , username);
		
		return User.builder().username(username)
										.password(passwordEncoder.encode("123456"))
										.authorities("ROLE_ADMIN")
										.build();
	}

}
