package org.sso.product.service.controller;

import org.simple.base.dao.entity.ProductInfo;
import org.simple.base.dao.mapper.ProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月26日下午3:21:06
 */
@RestController
@RequestMapping("/product")
public class ProductInfoController {

	@Autowired
	private ProductInfoMapper productInfoMapper;
	
	
	/**
	 * 
	 * 	http://localhost:8877/product/selectProductInfoById/1
	 * 
	 * @param productNo
	 * @return
	 */
	@RequestMapping("/selectProductInfoById/{productNo}")
	public Object selectProductInfoById(@PathVariable("productNo")String productNo) {
		ProductInfo productInfo = productInfoMapper.selectProductInfoById(productNo);
		System.out.println(JSON.toJSONString(productInfo));
		return productInfo;
	}
	
}
