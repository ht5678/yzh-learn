package org.sso.portal.password.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.sso.portal.password.config.MDA;
import org.sso.portal.password.entity.ProductInfo;
import org.sso.portal.password.entity.TokenInfo;

import ch.qos.logback.core.subst.Token;

/**
 * 
 * @author yue
 *
 */
@Controller
public class PortalProductController {

	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PortalProductController.class);	
	
	
	@RequestMapping("/product/{id}")
	public ModelAndView showProductDetail(@PathVariable("id")Long id , HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		
		//获取token
		TokenInfo tokenInfo = (TokenInfo)request.getSession().getAttribute(MDA.TOKEN_INFO_KEY);
		
		try{
			ResponseEntity<Result<ProductInfo>> responseEntity = restTemplate.exchange(MDA.GET_PRODUCT_INFO+id, HttpMethod.GET , 
					wrapRequest(tokenInfo),
					new ParameterizedTypeReference<Result<ProductInfo>>() {});
			
			
			Result<ProductInfo> productInfoResult = responseEntity.getBody();
			LOGGER.info("根据商品id : {}查询商品详细信息 : {}" , id, productInfoResult.getData());
			
			mv.addObject("productInfo" , productInfoResult.getData());
			mv.addObject("loginUser" , tokenInfo.getLoginUser());
			mv.setViewName("product_detail");
			
		}catch(Exception e){
			LOGGER.error("根据商品id查询商品详情异常 , {}",id);
		}
		return mv;
	}
	
	
	
	private HttpEntity wrapRequest(TokenInfo tokenInfo){
		//设置请求头
		String token = tokenInfo.getAccess_token();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "bearer "+token);
		
		HttpEntity httpEntity = new HttpEntity<>(null , headers);
		return httpEntity;
	}
	
}
