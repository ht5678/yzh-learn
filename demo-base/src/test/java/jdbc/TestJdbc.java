package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年10月30日 下午5:45:23  
 *
 */
public class TestJdbc {

	
	@Test
	public void test() {
        String sql = "select * from ordercenter.order_holding where id=9";
        Connection conn = DbUtil.open();
        try {
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs  = pstmt.executeQuery();
            while(rs.next()) {
            	Instant instant = rs.getTimestamp("start_time").toInstant();
            	System.out.println(instant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(conn);
        }
	}
	
	
	
}
