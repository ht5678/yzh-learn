package org.sso.portal.password.entity;

import java.math.BigDecimal;

/**
 * 
 * @author yue
 *
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
