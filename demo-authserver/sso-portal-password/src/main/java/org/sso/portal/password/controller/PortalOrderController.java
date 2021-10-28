package org.sso.portal.password.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.ms.cloud.common.component.exception.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.sso.portal.password.config.MDA;
import org.sso.portal.password.entity.OrderInfo;
import org.sso.portal.password.entity.TokenInfo;
import org.sso.portal.password.vo.BuyVo;
import org.sso.portal.password.vo.OrderInfoVo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @author yue
 *
 */
@Controller
public class PortalOrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PortalOrderController.class);
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@RequestMapping("/order/createOrder")
	@ResponseBody
	public OrderInfo saveOrder(@RequestBody BuyVo buyVo , HttpServletRequest request) throws JsonProcessingException{
		try{
			
			HttpEntity httpEntity = wrapRequest(request, buyVo);
			
			ResponseEntity<OrderInfo> responseEntity = restTemplate.exchange(MDA.ORDER_SERVER_CREATEORDER, HttpMethod.POST
									, httpEntity, new ParameterizedTypeReference<OrderInfo>() { });
			
			return responseEntity.getBody();
			
		}catch(Exception e){
			LOGGER.warn("创建订单异常...");
//			return Result.fail();
			return new OrderInfo();
		}
	}
	
	
	private HttpEntity wrapRequest(HttpServletRequest request , BuyVo buyVo){
		
		TokenInfo tokenInfo = (TokenInfo)request.getSession().getAttribute(MDA.TOKEN_INFO_KEY);
		OrderInfo orderInfoVo = createOrderInfo(buyVo, tokenInfo.getLoginUser());
		
		HttpEntity httpEntity = new HttpEntity<>(JSON.toJSONString(orderInfoVo) , wrapHeader(tokenInfo));
		
		return httpEntity;
 	}
	
	
	private OrderInfo createOrderInfo(BuyVo buyVo , String loginUserName){
		OrderInfo orderInfoVo = new OrderInfo();
		orderInfoVo.setProductNo(buyVo.getProductNo());
		orderInfoVo.setUserName(loginUserName);
		orderInfoVo.setProductCount(buyVo.getProductCount());
//		orderInfoVo.setCreateDt(new Date());
		return orderInfoVo;
	}
	
	
	private HttpHeaders wrapHeader(TokenInfo tokenInfo){
		//设置请求头
		String token = tokenInfo.getAccess_token();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "bearer "+token);
		return headers;
	}
	
}
