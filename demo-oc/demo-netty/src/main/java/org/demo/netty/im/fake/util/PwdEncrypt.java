package org.demo.netty.im.fake.util;

import org.apache.commons.codec.digest.Md5Crypt;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午4:49:01
 */
public class PwdEncrypt {
	
	private final static String salt = "set-MM!!@[customer]Oc-MD5-Base64?|}{(pwd)";

	public static boolean check(String mD5Password, String account, String password) {
		int len = account.length();
		String authStr = account.substring(len, len - 1).toUpperCase() + password + salt;
		String newSign = Md5Crypt.apr1Crypt(authStr.getBytes(), salt);
		return mD5Password.equals(newSign);
	}

	public static String createMD5Password(String account, String password) {
		int len = account.length();
		String authStr = account.substring(len - 4, len - 1).toUpperCase() + password + salt;
		return Md5Crypt.apr1Crypt(authStr.getBytes(), salt);
	}
}