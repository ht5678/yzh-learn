package license.rsa;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * 
 * RSA安全编码组件
 * 
 * @author yuezh2
 *
 * @date 2019年7月26日 下午4:06:39  
 *
 */
public class RSACoderSimple extends Coder{

	private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAcQ8AMIIBCgKCAQEAuA2bEsFCr-xZpXvNxY0FJ8N-6Jgwe9Hkjs60aiRObARDs7zueVD3pZyq4qk6-hWjyA5miMSUVpBm9Rfcy15VsfCRZRcgGGaSu9podRTEi3f0XFNZJjQTM0_TEDG98rHXHHTJqCwPvGKq2pmMUB-X9NIKjoy7gS2a3c-XpdlGhBMGZ_TsXpFo4Z2W5No5o-D20jCX70VH6BTmH6R_P_ORt5I3dwf86EZfV_FZUWLxVh3X1RJLtitM0y-TqQQOU2Y_UVaSSkzTvKKea-BgIteT89qBcCs9I7yNlmPgjMTSnrmjchCrb1weDfnb44aS1BHbPID30Kg53cq6x1hl7wGhHQIDAQAB";
	private static final String PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC+33wIqg4/14kXiW9fp5RF2ogxJYf7/Kx/EjrbEntX/A0Fjefog3wlaHp9DMaLJfNZtN2RuVfymGp5TEFG3lhvKCFJcH4CxSrEsQ4eni4qCeuTvTX1If4NRwfHZq72bZSFNoj/LcSCZ6bmnQ/di22GnvovUFC7zX/a0lG8we+Oapb2CCAXmoYen8ztsEqLPIczpnrHCXB/N2iAMlpuV9thYWcwzp0diRVOwztWv5NLF2h20IuBskGxz8xvj0l16KsQWS9nHAoMWnKlGagZZzU58jstexntKHwnBIfFjq1dPbY0A4yJLeuAZUEqz2iBUKT3bXGRxTuQTEmJM0xPfWFXAgMBAAECggEBALxLpAS4Hk6Exk1d22012GQA7ZHuQHQqE9FXnpA3b06g7AeqsjkCXRpaM1vhmovy6Fp6vonfmg9MpNz8iPjsp7kgkZXtT6JT95kKQa+axFfcaXXZlr04x3M5u0bO2ZNpDrdL0WJLOCydLF0cXGZmAapocs7yIHLki1jHWrMnbrfCYvTXC+B4ggFR9sVwB2vZBCpfXyOSvuNo8P9V3pCOCyKDp07HZojL8eiKNL/HPnGSfKKOVI1YE/Z7pjdCQMqWS2uGLBfARUGRrvYgHFGs5OHtr/1RaIAkzEWJIuGjGwJdMN4hyDhvk4ni7l5s2tANj87JV7VUqIqLwgEjUGkQLIECgYEA4AueKtD11DVBMA9uS6Yx5LP/9XiQqgk/gezk1vIgflD8UTl8hmkfmtN35Z4SHNaYwuCk0PhJfaGyLwR1uzdqsKSiKHSjw+doa9jET42Bw/zoKS3gDa3acRvd08pNzSOzq5qnjgQKhfSG6e861GwON/SMdidhfEaaywgNukYbjOcCgYEA2hirr4ZEAY6RFxTfyu24KbMgvda9cR0ZMSB2Fa6g1ZP8s7GKa+p1ob5i5cw05++KnLJuWXqU70bGwMZH8mN2jso70eFMnmPUO2IbdjiUh0ZPg+Ha6vrgdDPyKiSBVitY1XJ/JY21MQUGPL7CVNXzcGY65262ToEISanIk12YChECgYBoOD9NP4L7QgKe7q6p9heDgc/xSjJRRpgKQj55VoyS9e1Jg2pPxBr1lKVTZOKcE59gF8vTgYv+KveOM47olfDFVvAvzYJYoQ7MDDvVncfdeYotja+J7XON5h6cZKEoahEsJHcI8wxKH+OacrR1GAbNNK8aT1tjyhw7kfWAzxgFxQKBgQClEGD4YpUb3IwBPSI3kv18Sf9wzpLohk5nuRrGRDmkC/IJCkkqbfQDq3VsWMZ99/UfrYgJHtaT1ixdAHS5hf/YCEWIDo2PnxykqRcBvcbkLxnFRe4LRD8Wk64nqSNeVUZRneuPP5Yv+I6E8RdhX+B0ZFsWh9tgxRXBsseEiSSmEQKBgQCjQYRiWDlx3d3ZyzUiOqAZIlAU1GKunj46qzDB4G6dmOfOulFRmmzi/T1ZC7kJ/pShqnhv3tucyIP7EcIDmMkusgIn+cEZ4UpP1oniGV/0hm2wndGeLrhFlUUz7CjyQPbKH2bNhSHeAMgZSI+CMq3wACnmeex+Az+vM6T8kugevw==";
	
    
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
	private static final int MAX_DECRYPT_BLOCK = 128; 
	
	private static RSACoderSimple coder;
	
	private static KeyFactory privateKeyFactory = null;
	private static Key privateKey = null;
	
	private static KeyFactory publicKeyFactory = null;
	private static Key publicKey = null;
	
	private RSACoderSimple() {}
	
	
	
	public static synchronized RSACoderSimple getInstance() {
		if (coder == null) {
			coder = new RSACoderSimple();
			try {
				//对密钥解密
				byte[] privateKeyBytes = decryptBASE64(PRIVATE_KEY);
				//取得私钥
				PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
				privateKeyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
				privateKey = privateKeyFactory.generatePrivate(pkcs8KeySpec);
				
				
				//对密钥解密
				byte[] publicKeyBytes = decryptBASE64(PUBLIC_KEY);
				//取得公钥
				X509EncodedKeySpec x509keySpec = new X509EncodedKeySpec(publicKeyBytes);
				publicKeyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
				publicKey = publicKeyFactory.generatePublic(x509keySpec);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return coder;
	}
	
	
	
	/**
	 * 加密
	 *用私钥加密 
	 * 
	 * @return
	 * @throws Exception
	 */
	public byte[] encryptByPrivateKey(byte[] data)throws Exception{
		//对数据加密
		Cipher cipher = Cipher.getInstance(privateKeyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	
	
	
	/**
	 * 解密
	 * 用公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptByPublicKey(byte[] data)throws Exception{
		//对数据解密
		Cipher cipher = Cipher.getInstance(publicKeyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		
		int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();  
        
        
		return decryptedData;
	}
	
	
	
	
	
}
