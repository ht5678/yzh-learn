package com.demo.springcloud.first;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

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
 * @author yuezh2   2018年8月17日 下午3:53:53
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class MemberServer {

	
	public static void main(String[] args) {
		/** 判断：Servlet还是Reactive应用
			Servlet：spring原始框架就是为Servlet API和Servlet容器创建的；
			Reactive：是spring 5之后支持的一个web应用架构，集成Reactive的是：spring webflux 具体参见：https://springcloud.cc/web-reactive.html
			Servlet和Reactive的选择：https://www.infoq.com/news/2017/12/servlet-reactive-stack
		*/
		new SpringApplicationBuilder(MemberServer.class).web(true).run(args);
	}
	
}
