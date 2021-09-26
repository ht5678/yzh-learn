package org.simple.order.service.controller;

import org.simple.base.dao.entity.OrderInfo;
import org.simple.base.dao.mapper.OrderInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author yue
 *
 */
@RestController
@RequestMapping("/order")
public class OrderInfoController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderInfoController.class);
	
	
	
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	
	
	@RequestMapping("/selectOrderInfoById/{orderNo}")
	public Object selectOrderInfoById(@PathVariable("orderNo") String orderNo ,  String userName){
		LOGGER.info("username : {}" , userName);
		OrderInfo orderInfo = orderInfoMapper.selectOrderInfoByIdAndUserName(orderNo, userName);
		if(null == orderInfo){
			return "根据orderNo:"+orderNo+"  查询没有该订单";
		}
		return orderInfo;
	}
	
	
	@RequestMapping("saveOrder")
	public Object saveOrder(@RequestBody OrderInfo orderInfo){
		LOGGER.info("保存订单:{}" , orderInfo.toString());
		return orderInfo;
	}
	
}
