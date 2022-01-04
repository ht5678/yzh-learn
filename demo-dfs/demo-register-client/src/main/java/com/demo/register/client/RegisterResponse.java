package com.demo.register.client;


/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2022年1月4日下午6:33:32
 */
public class RegisterResponse {
	
	public static final String SUCCESS = "success";
	
	public static final String FAILTURE = "failure";
	
	

	private String status;
	
	private Integer code;
	
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
