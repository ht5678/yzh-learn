package com.learn.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


/**
 * 
 * 1 . auth server , get code 
 * http://localhost:9999/oauth/authorize?response_type=code&client_id=portal_app&redirect_uri=http://www.baidu.com&state=abc
 * 
	 * 参数说明: client_id 认证服务器分配给第三方客户端的appid
		response_type:固定格式 值位code
		redirect_uri: 用户在认证服务器上登陆成功了 需要回调到 客户端应用上
		state: 你传什么给授权服务器 授权服务器原封不动的返回给你

 * 
 * 
 * 
 * 
 * @author yue
 *
 */
@SpringBootApplication
public class Demo09MsAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Demo09MsAuthServerApplication.class, args);
	}

}
