package com.demo.springcloud.jdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springcloud.jdbc.simple.SimpleDao;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月18日 下午5:45:36
 *
 */
@RestController
public class SimpleController {

	@Autowired
	private SimpleDao simpleDao;
	
	
	@RequestMapping(value="simple")
	public String testSimple(){
		simpleDao.testTemplate1();
		simpleDao.testTemplate2();
		return "";
	}
	
	
}
