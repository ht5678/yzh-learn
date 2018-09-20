package com.demo.springcloud.jdbc.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 
 * 首先要将spring boot自带的DataSourceAutoConfiguration禁掉，因为它会读取application.properties文件的spring.datasource.*属性并自动配置单数据源。在@SpringBootApplication注解中添加exclude属性即可：

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
 * 
 * @author yuezh2   2018年9月18日 下午5:28:52
 *
 */
@SpringBootApplication(scanBasePackages={"com.demo.springcloud.jdbc"},
exclude = {
        DataSourceAutoConfiguration.class
})
@EnableEurekaServer
public class ServerApp {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ServerApp.class, args);
	}

}
