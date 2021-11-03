package org.sso.portal.code.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ms.cloud.common.component.exception.Result;
import org.ms.cloud.common.component.exception.SystemErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.sso.portal.code.config.MDA;
import org.sso.portal.code.entity.SysUser;
import org.sso.portal.code.entity.TokenInfo;
import org.sso.portal.code.interceptor.LoginInterceptor;
import org.sso.portal.code.util.CookieUtils;

/**
 * 
 * @author yue
 *
 */
@Controller
public class CallBackController {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CallBackController.class);
	
	
	@RequestMapping("/callBack")
	public String callBack(@RequestParam("code")String code , 
								@RequestParam("state")String state,
								HttpServletRequest request,
								HttpServletResponse httpServletResponse){
		LOGGER.info("code : {} ,  state:{} " , code , state);
		
		//通过code获取token
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.setBasicAuth(MDA.CLIENT_ID, MDA.CLIENT_SECRET);
		
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("code", code);
		param.add("grant_type", "authorization_code");
		param.add("redirect_uri", MDA.CALL_BACK_URL);
		
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(param,httpHeaders);
		
		
		ResponseEntity<TokenInfo> response = null;
		
		try{
			response = restTemplate.exchange(MDA.AUTH_SERVER_URL, HttpMethod.POST, httpEntity, TokenInfo.class);
		}catch(Exception e){
			LOGGER.warn("通过授权码获取token失败 : {}" , code);
		}
		
		
		//把token放到session中
		TokenInfo tokenInfo = response.getBody();
		LOGGER.info("token info : {}" , tokenInfo);
		
		//session的登录方案
		request.getSession().setAttribute(MDA.TOKEN_INFO_KEY, tokenInfo.initExpireTime());
		
		//cookie的登录方案
//		CookieUtils.writeCookie(httpServletResponse, tokenInfo);
		
		//根据access token获取用户登录信息
		String accessToken = tokenInfo.getAccess_token();
		HttpEntity<MultiValueMap<String, String>> userEntity = new HttpEntity<>(null,httpHeaders);
		
		ResponseEntity<SysUser> userResponseEntity = null;
		try{
			userResponseEntity = restTemplate.exchange(MDA.GET_CURRENT_USER+accessToken, HttpMethod.POST,userEntity,SysUser.class);
			
		}catch(Exception e){
			LOGGER.warn("通过授权码获取token失败: {} , 异常: {}",code , e);
		}
		
		SysUser sysUser = userResponseEntity.getBody();
		LOGGER.info("当前登录用户为: {}",sysUser);
		
		
		//把登录用户存储到session中
		request.getSession().setAttribute(MDA.CURRENT_LOGIN_USER, sysUser);
		
		return "redirect:"+state;
		
	}
	
	
	
	@RequestMapping("/logout")
	@ResponseBody
	public Result logout(HttpServletRequest request){
		request.getSession().invalidate();
		
		return Result.success("logout success");
	}
	
	
}
