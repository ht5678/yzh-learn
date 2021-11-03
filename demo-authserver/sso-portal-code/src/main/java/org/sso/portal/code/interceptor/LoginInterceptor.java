package org.sso.portal.code.interceptor;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.sso.portal.code.config.MDA;
import org.sso.portal.code.entity.TokenInfo;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 过滤token中是否有token信息,(基于session的单点登录)
 * 
 * @author yue
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	public static final String loginUrl = "http://localhost:9999/oauth/authorize?response_type=code&client_id=portal_app&redirect_uri=http://localhost:8855/callBack&state=";
	
	
	/**
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//从session中获取token的信息
		HttpSession session = request.getSession();
		
		TokenInfo tokenInfo = (TokenInfo)session.getAttribute(MDA.TOKEN_INFO_KEY);
		
		if(tokenInfo == null){
			String url = request.getRequestURL().toString();
			
			LOGGER.info("登录需要的url:{}",url);
			
			//重定向到登录页面
			response.sendRedirect(loginUrl+url);
			return false;
		}else{
			LOGGER.info("portal-web-code中的session的tokenInfo有效期:{} , 当前时间:{}" , tokenInfo.getExpireTime(),LocalDateTime.now());
			//判断accessToken是否过期,若过期的话,就需要通过刷新令牌去刷新我们的accessToken
			if(tokenInfo.isExpire()){
				LOGGER.info("accessToken失效 : {} , accessToken有效期: {} , 当前时间: {}" , tokenInfo.getAccess_token(),tokenInfo.getExpireTime() , LocalDateTime.now());
				//通过refreshToken刷新我们的accessToken
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				httpHeaders.setBasicAuth(MDA.CLIENT_ID, MDA.CLIENT_SECRET);
				
				MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
				param.add("grant_type", "refresh_token");
				param.add("refresh_token", tokenInfo.getRefresh_token());
				
				HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(param , httpHeaders);
				
				
				ResponseEntity<TokenInfo> responseEntity = null;
				
				try{
					responseEntity = restTemplate.exchange(MDA.AUTH_SERVER_URL, HttpMethod.POST,httpEntity , TokenInfo.class);
					TokenInfo newTokenInfo = responseEntity.getBody().initExpireTime();
					
					request.getSession().setAttribute(MDA.TOKEN_INFO_KEY, newTokenInfo);
				}catch(Exception e){
					//refresh token失效了 , 从新走认证服务器流程
					LOGGER.warn("根据refresh token:{} 获取access token失败" , tokenInfo.getRefresh_token());
					String contentType = request.getContentType();
					//表示ajax请求
					if(contentType!=null && contentType.contains(MediaType.APPLICATION_JSON_UTF8.toString())){
						response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
						response.getWriter().write(JSON.toJSONString(Result.fail(SystemErrorType.REFRESH_TOKEN_EXPIRE)));
						return false;
					}
					
					//通过刷新token获取access token失败
					String url = "http://localhost:8855/home.html";
					//重定向到登录页面
					LOGGER.info("重定向url: {}" , (loginUrl+url));
					response.sendRedirect(loginUrl+url);
					return false;
				}
			}
		}
		
		return true;
	}

	
	
}
