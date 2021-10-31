package org.sso.portal.code.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sso.portal.code.config.MDA;
import org.sso.portal.code.entity.TokenInfo;

/**
 * cookie工具
 * @author yue
 *
 */
public class CookieUtils {
	
	
	
	/**
	 * 写cookie
	 * @param response
	 * @param tokenInfo
	 */
	public static void writeCookie(HttpServletResponse response , TokenInfo tokenInfo) {
		Cookie accessCookie = new Cookie(MDA.COOKIE_ACCESS_TOKEN_KEY, tokenInfo.getAccess_token());
		accessCookie.setMaxAge(3600);
		accessCookie.setPath("/");
		accessCookie.setSecure(false);
		accessCookie.setDomain("localhost");
		response.addCookie(accessCookie);
		
		Cookie refreshCookie = new Cookie(MDA.COOKIE_REFRESH_TOKEN_KEY, tokenInfo.getRefresh_token());
		refreshCookie.setMaxAge(3600*24*30);
		refreshCookie.setPath("/");
		refreshCookie.setSecure(false);
		refreshCookie.setDomain("localhost");
		response.addCookie(refreshCookie);
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param cookieKey
	 * @return
	 */
	public static String readCookieValue(HttpServletRequest request , String cookieKey){
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals(cookieKey)){
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	

}
