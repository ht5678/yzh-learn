package org.sso.portal.code.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.sso.portal.code.util.CookieUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author yue
 *
 */
//@Component
public class CookieLoginInterceptor implements HandlerInterceptor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CookieLoginInterceptor.class);

	
	public static final String loginUrl = "http://localhost:8888/oauth/authorize?response_type=code&client_id=portal_app&redirect_uri=http://localhost:8855/callBack&state=";

	
	@Autowired
	private RestTemplate restTemplate;

	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//先从cookie读取access token的值
		String accessTokenInCookie = CookieUtils.readCookieValue(request, MDA.COOKIE_ACCESS_TOKEN_KEY);
		LOGGER.info("从cookie读取AccessToken的值 : {}",accessTokenInCookie);
		
		//从cookie中获取refreshToken的值
		String refreshTokenInCookie = CookieUtils.readCookieValue(request, MDA.COOKIE_REFRESH_TOKEN_KEY);
		LOGGER.info("从cookie中获取refreshToken的值:{}" , refreshTokenInCookie);
		
		//如若cookie中的accessToken没有过期,我们拦截器直接放行 , 不进行拦截
		if(!StringUtils.isEmpty(accessTokenInCookie)){
			response.setHeader(MDA.COOKIE_ACCESS_TOKEN_KEY, accessTokenInCookie);
			return true;
		}else{
			//accessToken已经失效了,但是我们的cookie中的refreshToken没有过期,我们需要通过刷新令牌去进行刷新
			//我们的access token
			if(!StringUtils.isEmpty(refreshTokenInCookie)) {
				//刷新令牌不为空
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				httpHeaders.setBasicAuth(MDA.CLIENT_ID, MDA.CLIENT_SECRET);
				
				MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
				param.add("grant_type", "refresh_token");
				param.add("refresh_token", refreshTokenInCookie);
				
				HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(param,httpHeaders);
				
				ResponseEntity<TokenInfo> responseEntity = null;
				
				try {
					//刷新我们的令牌
					responseEntity = restTemplate.exchange(MDA.AUTH_SERVER_URL, HttpMethod.POST,httpEntity , TokenInfo.class);
					TokenInfo newTokenInfo = responseEntity.getBody().initExpireTime();
					
					//把新的令牌存储到cookie中
					CookieUtils.writeCookie(response, newTokenInfo);
					response.setHeader(MDA.COOKIE_ACCESS_TOKEN_KEY, newTokenInfo.getAccess_token());
					
				}catch(Exception e) {

					//认证服务器上的refresh token刷新失败了(一般是我们的刷新令牌过期了)
					LOGGER.warn("认证服务器的refreshToken已经过期," , refreshTokenInCookie);
					String contentType = request.getContentType();
					//表示ajax请求
					if(contentType != null && contentType.contains(MediaType.APPLICATION_JSON_UTF8.toString())) {
						response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
						response.getWriter().write(JSON.toJSONString(Result.fail(SystemErrorType.REFRESH_TOKEN_EXPIRE)));
						return false;
					}
					
					//通过刷新token获取accessToken失败 , 从新登录
					String url = "http://localhost:8855/home.html";
					//重定向到登录页面
					LOGGER.info("重定向url : {}"  , (loginUrl+url));
					response.sendRedirect(loginUrl+url);
					return false;
				}
			
			}else {
				//客户端的cookie中的refreshToken已经失效
				String contentType = request.getContentType();
				//表示ajax请求
				if(contentType != null && contentType.contains(MediaType.APPLICATION_JSON_UTF8.toString())) {
					response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
					response.getWriter().write(JSON.toJSONString(Result.fail(SystemErrorType.REFRESH_TOKEN_EXPIRE)));
					return false;
				}
				
				//通过刷新token获取access token失败 , 从新登录
				String url = "http://localhost:8855/home.html";
				//重定向到登录页面
				LOGGER.info("重定向url : {}"  , (loginUrl+url));
				response.sendRedirect(loginUrl+url);
				return false;
			}
		}
		
		return true;
	}
		
	
	
}
