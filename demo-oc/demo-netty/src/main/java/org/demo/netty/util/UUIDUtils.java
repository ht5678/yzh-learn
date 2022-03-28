/**
 * 
 */
package org.demo.netty.util;

import java.util.UUID;

/**
 * @Description:
 * @author pengzq1
 * @createDate 2018年8月6日
 * @version v 1.0
 */
public class UUIDUtils {
	
	/**
	 * 生成UUID
	 * @return
	 */
	public static String getUIDBrief() {
		UUID randomUUID = UUID.randomUUID();
		
		return randomUUID.toString().replaceAll("-", "");
	}

	public static String getUID() {
		return UUID.randomUUID().toString();
	}

	public static String createPid(int pre) {
		return pre + "-" + UUID.randomUUID().toString();
	}
}
