package mybatis.session;

import mybatis.executor.SimpleExecutor;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年9月18日 下午9:45:55  
 *
 */
public class SqlSessionFactory {

	
	public SqlSession openSession(Configuration configuration) {
		return new DefaultSqlSession(configuration , new SimpleExecutor(configuration));
	}
	
	
}
