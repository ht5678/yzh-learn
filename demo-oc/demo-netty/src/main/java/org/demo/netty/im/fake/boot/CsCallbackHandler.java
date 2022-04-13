package org.demo.netty.im.fake.boot;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.AuthorizeCallback;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午5:33:59
 */
public class CsCallbackHandler implements CallbackHandler{
	
	
	

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for(Callback callback : callbacks) {
			if(callback instanceof AuthorizeCallback) {
				
			}else {
				throw new UnsupportedCallbackException(callback , "无法访问callback");
			}
		}
	}

}
