package com.demo.springcloud.first;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TestController {
	
	
	@Autowired
	private HelloClient helloClient;
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/routerclient",method=RequestMethod.GET)
	public String router(){
		String result = helloClient.hello("zhangsan");
		return result;
	}
	
	
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/police",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public Police getPolice(){
		Police p = helloClient.getPolice("1");
		return p;
	}
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/myhello",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public String myhello(){
		String result = helloClient.myhello();
		return result;
	}
	
	

}
