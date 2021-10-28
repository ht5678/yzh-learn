package org.gateway.order.service.controller;

import java.util.Date;

import org.simple.base.dao.entity.OrderInfo;
import org.simple.base.dao.mapper.OrderInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月19日下午3:18:00
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	
	@Qualifier("orderInfoMapper")
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	
	@RequestMapping("/selectOrderInfoByIdAndUserName/{orderNo}")
	public Object selectOrderInfoById(@PathVariable("orderNo")String orderNo , @RequestHeader("userName")String userName) {
		LOGGER.info("username : {}",userName);
		
		OrderInfo orderInfo = orderInfoMapper.selectOrderInfoByIdAndUserName(orderNo, userName);
		if(null == orderInfo) {
			return "根据orderNo:"+orderNo+"查询没有订单";
		}
		return orderInfo;
	}
	
	
	
	@RequestMapping("/saveOrder")
	public Object saveOrder(OrderInfo orderInfo) {
		orderInfo.setCreate_dt(new Date());
		orderInfo.setOrderNo("xxx");
		orderInfo.setProductNo("1");
		LOGGER.info("保存订单:{}",JSON.toJSONString(orderInfo));
		return orderInfo;
	}
	
}
