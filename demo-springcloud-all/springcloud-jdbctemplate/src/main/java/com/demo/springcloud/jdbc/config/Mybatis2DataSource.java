package com.demo.springcloud.jdbc.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月20日 下午4:18:36
 *
 */
@Configuration
@PropertySource("classpath:datasource.properties")
@MapperScan(basePackages = "com.demo.springcloud.jdbc", sqlSessionTemplateRef  = "sentinelSqlSessionTemplate")
public class Mybatis2DataSource {

    @Bean(name = "sentinelData")
    @ConfigurationProperties(prefix = "spring.datasource.sentinel") // application.properteis中对应属性的前缀
//    @Primary		//和jdbctemplate的datasource冲突才注释的
    public DataSource sentinelData() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sentinelSqlSessionFactory")
//    @Primary
    public SqlSessionFactory sentinelSqlSessionFactory(@Qualifier("sentinelData") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/sentinel/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "sentinelTransactionManager")
//    @Primary
    public DataSourceTransactionManager sentinelTransactionManager(@Qualifier("sentinelData") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sentinelSqlSessionTemplate")
//    @Primary
    public SqlSessionTemplate sentinelSqlSessionTemplate(@Qualifier("sentinelSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}