package org.demo.netty.im.auth;

import org.demo.netty.exception.BSAuthorizeException;
import org.demo.netty.im.bs.listener.AuthorizationListener;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午10:06:10
 */
public class DefaultAuthorizationListener implements AuthorizationListener{

	@Override
	public boolean isAuthorized() throws BSAuthorizeException {
		return false;
	}

	
	
}
