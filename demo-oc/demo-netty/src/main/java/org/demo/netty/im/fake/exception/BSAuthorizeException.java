package org.demo.netty.im.fake.exception;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午10:05:22
 */
public class BSAuthorizeException extends Exception{

	private static final long serialVersionUID = 1L;
	public BSAuthorizeException() {
		super();
	}
	
	public BSAuthorizeException(String message) {
		super(message);
	}
	
	public BSAuthorizeException(Throwable throwable) {
		super(throwable);
	}
	
	public BSAuthorizeException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
