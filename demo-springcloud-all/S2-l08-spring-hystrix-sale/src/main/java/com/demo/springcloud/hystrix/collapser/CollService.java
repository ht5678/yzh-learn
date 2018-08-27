package com.demo.springcloud.hystrix.collapser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.demo.springcloud.hystrix.simple.Member;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

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
 * @author yuezh2   2018年8月27日 上午11:05:45
 *
 */
@Service
public class CollService {

	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@HystrixCollapser(batchMethod="getMembers",collapserProperties={
			@HystrixProperty(name="timerDelayInMilliseconds",value="1000")		//收集1s内的请求,合并处理
	})
	public Future<Member> getMember(Integer id){
		System.out.println("执行getMember方法");
		return null;
	}
	
	
	
	@HystrixCommand
	public List<Member> getMembers(List<Integer> ids){
		List<Member> ms = new ArrayList<>();
		for(Integer id : ids){
			Member m = new Member();
			m.setId(id);
			m.setName("z"+id);
			ms.add(m);
			System.out.println(id);
		}
		return ms;
	}
	
	
}
