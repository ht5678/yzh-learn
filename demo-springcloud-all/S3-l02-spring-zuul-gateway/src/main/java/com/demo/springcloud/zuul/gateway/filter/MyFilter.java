package com.demo.springcloud.zuul.gateway.filter;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;



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
 * 配合网易云笔记里边的zuul过滤器图看
 * 
 * @author yuezh2   2018年8月28日 下午9:23:26
 *
 */
public class MyFilter extends ZuulFilter {

	
	@Override
	public Object run() throws ZuulException {
		System.out.println("执行MyFilter过滤器");
		return true;
	}

	
	/**
	 * 判断是否要执行
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	
	/**
	 * 执行的优先级
	 */
	@Override
	public int filterOrder() {
		return 1;
	}

	/**
	 * 过滤器类型
	 */
	@Override
	public String filterType() {
		return FilterConstants.ROUTE_TYPE;
	}

}
