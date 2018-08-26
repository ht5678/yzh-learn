package com.demo.springcloud.hystrix.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;



@Configuration
@WebFilter(urlPatterns="/*",filterName="hystrixFilter")
public class MyFilter implements Filter {

	
	/**
	 * 
	 */
	@Override
	public void destroy() {

	}

	
	/**
	 * 
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("####触发了缓存######");
		HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
		try{
			chain.doFilter(req, res);
		}finally{
			ctx.shutdown();
		}
		System.out.println("####结束了缓存######");
	}

	
	
	/**
	 * 
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
