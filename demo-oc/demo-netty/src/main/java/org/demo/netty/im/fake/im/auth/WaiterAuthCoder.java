package org.demo.netty.im.fake.im.auth;

import org.demo.netty.im.fake.domain.Waiter;
import org.demo.netty.im.fake.util.B64;
import org.demo.netty.im.fake.util.MD5Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月6日 下午10:15:53
 */
public class WaiterAuthCoder {

	private static Logger log = LoggerFactory.getLogger(WaiterAuthCoder.class);
	
	public static String SERVER_COOKIE_NAME = "OCIM-Server";
	
	
	/**
	 * 
	 * @return
	 */
	public static String encode(Waiter waiter) {
		StringBuilder sb = new StringBuilder();
		sb.append(waiter.getWaiterName()).append("|");
		sb.append(waiter.getWaiterCode()).append("|");
		sb.append(waiter.getTenantCode()).append("|");
		sb.append(waiter.getTeamCode()).append("|");
		sb.append(System.currentTimeMillis());
		String sign = MD5Encrypt.sign(sb.toString());
		sb.append("=").append(sign);
		return B64.encoder(sb.toString());
	}
	
}
