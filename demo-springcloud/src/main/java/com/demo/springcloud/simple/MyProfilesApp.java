package com.demo.springcloud.simple;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

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
 * @author yuezh2   2018年8月16日 下午7:49:03
 *
 */
@SpringBootApplication
public class MyProfilesApp {
	
	
	/**
	 * 通过指定  linux或者windows使用不同的配置 ,
	 * 测试:
	 * http://localhost:8080/prop1
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new SpringApplicationBuilder(MyProfilesApp.class)
				.properties("spring.profiles.active=linux","server.port=9999").run(args);
	}

}
