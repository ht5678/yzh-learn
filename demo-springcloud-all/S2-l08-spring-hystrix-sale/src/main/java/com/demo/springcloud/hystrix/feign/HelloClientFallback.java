package com.demo.springcloud.hystrix.feign;

import org.springframework.stereotype.Component;






@Component
public class HelloClientFallback implements HelloClient{

	
	/**
	 *  
	 */
	@Override
	public String hello() {
		return "fallback hello";
	}

	
	/**
	 * 
	 */
	@Override
	public String toHello() {
		return "fallback timeout hello";
	}

}
