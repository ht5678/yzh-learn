package com.demo.springcloud.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;




public class MyInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		//请求发出之前的拦截器 , 统一设置头信息
		template.header("Content-Type", "application/json");
		System.out.println("这是一个自定义拦截器");
	}

}
