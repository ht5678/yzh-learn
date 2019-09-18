package mybatis.session;

import mybatis.executor.Executor;

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
	
	
	@Override
	public <T> T selectOne(String statement) {
		
		return null;
	}
	
	
	

}
