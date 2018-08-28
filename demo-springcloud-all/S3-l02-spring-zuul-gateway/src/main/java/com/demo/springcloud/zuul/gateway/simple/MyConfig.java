package com.demo.springcloud.zuul.gateway.simple;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
 * @author yuezh2   2018年8月28日 下午4:26:14
 *
 */
@Configuration
public class MyConfig {

	
	/**
	 * 
	 * 自定义路由规则 , 
	 *
	 * 测试调用 /xxxxx/** , 将跳转到zuul-xxxxx-service 服务进行处理
	 * @return
	 */
	@Bean
	public PatternServiceRouteMapper patternServiceRouteMapper(){
//		return new PatternServiceRouteMapper(
//								"(zuul)-(?<module>.+)-(service)", "${module}/**");
		
		//测试的时候 , 需要把配置文件中的相关规则给屏蔽
		//测试调用/sale/** , 跳转到 zuul-sale 服务进行处理 , 在这里${module}等于url里边的前缀sale
		return new PatternServiceRouteMapper(
				"(zuul)-(?<module>.+)", "${module}/**");
	}
	
}
