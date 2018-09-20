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
 * @Primary 标志这个 Bean 如果在多个同类 Bean 候选时，该 Bean 优先被考虑。「多数据源配置的时候注意，必须要有一个主数据源，用 @Primary 标志该 Bean」
@MapperScan 扫描 Mapper 接口并容器管理，包路径精确到 master，为了和下面 cluster 数据源做到精确区分
@Value 获取全局配置文件 application.properties 的 kv 配置,并自动装配
sqlSessionFactoryRef 表示定义了 key ，表示一个唯一 SqlSessionFactory 实例
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