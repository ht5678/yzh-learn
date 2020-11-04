package jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年10月30日 下午5:44:51  
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
        driver = "com.mysql.jdbc.Driver";//需要的数据库驱动
        url = "jdbc:mysql://mysql.host:3306/?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=UTC";//数据库名路径
        username = "username";
        password = "12345";
    }

    public static Connection open() {
        try {
            Class.forName(driver);
            return (Connection) DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("数据库连接失败！");
            e.printStackTrace();
        }//加载驱动
        return null;
    }

    /*
     * 关闭数据库
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}