package org.demo.netty.domain;



/**
 * 
 * @author yuezh2
 * @date	  2022年3月22日 下午5:11:15
 */
public class Properties {

	private Integer id;
	
	private String tenantCode;
	
	private String timeoutTipMessage;
	
	private String timeoutCloseMessage;
	
	
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getTimeoutTipMessage() {
		return timeoutTipMessage;
	}

	public void setTimeoutTipMessage(String timeoutTipMessage) {
		this.timeoutTipMessage = timeoutTipMessage;
	}

	public String getTimeoutCloseMessage() {
		return timeoutCloseMessage;
	}

	public void setTimeoutCloseMessage(String timeoutCloseMessage) {
		this.timeoutCloseMessage = timeoutCloseMessage;
	}
	
	
	
	
}
