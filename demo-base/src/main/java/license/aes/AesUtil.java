package license.aes;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
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
