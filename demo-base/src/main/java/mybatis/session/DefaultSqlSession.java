package mybatis.session;

import java.lang.reflect.Proxy;
import java.sql.SQLException;

import mybatis.binding.MapperMethod;
import mybatis.binding.MapperProxy;
import mybatis.executor.Executor;
import mybatis.statement.StatementHandler;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年9月17日 下午8:35:37  
 *
 */
public class DefaultSqlSession implements SqlSession{
	
	private Configuration configuration;
	
	private Executor executor;

	
	public DefaultSqlSession(Configuration configuration , Executor executor) {
		this.configuration = configuration;
		this.executor = executor;
	}
	
	
	
	public <T> T getMapper(Class<T> type) {
		return (T)Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, new MapperProxy<>(this , type) );
	}
	
	
	
	@Override
	public <T> T selectOne(MapperMethod mapperMethod , Object statement) throws Exception {
		return executor.query(mapperMethod, statement);
	}



	public Configuration getConfiguration() {
		return configuration;
	}
	
	

}
