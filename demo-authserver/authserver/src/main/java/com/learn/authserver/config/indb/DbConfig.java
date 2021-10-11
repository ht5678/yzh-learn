package com.learn.authserver.config.indb;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月11日上午10:01:52
 */
@Configuration
public class DbConfig {

	
	@ConfigurationProperties("spring.datasource")
	@Bean
	public DataSource dataSource() {
		return new DruidDataSource();
	}
	
}
