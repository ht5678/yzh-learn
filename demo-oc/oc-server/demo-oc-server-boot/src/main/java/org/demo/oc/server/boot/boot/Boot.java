package org.demo.oc.server.boot.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月24日下午6:19:46
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages="org.demo.oc.server.boot")
@MapperScan(basePackages="org.demo.oc.server.boot.mapper")
@ServletComponentScan
@Configuration
public class Boot {

//	public springcontext
	
}
