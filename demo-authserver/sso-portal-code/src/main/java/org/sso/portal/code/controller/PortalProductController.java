package org.sso.portal.code.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ms.cloud.common.component.entity.ProductInfo;
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
import org.sso.portal.code.config.MDA;
import org.sso.portal.code.entity.SysUser;
import org.sso.portal.code.entity.TokenInfo;

import com.alibaba.fastjson.JSON;

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
	
	
	
	/**
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/product/{id}")
	public ModelAndView showProductDetail(@PathVariable("id")Long id , HttpServletRequest request , HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		
		//session中获取access token
		TokenInfo tokenInfo = (TokenInfo)request.getSession().getAttribute(MDA.TOKEN_INFO_KEY);
		String accessToken = tokenInfo.getAccess_token();
		
		
		//拦截器中把accessToken存储到了请求头中
//		String accessToken = GetTokenUtils.getAccessToken(request, response);
		
		
		SysUser sysUser = (SysUser)request.getSession().getAttribute(MDA.CURRENT_LOGIN_USER);
		
		
		try{
			ResponseEntity<ProductInfo> responseEntity = restTemplate.exchange(MDA.GET_PRODUCT_INFO+id, HttpMethod.GET, 
					wrapRequest(accessToken), new ParameterizedTypeReference<ProductInfo>() {
					});
			
//			Result<ProductInfo> productInfoResult = responseEntity.getBody();
			LOGGER.info("根据商品id:{}查询商品详细信息:{}" , id,JSON.toJSONString(responseEntity));
			mv.addObject("productInfo" , responseEntity);
			mv.addObject("loginUser",sysUser.getUsername());
			mv.setViewName("product_detail");
		}catch(Exception e){
			LOGGER.error("根据商品id查询商品详情异常: {}" , id);
		}
		return mv;
	}
	
	
	
	
	private HttpEntity wrapRequest(String token){
		//设置请求头
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "bearer "+token);
		HttpEntity httpEntity = new HttpEntity(null, headers);
		return httpEntity;
	}
	
	
	
	
}
