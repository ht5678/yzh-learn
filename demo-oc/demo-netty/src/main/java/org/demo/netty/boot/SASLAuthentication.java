package org.demo.netty.boot;

import javax.security.sasl.SaslServer;

import org.demo.netty.domain.Waiter;

import io.netty.util.AttributeKey;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午3:10:01
 */
public class SASLAuthentication {

	public static AttributeKey<SaslServer> saslServer = AttributeKey.valueOf("SaslServer");
	public static AttributeKey<Waiter> WAITER = AttributeKey.valueOf("waiter"); 
	
	public final static String MECHANISM = "mechanism";
	public final static String AUTHENTICATION_VALUE = "authenticationValue";
	
	private static 
	
}
