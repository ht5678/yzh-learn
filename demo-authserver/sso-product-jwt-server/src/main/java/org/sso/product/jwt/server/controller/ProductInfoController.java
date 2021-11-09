package org.sso.product.jwt.server.controller;

import org.ms.cloud.common.component.exception.Result;
import org.simple.base.dao.entity.ProductInfo;
import org.simple.base.dao.mapper.ProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月9日下午6:01:28
 */
@RestController
@RequestMapping("/product")
public class ProductInfoController {

	@Autowired
	private ProductInfoMapper productInfoMapper;
	
	
	
	/**
	 * 
	 * http://localhost:8877/product/selectProductInfoById/1
	 * 
	 * 
	 * @param productNo
	 * @param username
	 * @return
	 */
	@RequestMapping("/selectProductInfoById/{productNo}")
	public Result<ProductInfo> selectProductInfoById(@PathVariable("productNo")String productNo , 
																					@RequestHeader("username")String username) {
		System.out.println(username);
		ProductInfo productInfo = productInfoMapper.selectProductInfoById(productNo);
		return Result.success(productInfo);
	}
}
