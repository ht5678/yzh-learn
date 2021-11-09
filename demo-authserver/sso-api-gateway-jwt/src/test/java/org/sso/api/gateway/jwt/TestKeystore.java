package org.sso.api.gateway.jwt;

import java.security.KeyPair;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月9日下午4:18:39
 */
public class TestKeystore {

	
	
	public static void main(String[] args) throws Exception{
//		System.out.println(new ClassPathResource("jwt.key").contentLength()+"");
		
	    KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
	    
	    KeyPair keyPair = keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
	    System.out.println(keyPair.getPrivate().getEncoded().toString());
	    
	}
		
}
