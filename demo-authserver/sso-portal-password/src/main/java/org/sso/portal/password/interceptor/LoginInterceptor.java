package org.sso.portal.password.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.sso.portal.password.config.MDA;
import org.sso.portal.password.entity.TokenInfo;

/**
 * 
 * 拦截器,拦截 portal-web工程中需要登录url
 * 
 * @author yue
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
	
	
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
			response.sendRedirect("/login.html?targetUrl="+url);
			return false;
		}
		
		return true;
	}

	
	
}
