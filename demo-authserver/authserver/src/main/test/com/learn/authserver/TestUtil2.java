package com.learn.authserver;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author yue
 *
 */
public class TestUtil2 {

	
	@Test
	public void test(){
		System.out.println(new BCryptPasswordEncoder().encode("order_app"));
		
	}
	
	
}
