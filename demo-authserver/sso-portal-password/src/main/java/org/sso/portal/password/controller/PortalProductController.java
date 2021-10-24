package org.sso.portal.password.controller;

import javax.servlet.http.HttpServletRequest;

import org.ms.cloud.common.component.exception.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.sso.portal.password.config.MDA;
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
//			ResponseEntity<Result<product>/>
		}catch(Exception e){
			LOGGER.error("根据商品id查询商品详情异常 , {}",id);
		}
		return mv;
	}
	
}
