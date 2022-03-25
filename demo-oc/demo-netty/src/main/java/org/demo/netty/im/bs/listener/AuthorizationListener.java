package org.demo.netty.im.bs.listener;

import org.demo.netty.exception.BSAuthorizeException;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午10:04:40
 */
public interface AuthorizationListener {

	/**
	 * 
	 * @return
	 * @throws BSAuthorizeException
	 */
	boolean isAuthorized() throws BSAuthorizeException;
	
}
