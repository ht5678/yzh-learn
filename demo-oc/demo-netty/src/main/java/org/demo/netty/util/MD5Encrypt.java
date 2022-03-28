package org.demo.netty.util;

import org.apache.commons.codec.digest.Md5Crypt;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月28日 下午5:49:12
 */
public class MD5Encrypt {
	
	private final static String salt = "O.c~!admin?5>1 -1<05%^#(12abc)";
	
	public static String sign(String str) {		
		return Md5Crypt.apr1Crypt(str.getBytes(), salt);
	}
	
	public static boolean check(String sign, String str) {
		String newSign = Md5Crypt.apr1Crypt(str.getBytes(), salt);
		return sign.equals(newSign);
	}
	
	public static String sign(String str, String salt) {
		return Md5Crypt.apr1Crypt(str.getBytes(), salt);
	}
	
	public static boolean check(String sign, String salt, String str) {
		String newSign = Md5Crypt.apr1Crypt(str.getBytes(), salt);
		return sign.equals(newSign);
	}
}
