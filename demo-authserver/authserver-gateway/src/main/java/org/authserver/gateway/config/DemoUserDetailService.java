package org.authserver.gateway.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月18日下午4:41:58
 */
@Component
public class DemoUserDetailService implements UserDetailsService{

	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
