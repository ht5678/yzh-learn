package com.demo.springcloud.jdbc.simple;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 * 
 * @author yuezh2   2018年9月18日 下午5:36:51
 *
 */
@Component
public class SimpleDaoImpl implements SimpleDao {


	
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;
 
    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate2;
    
    
    /**
     * 
     */
    public void testTemplate1(){
    	jdbcTemplate1.query("select count(*) as count from club.common_button ", new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				while(rs.next()){
					System.out.println("-------------------------------------------");
					System.out.println("测试第一个数据源");
					System.out.println(rs.getInt("count"));
				}
				return null;
			}
    		
    	});
    }
    
    
    /**
     * 
     */    
    public void testTemplate2(){
    	jdbcTemplate2.query("select count(*) as count from club.common_button ", new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				while(rs.next()){
					System.out.println("-------------------------------------------");
					System.out.println("测试第2个数据源");
					System.out.println(rs.getInt("count"));
				}
				return null;
			}
    		
    	});
    }
	
	
}
