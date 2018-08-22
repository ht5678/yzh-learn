package com.demo.springcloud.cloud;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
public class PoliceController {
	
	
	
	@RequestMapping(value="hello",produces=MediaType.APPLICATION_JSON_VALUE)
	public String hello(HttpServletRequest request){
		return "helloworld";
	}

	
	
	@RequestMapping(value="call/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public Police call(@PathVariable Integer id,HttpServletRequest request){
		Police p = new Police();
		p.setId(1);
		p.setName("zahngsa");
		p.setMessage(request.getRequestURL().toString());
		
		return p;
	}
	
	
	
	@RequestMapping(value="person/create",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public String createPerson(@RequestBody Police p){
		System.out.println(p.getName()+"---"+p.getMessage());
		return "success , id:"+p.getId();
	}
	
	
	
	@RequestMapping(value="person/createXML",method=RequestMethod.POST
			,consumes=MediaType.APPLICATION_XML_VALUE
			,produces=MediaType.APPLICATION_XML_VALUE)
	public String createXMLPerson(@RequestBody Police p){
		System.out.println(p.getName()+"---"+p.getMessage());
		return "<result><message>success</message></result>";
	}
	
	
	
	@RequestMapping(value="hello/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
	public String helloName(@PathVariable String name , HttpServletRequest request){
		return "hello"+name;
	}
	
}
