package mybatis.resultset;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import mybatis.binding.MapperMethod;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年9月24日 上午1:52:07
 *
 */
public interface ResultSetHandler {
	
	public <T> T handle(PreparedStatement pstmt , MapperMethod mapperMethod) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, 
					InvocationTargetException, SQLException;

}
