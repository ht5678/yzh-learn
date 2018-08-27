package com.demo.springcloud.hystrix.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;

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
 * @author yuezh2   2018年8月27日 下午1:53:37
 *
 */
@RestController
public class FeignController {
	
	@Autowired
	private HelloClient helloClient;
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/hello",method=RequestMethod.GET)
	public String hello(){
		return helloClient.hello();
	}
	
	
	
	/**
	 * 浏览器中一直请求 : http://localhost:8081/toHello   就可以看到效果了
	 * @return
	 */
	@RequestMapping(value="/toHello",method=RequestMethod.GET)
	public String toHello(){
		String result = helloClient.toHello();
		//5分钟之内会处于关闭状态,
		HystrixCircuitBreaker breaker = HystrixCircuitBreaker.Factory.getInstance(HystrixCommandKey.Factory.asKey("HelloClient#toHello()"));
		System.out.println("断路器状态:"+breaker.isOpen());
		return result;
	}
	

}
