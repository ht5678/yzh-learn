package org.demo.netty.boot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;
import javax.security.sasl.SaslServerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午3:30:48
 */
public class CsSaslServerFactory implements SaslServerFactory{
	
	private static Logger log = LoggerFactory.getLogger(CsSaslServerFactory.class);
	
	private static Set<Mechanism> mechanisms = new HashSet<>();

	static {
		mechanisms.add(new Mechanism("ANONYMOUS", true, true));
		mechanisms.add(new Mechanism("PLAIN", false, true));
	}
	
	

	@Override
	public SaslServer createSaslServer(String mechanism, String protocol, String serverName, Map<String, ?> props,
			CallbackHandler cbh) throws SaslException {
		
		if(!Arrays.asList(getMechanismNames(props)).contains(mechanism)) {
			log.debug("当前实现不包含此认证原理 : {}" , mechanism);
			return null;
		}
		
		switch (mechanism.toUpperCase()) {
		case "ANONYMOUS":
			return new AnonymousSaslServer(props);
		case "PLAIN":
			return new PlainSaslServer(props);
		default:
			throw new IllegalStateException();
		}
	}

	@Override
	public String[] getMechanismNames(Map<String, ?> props) {
		Set<String> result = new HashSet<>();
		for(Mechanism mechanism : mechanisms) {
			if(null != props) {
				//在设置 '非匿名' 策略 , 不要包含允许匿名登录身份验证的机制
				if(mechanism.allowsAnonymous && props.containsKey(Sasl.POLICY_NOANONYMOUS) && Boolean.parseBoolean((String)props.get(Sasl.POLICY_NOANONYMOUS))) {
					continue;
				}
				
				//在设置 '无明文' 策略时 , 不要包含容易收到简单的纯被动机制
				if(mechanism.plainText && props.containsKey(Sasl.POLICY_NOPLAINTEXT) && Boolean.parseBoolean((String)props.get(Sasl.POLICY_NOPLAINTEXT))) {
					continue;
				}
			}
			
			result.add(mechanism.name);
		}
		return result.toArray(new String[result.size()]);
	}

	
	
	
	
	private static class Mechanism {
		final String name;
		final boolean allowsAnonymous;
		final boolean plainText;
		
		private Mechanism(String name , boolean allowsAnonymous ,  boolean plainText) {
			this.name = name;
			this.allowsAnonymous = allowsAnonymous;
			this.plainText = plainText;
		}
	}
	
}
