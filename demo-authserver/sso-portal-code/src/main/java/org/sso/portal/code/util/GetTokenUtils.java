package org.sso.portal.code.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sso.portal.code.config.MDA;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月2日下午3:46:50
 */
public class GetTokenUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetTokenUtils.class);
	
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getAccessToken(HttpServletRequest request , HttpServletResponse response) {
		//先去cookie中找
		String accessTokenInCookie = CookieUtils.readCookieValue(request, MDA.COOKIE_ACCESS_TOKEN_KEY);
		
		if(!StringUtils.isEmpty(accessTokenInCookie)) {
			LOGGER.info("从cookie中获取到accesstoken:{}",accessTokenInCookie);
			return accessTokenInCookie;
		}
		
		String accessTokenInHeader = response.getHeader(MDA.COOKIE_ACCESS_TOKEN_KEY);
		if(!StringUtils.isEmpty(accessTokenInHeader)) {
			LOGGER.info("从header中获取到accessToken:{}",accessTokenInHeader);
			return accessTokenInHeader;
		}
		return null;
	}
	
}
