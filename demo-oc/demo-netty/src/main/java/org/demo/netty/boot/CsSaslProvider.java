package org.demo.netty.boot;

import java.security.Provider;

/**
 * sasl将自定义的Mechanism加入provider
 * @author yuezh2
 * @date	  2022年3月14日 下午3:27:49
 */
public class CsSaslProvider extends Provider{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3693754545492809979L;

	
	
	
	protected CsSaslProvider(String name, double version, String info) {
		super("OnlineCall", 1.01, "OnlineCall SASL provider v1.01");
		CsSaslServerFactory factory = new CsSaslServerFactory();
		String[] mechanismNames = factory.getMechanismNames(null);
		
		for(String mechanismName : mechanismNames) {
			put("SaslServerFactory."+mechanismName, factory.getClass().getCanonicalName());
		}
		
	}

	
	
}
