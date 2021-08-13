package utils;//package com.lenovo.aspect;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;


/**
 * 流控拦截器
 * @author yuezh2
 * @date 2020/09/07 14:43
 *
 */
public class RateLimiterUtil {


    public static final ConcurrentHashMap<String, RateLimiter> RATE_LIMITER = 
    		new ConcurrentHashMap<String, RateLimiter>();
    
    
    static {
    	RATE_LIMITER.put("DEFAULT", RateLimiter.create(300, 1000, TimeUnit.MILLISECONDS));
    }
    


}
