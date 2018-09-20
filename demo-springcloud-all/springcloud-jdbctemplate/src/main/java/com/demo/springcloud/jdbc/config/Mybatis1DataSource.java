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
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


/**
 * 
 * 
 * @Primary 标志这个 Bean 如果在多个同类 Bean 候选时，该 Bean 优先被考虑。「多数据源配置的时候注意，必须要有一个主数据源，用 @Primary 标志该 Bean」
@MapperScan 扫描 Mapper 接口并容器管理，包路径精确到 master，为了和下面 cluster 数据源做到精确区分
@Value 获取全局配置文件 application.properties 的 kv 配置,并自动装配
sqlSessionFactoryRef 表示定义了 key ，表示一个唯一 SqlSessionFactory 实例


 * @author yuezh2   2018年9月20日 下午4:20:14
 *
 */
@Configuration
@PropertySource("classpath:datasource.properties")
@MapperScan(basePackages = "com.demo.springcloud.jdbc", sqlSessionTemplateRef  = "outerSqlSessionTemplate")
public class Mybatis1DataSource {

    @Bean(name = "outerData")
    @ConfigurationProperties(prefix = "spring.datasource.outer") // application.properteis中对应属性的前缀
    public DataSource outData() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "outerSqlSessionFactory")
    public SqlSessionFactory outerSqlSessionFactory(@Qualifier("outerData") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/outer/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "outerTransactionManager")
    public DataSourceTransactionManager outerTransactionManager(@Qualifier("outerData") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "outerSqlSessionTemplate")
    public SqlSessionTemplate outerSqlSessionTemplate(@Qualifier("outerSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}