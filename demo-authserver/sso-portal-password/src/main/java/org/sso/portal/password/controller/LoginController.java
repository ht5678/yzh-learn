package org.sso.portal.password.controller;

import javax.servlet.http.HttpServletRequest;

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
import org.sso.portal.password.config.MDA;
import org.sso.portal.password.entity.TokenInfo;

/**
 * 
 * @author yue
 *
 */
@Controller
public class LoginController {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	
	/**
	 * 
	 * http://localhost:8855/home.html
	 * 
	 * http://localhost:8855/login.html
	 * 
	 * 
	 * 	admin/admin
	 * 
	 * zhangsan/123456
	 * 
	 * 
	 * 
	 * @param userName
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Result login(@RequestParam(value="username")String userName 
			, @RequestParam(value="password")String password , HttpServletRequest request){
		ResponseEntity<TokenInfo> response;
		try{
			//通过网关, 去认证服务器获取tokenInfo信息
			response = restTemplate.exchange(MDA.AUTH_SERVER_URL, HttpMethod.POST, wrapRequest(userName, password),TokenInfo.class);
		}catch(Exception e){
			LOGGER.error("userName:{} , password:{} 去认证服务器登录异常, 异常信息:{}",userName,password,e);
			return Result.fail(SystemErrorType.SYSTEM_BUSY);
		}
		
		settingToken2Session(response, request, userName);
		
		return Result.success(SystemErrorType.SUCCESS);
	}
	
	
	
	private HttpEntity<MultiValueMap<String, String>> wrapRequest(String userName , String password){
		//封装oauth2请求头clientId , clientSecret
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.setBasicAuth(MDA.CLIENT_ID, MDA.CLIENT_SECRET);
		
		//封装请求参数
		MultiValueMap<String, String> reqParams = new LinkedMultiValueMap<>();
		reqParams.add("username", userName);
		reqParams.add("password", password);
		reqParams.add("grant_type", "password");
		reqParams.add("scope", "read");
		
		//封装请求参数
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String,String>>(reqParams,httpHeaders);
		return entity;
	}
	
	
	
	/**
	 * 
	 * @param responseEntity
	 * @param request
	 * @param userName
	 */
	private void settingToken2Session(ResponseEntity<TokenInfo> responseEntity , HttpServletRequest request , String userName){
		//把token放到session中
		TokenInfo tokenInfo = responseEntity.getBody();
		tokenInfo.setLoginUser(userName);
		request.getSession().setAttribute(MDA.TOKEN_INFO_KEY, responseEntity.getBody());
	}
	
}
