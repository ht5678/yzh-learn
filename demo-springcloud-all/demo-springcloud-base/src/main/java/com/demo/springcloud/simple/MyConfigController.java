package com.demo.springcloud.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * 自定义配置文件启动
 * 
 * @author yuezh2   2018年8月16日 下午7:15:51
 *
 */
@Controller
public class MyConfigController {

	
	@Autowired
	private ApplicationContext ctx;
	
	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="prop")
	public String getName(){
		
		return ctx.getEnvironment().getProperty("test.user.name");
	}
	
	
	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="prop1")
	public String getName1(){
		
		return ctx.getEnvironment().getProperty("test.user.name1");
	}
	
	
}
