package org.demo.netty.exception;

import javax.security.sasl.SaslException;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午4:52:07
 */
public class SaslFailureException extends SaslException {

	private static final long serialVersionUID = -4965040934998529695L;
	
	public String msg;
	
	public SaslFailureException(String message, String msg) {
		super(message);
		this.msg = msg;
	}
}