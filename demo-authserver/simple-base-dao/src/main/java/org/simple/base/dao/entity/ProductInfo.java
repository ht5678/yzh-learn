package org.simple.base.dao.entity;

import java.math.BigDecimal;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月26日下午3:23:14
 */
public class ProductInfo {
	
	private String productNo;
	
	private String productName;
	
	private int productStore;
	
	private BigDecimal productPrice;

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductStore() {
		return productStore;
	}

	public void setProductStore(int productStore) {
		this.productStore = productStore;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	
	
}
