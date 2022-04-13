package org.demo.netty.im.fake.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午9:57:08
 */
public final class HttpRequests {
	
	public static String getRequestParam(Map<String, List<String>> parameters, String name) {
		List<String> params = parameters.get(name);
		return params == null ? null : StringUtils.isEmpty(params.get(0)) ? null : params.get(0);
	}
}