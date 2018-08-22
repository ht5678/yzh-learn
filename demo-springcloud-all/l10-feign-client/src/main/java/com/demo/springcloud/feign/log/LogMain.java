package com.demo.springcloud.feign.log;

import com.demo.springcloud.feign.simple.HelloService;

import feign.Feign;
import feign.Logger;

public class LogMain {

	
	
	/**
	 * feign.Logger.Level.NONE : 默认值,不计入日志
	 * BASIC : 记录请求url,方法,状态代码和时间
	 * HEADERS:除了BASIC的日志外, 还会记录请求头和响应头信息
	 * FULL:除了HEADERS的信息外 , 请求和相应的元数据都会保存
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		HelloService service = Feign.builder()
				.logLevel(feign.Logger.Level.FULL)
				.logger(new Logger.JavaLogger().appendToFile("logs/http.log"))
				.target(HelloService.class , "http://localhost:8080");
		
		String result = service.hello();
		System.out.println(result);
				
	}
	
	
}
