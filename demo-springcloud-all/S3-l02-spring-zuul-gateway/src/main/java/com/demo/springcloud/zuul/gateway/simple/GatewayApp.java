package com.demo.springcloud.zuul.gateway.simple;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;

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
 * @author yuezh2   2018年8月28日 下午2:56:45
 *
 */
@SpringBootApplication(scanBasePackages={"com.demo.springcloud.zuul.gateway"})
@EnableEurekaClient
@EnableZuulProxy
public class GatewayApp {
	
	
	@PostConstruct
	public void zuulInit(){
		FilterLoader.getInstance().setCompiler(new GroovyCompiler());
		//设置动态加载zuul的filter目录 , 默认为 src/main/java/groovy/filters
		String scriptRoot = System.getProperty("zuul.filter.root","groovy/filters/");
		//设置扫描刷新时间
		String refreshInterval = System.getProperty("zuul.filter.refreshInterval","5");
		if(scriptRoot.length()>0){
			scriptRoot = scriptRoot + File.separator;
		}
		
		try {
			FilterFileManager.setFilenameFilter(new GroovyFileFilter());
			
			// pre  |  route  |  post  代表3个阶段的过滤器
			FilterFileManager.init(Integer.parseInt(refreshInterval), scriptRoot+"pre",scriptRoot+"route",scriptRoot+"post");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new SpringApplicationBuilder(GatewayApp.class).web(true).run(args);
	}
}
