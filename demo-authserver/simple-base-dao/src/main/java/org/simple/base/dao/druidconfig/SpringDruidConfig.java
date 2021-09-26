package org.simple.base.dao.druidconfig;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.simple.base.dao.support.DruidDataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 * @author yue
 *
 */
@EnableConfigurationProperties(value=DruidDataSourceProperties.class)
@Configuration
@MapperScan(basePackages="com.simple.base.dao")
public class SpringDruidConfig {

	@Autowired
	private DruidDataSourceProperties druidDataSourceProperties;
	
	
	@Bean
	public DataSource dataSource() throws SQLException{
		System.out.println(druidDataSourceProperties);
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUsername(druidDataSourceProperties.getUsername());
		druidDataSource.setPassword(druidDataSourceProperties.getPassword());
		druidDataSource.setUrl(druidDataSourceProperties.getJdbcUrl());
		druidDataSource.setDriverClassName(druidDataSourceProperties.getDriverClassName());
		druidDataSource.setInitialSize(druidDataSourceProperties.getInitialSize());
		druidDataSource.setMinIdle(druidDataSourceProperties.getMinIdle());
		druidDataSource.setMaxActive(druidDataSourceProperties.getMaxActive());
		druidDataSource.setMaxWait(druidDataSourceProperties.getMaxWait());
		druidDataSource.setPoolPreparedStatements(druidDataSourceProperties.isPoolPreparedStatements());
		
		return druidDataSource;
	}
	
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath:/mybatis/mapper/**/*.xml"));
		sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/mybatis/mybatis-config.xml"));
		sqlSessionFactoryBean.setDataSource(dataSource());
		return sqlSessionFactoryBean;
	}
	
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		return sqlSessionTemplate;
	}
	
	
	@Bean
	public PlatformTransactionManager platformTransactionManager()throws SQLException{
		PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource());
		return platformTransactionManager;
	}
	
}
