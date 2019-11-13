package license.aes;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * 对长度有要求：
 * PUBLIC_KEY=MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCutMa9dDfnDp88VtHzNu6hzG+aTuvVorgIKjaqvx6Z8BEnl9NABdBB9KnLA3sL90yTPTzqpP8OJYQvzk5B+QAzfFmuQgbvN0M/NvZsei62VJWVRMi1pGKMpx5/I/pj+yO7v1dJ0FyThDgh99taL7QpGH8gIfehnao5bZvYX8aD8QIDAQAB
	PRIVATE_KEY=MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK60xr10N+cOnzxW0fM27qHMb5pO69WiuAgqNqq/HpnwESeX00AF0EH0qcsDewv3TJM9POqk/w4lhC/OTkH5ADN8Wa5CBu83Qz829mx7LrZUlZVEyLWkYoynHn8j+mP7I7q/V0nQXJOEOCH321ovtCkYfyAh96Gdqjltm9hfxoPxAgMBAAECgYBOul44789lSxyM118DKAT/Wp4MPnZeLlaaL1O1PCzxF9LTMcLbvgLsRfHh726apNWYIGd8IeBiIpvf4ys25Zul1+UmNomMQy9oJLYF/SDW65ekcxWnXJ3KUZm3kkL/Hb6T/VSDoQHgWFoPHqqAwW+x5WSubKhO/dZThf7Mt9n3JQJBAO7lFTyKXO9m24G5nxVcvIPKQkEU04jY//EMoznEVsOhL6qDQMk6HxKXF4W50EOGaay/F3ci17ZZPY/zOGhuhtMCQQC7Nx8WbS2k+DqnbXFNAo1qR0WAxH8Wa/TtKjuT/doaMBWXqapFPz03YipvIpNGspGZg3qZoINt5arkjBL2ZJerAkEA5Z8Z/3v7Aj26hYF2sKDXrlFtx4KOJP53XCnKho762sUGvP21+vU7wrGp8WZ+ETmUP2/ZzTCXaPV0H/YnZPG/8wJAITkA7Uvmo7XhQsDrME1ZpxiKvvUf0GzMBCJQfEy6HZeW3VkgfDViuadT/HDYTd12dRWd9hFEAYaWMwSggNUNDQJAFOzpL3G3w47uHXaAtnbTtLKtYqu2N7q4B4olrTEwQSPXGvUpKL4OfTd9S6MIvuMsXmii1DSZXfPtrEuRlZ4xaA==

	IV=eyWR7v5mnC7BqYYxV5P595agNRAqHzHNwHh6qIdyMbWbY6DnMdJ6aXLPNUkZH60uGbBvglyFVoaNlKN0RjbiedT/BK/FG6+Y0xu0olSqq2qoUMWnHRNCCIMyPpWuwxleJQy/oUsLD1grT35w4gLaaPj2ncJVxo6xk0lEQMoL5QI=
	PKEY=K3dmH7nWkxiL6IVyYp7lPuT7hA2PRYRP3/R7PELWGMAcj1wSA3Mpff9FITRy0cAAgy83+On+57Qf8fDoI8H0Iw1ZDB6VI+BX+9sZ0vGeMnmRWaM7MIdugqQxfu3kbwQqtOtaT2vhs/ihDkFqsH5vfZhVWVW32vx7v+6Jg++yFc4=

 * 
 * aes
 * @author yuezhihua
 *
 */
public class AesUtil {

	private static AesUtil aes = null;
	
	private static AlgorithmParameterSpec paramSpec = null;
	
	private static SecretKey key = null;
	
	
	public static byte[] iv = new byte[] { 82, 22, 50, 44, -16, 124, -40, -114,
			-87, -40, 37, 23, -56, 23, -33, 75 };


	public static byte[] key1 = new byte[] { -42, 35, 67, -86, 19, 29, -11, 84,
			94, 111, 75, -104, 71, 46, 86, -21, -119, 110, -11, -32, -28, 91,
			-33, -46, 99, 49, 2, 66, -101, -11, -8, 56 };
	
	
	
	private AesUtil() {}

	
	
	public static synchronized AesUtil getInstance() {
		if (aes == null) {
			aes = new AesUtil();
			try {
				paramSpec = new IvParameterSpec(iv);
				
				// 注意，为了能与 iOS 统一
				// 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
				key = new SecretKeySpec(key1, "AES");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return aes;
	}


	
	public String encrypt(String msg) {
		try {
			Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			return asHex(ecipher.doFinal(msg.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	private String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}
	
	
	public String decrypt(String value) {
		try {
			
			Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			
			return new String(dcipher.doFinal(asBin(value)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	
	
	private byte[] asBin(String src) {
		if (src.length() < 1)
			return null;
		byte[] encrypted = new byte[src.length() / 2];
		for (int i = 0; i < src.length() / 2; i++) {
			int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);

			encrypted[i] = (byte) (high * 16 + low);
		}
		return encrypted;
	}

}
