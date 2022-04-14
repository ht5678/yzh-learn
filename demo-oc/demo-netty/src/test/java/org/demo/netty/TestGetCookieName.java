package org.demo.netty;

import java.util.List;
import java.util.Set;

import org.demo.netty.im.fake.exception.BSAuthorizeException;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月14日 下午10:24:54
 */
public class TestGetCookieName {

	
	/**
	 * 
	 * @param args
	 * @throws BSAuthorizeException 
	 */
	public static void main(String[] args) throws BSAuthorizeException {
		HttpHeaders headers = new DefaultHttpHeaders();
		headers.add("Cookie", CUSTOMER_COOKIE_NAME+":tes2;tt:22");
		
		String result = getCustomerCookieValue(headers);
		System.out.println(result);
	}
	
	
	
	//
	private static String CUSTOMER_COOKIE_NAME = "OCIM-Customer";
	
	
	/**
	 * org.demo.netty.im.fake.im.auth.CustomerAuthCoder.getCustomerCookieValue(HttpHeaders)
	 * @param headers
	 * @return
	 * @throws BSAuthorizeException
	 */
	private static String getCustomerCookieValue(HttpHeaders headers) throws BSAuthorizeException{
		List<String> cookieList = headers.getAll("Cookie");
		if(null == cookieList || cookieList.isEmpty()) {
			throw new BSAuthorizeException("1001 不存在任何cookie信息");
		}
		ServerCookieDecoder strict = ServerCookieDecoder.STRICT;
		Set<Cookie> cookies = strict.decode(cookieList.get(0));
		String customerCookieValue = null;
		for(Cookie cookie : cookies) {
			if(cookie.name().equals(CUSTOMER_COOKIE_NAME)) {
				customerCookieValue = cookie.value();
				break;
			}
		}
		return customerCookieValue;
	}
	
}
