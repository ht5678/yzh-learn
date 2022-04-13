package org.demo.netty.im.fake.im.auth;

import java.util.List;
import java.util.Set;

import org.demo.netty.im.fake.exception.BSAuthorizeException;
import org.demo.netty.im.fake.util.B64;
import org.demo.netty.im.fake.util.MD5Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;

/**
 * 客户信息认证
 * @author yuezh2
 * @date	  2022年3月28日 下午5:39:56
 */
public class CustomerAuthCoder {

	private static Logger log = LoggerFactory.getLogger(CustomerAuthCoder.class);
	
	private static String CUSTOMER_COOKIE_NAME = "OCIM-Customer";
	
	
	/**
	 * 
	 * @return
	 */
	public static CustomerInfo decode(HttpHeaders headers) throws BSAuthorizeException{
		String customerCookieValueB64 = getCustomerCookieValue(headers);
		if(null == customerCookieValueB64 || customerCookieValueB64.isEmpty()) {
			throw new BSAuthorizeException("1004 无法客户获取认证信息");
		}
		try {
			String customerCookieValue = B64.decoder(customerCookieValueB64);
			String[] body = customerCookieValue.split("=");
			String[] params = body[0].split("\\|");
			String sign = MD5Encrypt.sign(body[0]);
			if(sign.equals(body[1])) {
				CustomerInfo customerInfo = new CustomerInfo();
				customerInfo.setTtc(params[0]);
				customerInfo.setTmc(params[1]);
				customerInfo.setSkc(params[2]);
				customerInfo.setBuc(params[3]);
				customerInfo.setCc(params[4]);
				customerInfo.setCn(params[5]);
				customerInfo.setGc(params[6]);
				customerInfo.setReal(params[7]);
				customerInfo.setDevice(params[8]);
				return customerInfo;
			}
		}catch(Exception e) {
			log.error("客户认证信息 ; {} , 解析发生异常" , customerCookieValueB64 , e);
		}
		throw new BSAuthorizeException("1002 客户信息认证失败 : ["+customerCookieValueB64+"]");
	}
	
	
	
	/**
	 * 
	 * @return
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
