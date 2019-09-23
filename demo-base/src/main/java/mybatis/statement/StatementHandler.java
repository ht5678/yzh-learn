package mybatis.statement;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import mybatis.binding.MapperMethod;
import mybatis.resultset.DefaultResultSetHandler;
import mybatis.session.Configuration;
import mybatis.util.DbUtil;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年9月24日 上午12:57:34
 *
 */
public class StatementHandler {
	
	private Configuration configuration;
	
	private DefaultResultSetHandler resultSetHandler = new DefaultResultSetHandler();
	
	public StatementHandler(Configuration configuration){
		this.configuration = configuration;
	}

	
	public <T> T query(MapperMethod method , Object parameter) 
			throws SQLException, NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException,InvocationTargetException{
		Connection connection = DbUtil.open();
		PreparedStatement preparedStatement = connection.prepareStatement(
				String.format(method.getSql(), (String)parameter));
		preparedStatement.execute();
		return resultSetHandler.handle(preparedStatement, method);
	}
	
	
}
