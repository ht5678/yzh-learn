package com.demo.springcloud.hystrix.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springcloud.hystrix.simple.Member;
import com.demo.springcloud.hystrix.simple.MemberService;
import com.netflix.discovery.converters.Auto;

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
 * @author yuezh2   2018年8月26日 下午9:01:02
 *
 */
@RestController
public class CacheController {

	
	@Autowired
	private CacheService cacheService;
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/cache",method=RequestMethod.GET , produces=MediaType.APPLICATION_JSON_VALUE)
	public String cache(){
		for(int i = 0 ; i < 3 ; i++){
			cacheService.cacheMember(1);	//参数变了的话,是无法触发缓存机制的
		}
		return "";
	}
	
}
