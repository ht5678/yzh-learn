package com.demo.springcloud.hystrix.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
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
 * @author yuezh2   2018年8月25日 下午10:13:38
 *
 */
@Service
@DefaultProperties(defaultFallback="getMemberFallback")//公共配置,不用每个方法上写
public class MemberService {

	
	@Autowired
	private RestTemplate restTpl;
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@HystrixCommand(fallbackMethod="getMemberFallback",groupKey="MemberGroup",commandKey="MemberCommand",
			commandProperties={//可以省略掉 : hystrix.command.default
					@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1000")
			},threadPoolProperties={//可以省略掉: hystrix.threadpool.default
					@HystrixProperty(name="coreSize",value="2")
			})
	public Member getMember(Integer id){
		//模拟超时情况 , 会出异常 , 但是不用管
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		//正常调用 , 如果把member给关掉 , 可以测试fallback方法
		Member member = restTpl.getForObject("http://hystrix-member/member/{id}", Member.class , id);
		return member;
	}
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Member getMemberFallback(Integer id){
		Member m = new Member();
		m.setId(id);
		m.setName("error member");
		
		return m;
	}
	
	
}
