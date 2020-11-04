package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;

import org.apache.commons.dbcp2.BasicDataSource;


/**
 * 
 * @author yuezh2
 *
 * @date 2020年10月30日 下午5:54:51  
 *
 */
public class TestDbcp2 {
	
	
	    public static BasicDataSource ds = null;
	    
	    //以下四个参数根据实际情况更改
	    
	    public final static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	    public final static String USER_NAME = "username";
	    public final static String PASSWORD = "12345";
	    public final static String DB_URL = "jdbc:mysql://mysql.host:3306/?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=UTC";

	    /*数据库连接池初始化方法*/

	    public static void dbPoolInit() {
	        ds = new BasicDataSource();
	        ds.setDriverClassName(DRIVER_NAME);
	        ds.setUsername(USER_NAME);
	        ds.setPassword(PASSWORD);
	        ds.setUrl(DB_URL);

	    }
	    /*
	    * 数据库连接池调用连接
	    * */
	    @org.junit.Test
	    public void dbPoolTest() {
	    	dbPoolInit();
	    	
	        Connection con = null;
	        Statement sta = null;
	        ResultSet result = null;
	        try {
	            con = ds.getConnection();
	            sta = con.createStatement();
	            result = sta.executeQuery("select * from ordercenter.order_holding where id=9");
	            while (result.next()) {
//	                System.out.println(result.getString("name"));
	            	Instant instant = result.getTimestamp("start_time").toInstant();
	            	System.out.println(instant);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            /*
	            * 这里的close()方法区别于jdbc的Close()，
	            * 这里的连接池是重写了close()，所以
	            * close后不是关闭而是归还链接给连接池，
	            * jdbc的close()是关闭了连接
	            * */
	            try {
	                if (con != null) {
	                    con.close();
	                }
	                if (sta != null) {
	                    sta.close();
	                }
	                if (result != null) {
	                    result.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	    }


	    
}
