package org.ms.cloud.gateway.component.exception;


/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月19日下午9:29:27
 */
public class GateWayException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1372002383909939882L;
	
	

	private String code;
	
	private String msg;
	
	
	public GateWayException(SystemErrorType systemErrorType) {
		this.code = systemErrorType.getCode();
		this.msg = systemErrorType.getMsg();
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
