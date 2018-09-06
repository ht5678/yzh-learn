package com.demo.springcloud.config;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * 
 *         ┏┓　　　┏┓
 *      ┏┛┻━━━┛┻┓
 *      ┃　　　　　　　┃ 　
 *      ┃　　　━　　　┃
 *      ┃　┳┛　┗┳　┃
 *      ┃　　　　　　　┃
 *      ┃　　　┻　　　┃
 *      ┃　　　　　　　┃
 *      ┗━┓　　　┏━┛
 *         ┃　　　┃　　　　　　　　　
 *         ┃　　　┃
 *         ┃　　　┗━━━┓
 *         ┃　　　　　　　┣┓
 *         ┃　　　　　　　┏┛
 *         ┗┓┓┏━┳┓┏┛
 *　　      ┃┫┫　┃┫┫
 *　        ┗┻┛　┗┻┛
 *
 *-------------------- 神兽保佑 永无bug --------------------
 * 
 * 
 * 
 * @author yuezh2   2018年9月5日 下午4:36:50
 *
 */
public class DecryptMain {
	
	
	/**
	 * 解密
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost("http://localhost:8888/decrypt");
		
//		HttpEntity entity = new StringEntity("ff477c23e45efd59def381ec2d487e473060e803517153fb5187bfd590f52ebb48144f66b8eca2b58eab3ac9df8a4e66" , Consts.UTF_8);
		HttpEntity entity = new StringEntity("AQAVMeDvrbPe8FOxF5ISIUggOtbWSqg9wk8u9uS87PCoxz4I8mivEowKYHKwa3daHJ5D/NIu6JmWCzqNMj7rHDwTb7dp6ooQae91q2qb8MItqX0zI7ge+nV5n2QeAHIHuthdHNx/hbLY9+4Vj6XwI0AcKCYsfUXULJlCPyKT1/abAEcKOcQWCbeisqImT726+wJ7aZz3ao+VnfuOSfda0rbBhJDKSMxJOQfURzYHh0vFxWQhWtOHkrupiDnZMY4oTh/zZbMtGo1S8PSIdZ8i8mG+Ug/e9gIfLEh/x49QwpwSPQ2JPnTzk2E3wZMtnq9rTSswodUXgWoDRVS6pPos2OWgX/sTvBSfUZnchofeWcJqPWvCgKBRwj9bCbRp130iAve4nqACFDlzOOSYEsQQylQW" , Consts.UTF_8);
		
		post.setEntity(entity);
		
		HttpResponse response = client.execute(post);
		System.out.println(EntityUtils.toString(response.getEntity()));
		
	}

}
