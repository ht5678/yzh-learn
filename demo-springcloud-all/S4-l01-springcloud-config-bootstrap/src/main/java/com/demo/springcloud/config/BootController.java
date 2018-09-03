package com.demo.springcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
 * 这是一个引导程序
 * 
 * @author yuezh2   2018年9月3日 下午2:14:13
 *
 */
@RestController
public class BootController {

	
	@Autowired
	private ApplicationContext ctx;
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="hello",method=RequestMethod.GET)
	public String hello(){
		System.out.println("########");
		//有两个ApplicationContext
		System.out.println("     "+ctx);
		System.out.println("     "+ctx.getParent());
		System.out.println("########getProperty");
		Environment env = ctx.getEnvironment();
		System.out.println(env.getProperty("test.user.name"));
		return "success";
	}
	
	
}
