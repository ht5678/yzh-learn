package org.demo.netty.util;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月30日 下午3:51:06
 */
public class JsonUtils {
	private static JsonSupport jsonSupport = new JacksonJsonSupport();
	
	public static JsonSupport getJson() {
		return jsonSupport;
	}
}