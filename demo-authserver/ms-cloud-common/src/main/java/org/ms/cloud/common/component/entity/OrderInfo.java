package org.ms.cloud.common.component.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author yue
 *
 */
public class OrderInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4479226354561102432L;

	
	
	private String orderNo;
	
	private String userName;
	
	private Date create_dt;
	
	private String productNo;
	
	private int productCount;
	
	
	

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreate_dt() {
		return create_dt;
	}

	public void setCreate_dt(Date create_dt) {
		this.create_dt = create_dt;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	
	
}
