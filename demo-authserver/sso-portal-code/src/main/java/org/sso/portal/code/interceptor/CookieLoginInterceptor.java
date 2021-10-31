package org.sso.portal.code.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.sso.portal.code.config.MDA;
import org.sso.portal.code.util.CookieUtils;

/**
 * 
 * @author yue
 *
 */
//@Component
public class CookieLoginInterceptor implements HandlerInterceptor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CookieLoginInterceptor.class);

	
	public static final String loginUrl = "http://localhost:8888/oauth/authorize?response_type=code&client_id=portal_app&redirect_uri=http://localhost:8855/callBack&state=";


	
	
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
			
			
		}
		
		return true;
	}
		
	
	
}
