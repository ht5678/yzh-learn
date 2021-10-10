package org.simple.order.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yue
 *
 */
@Component("userDetailsService")
public class DemoUserDetailService implements UserDetailsService{

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoUserDetailService.class);
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("当前登录用户名为 : {}" , username);
		
		return User.builder().username(username)
										.password(passwordEncoder.encode("123456"))
										.authorities("ROLE_ADMIN").build();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
