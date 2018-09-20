package com.demo.springcloud.jdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springcloud.jdbc.mybatis1.SentinelMapper;
import com.demo.springcloud.jdbc.mybatis1.TaskModel;
import com.demo.springcloud.jdbc.mybatis2.OuterMapper;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月20日 下午4:27:24
 *
 */
@RestController
public class MybatisController {

	@Autowired
	private SentinelMapper mapper;
	
	@Autowired
	private OuterMapper mapper2;
	
	
	/**
	 * http://localhost:8761/mybatis2
	 * @return
	 */
	@RequestMapping(value="mybatis2")
	public String testSimple1(){
		TaskModel model = mapper2.getOneClan(1);
		System.out.println(model.toString());
		return "";
	}
	
	
	/**
	 * http://localhost:8761/mybatis1
	 * @return
	 */
	@RequestMapping(value="mybatis1")
	public String testSimple2(){
		TaskModel model = mapper.getOneClan(2);
		System.out.println(model.toString());
		return "";
	}
}
