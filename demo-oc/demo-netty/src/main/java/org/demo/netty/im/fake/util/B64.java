/**
 * 
 */
package org.demo.netty.im.fake.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Description:
 * @author pengzq1
 * @createDate 2018年11月6日
 * @version v 1.0
 */
public class B64 {
	
	public static String decoder(String value) {
		byte[] decodes = Base64.getDecoder().decode(value);
		return new String(decodes, StandardCharsets.UTF_8);
	}
	
	public static String encoder(String value) {
		byte[] encodes = Base64.getEncoder().encode(value.getBytes(StandardCharsets.UTF_8));
		return new String(encodes, StandardCharsets.UTF_8);
	}

}
