package com.demo.springcloud.feign.interceptor;

import com.demo.springcloud.feign.simple.HelloService;

import feign.Feign;
import feign.gson.GsonDecoder;

public class InterceptorMain {


	public static void main(String[] args) {
		HelloService service = Feign.builder()
				.requestInterceptor(new MyInterceptor())
				.decoder(new GsonDecoder()).target(HelloService.class,"http://localhost:8080");
		
		String result = service.hello();
		System.out.println(result);
		
	}
	
}
