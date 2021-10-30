package org.sso.auth.code.server.config.indb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yue
 *
 */
@Component
public class DemoLogoutSuccessHandler implements LogoutSuccessHandler{

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoLogoutSuccessHandler.class);
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		LOGGER.info("current url : {}",request.getRequestURL());
		LOGGER.info("redirect url: {}" , request.getParameter("redirectUrl"));
		
		String redirectUrl = request.getParameter("redirectUrl");
		if(!StringUtils.isEmpty(redirectUrl)){
			response.sendRedirect(redirectUrl);
		}
		
	}

	
	
	
}
