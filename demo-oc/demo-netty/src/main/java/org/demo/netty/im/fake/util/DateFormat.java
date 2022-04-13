/**
 * 
 */
package org.demo.netty.im.fake.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author yue
 *
 */
public class DateFormat {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public synchronized static String getStringDate(Date date) {
		return format.format(date);
	}
}
