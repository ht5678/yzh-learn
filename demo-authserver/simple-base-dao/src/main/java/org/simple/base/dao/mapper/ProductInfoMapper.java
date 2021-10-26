package org.simple.base.dao.mapper;

import org.simple.base.dao.entity.ProductInfo;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月26日下午3:22:50
 */
public interface ProductInfoMapper {

	
	
	public ProductInfo selectProductInfoById(String productNo);
	
}
