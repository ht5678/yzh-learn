package mybatis.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年9月24日 上午1:00:51
 *
 */
public class DbUtil {
    /*
     * 打开数据库
     */
    private static String driver;//连接数据库的驱动
    private static String url;
    private static String username;
    private static String password;

    static {
      driver="com.mysql.jdbc.Driver";//需要的数据库驱动
      url="jdbc:mysql://lenovodb:3306/";//数据库名路径
      username="myuser";
      password="mypassword";
    }
    public static Connection open()
    {
      try {
        Class.forName(driver);
        return (Connection) DriverManager.getConnection(url,username, password);
      } catch (Exception e) {
        System.out.println("数据库连接失败！");
        e.printStackTrace();
      }//加载驱动

      return null;
    }

    /*
     * 关闭数据库
     */
    public static void close(Connection conn)
    {
      if(conn!=null)
      {
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }


  }

