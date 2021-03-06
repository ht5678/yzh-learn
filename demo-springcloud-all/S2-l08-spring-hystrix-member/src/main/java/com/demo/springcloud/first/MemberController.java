package com.demo.springcloud.first;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
 * 
 * @author yuezh2   2018年8月17日 下午3:51:13
 *
 */
@RestController
public class MemberController {

	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="member/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public Member call(@PathVariable Integer id){
		Member p = new Member();
		p.setId(1);
		p.setName("zahngsa");
		return p;
	}
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/hello",method=RequestMethod.GET)
	public String hello(){
		return "hello";
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/toHello",method=RequestMethod.GET)
	public String toHello(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "timeout hello";
	}
	
}
