package com.demo.springcloud.hystrix.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.springcloud.hystrix.simple.Member;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;

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
 * @author yuezh2   2018年8月26日 下午8:58:17
 *
 */
@Service
public class CacheService {
	
	
	@Autowired
	private RestTemplate restTpl;
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@CacheResult//需要配合hystrixCommand一起使用,不然没有效果
	@HystrixCommand
	public Member cacheMember(Integer id){
		System.out.println("调用了CacheMember方法");
//		Member m = restTpl.getForObject("http://hystrix-member/{id}", Member.class,id);
//		return m;
		return null;
	}
	
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@CacheResult
	@HystrixCommand(commandKey="cacheKey")
	public String getCache(Integer id){
		System.out.println("执行getCache方法");
		return null;
	}
	
	
	/**
	 * 需要有和getCache相同的参数和参数值 , 否则会删除缓存失效
	 * @param id
	 */
	@CacheRemove(commandKey="cacheKey")
	@HystrixCommand
	public void removeCache(Integer id){
		System.out.println("执行removeCache方法");
	}

}
