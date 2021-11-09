package com.learn.authserver;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author yue
 *
 */
public class TestEncodeUtil {

	
	@Test
	public void test(){
		String str = new BCryptPasswordEncoder().encode("api-gateway");
		System.out.println(str);
	}
	
	
}
