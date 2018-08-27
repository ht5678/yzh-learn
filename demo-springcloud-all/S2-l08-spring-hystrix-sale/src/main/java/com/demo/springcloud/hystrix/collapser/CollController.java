package com.demo.springcloud.hystrix.collapser;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springcloud.hystrix.simple.Member;

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
 * @author yuezh2   2018年8月27日 上午11:10:08
 *
 */
@RestController
public class CollController {

	@Autowired
	private CollService collService;
	
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/coll",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public String testCollapser()throws Exception{
		//合并3次请求
		Future<Member> f1 = collService.getMember(1);
		Future<Member> f2 = collService.getMember(2);
		Future<Member> f3 = collService.getMember(3);
		
		Member p1 = f1.get();
		Member p2 = f2.get();
		Member p3 = f3.get();
		
		
		return "";
	}
	
	
}
