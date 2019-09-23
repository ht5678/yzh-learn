package mybatis.executor;

import mybatis.binding.MapperMethod;
import mybatis.session.Configuration;
import mybatis.statement.StatementHandler;

/**
 */
public class SimpleExecutor implements Executor {

	private Configuration configuration;
	
	
	public SimpleExecutor(Configuration configuration) {
		this.configuration = configuration;
	}
	
	
	@Override
	public <T> T query(MapperMethod method , Object parameter) throws Exception {
		StatementHandler statementHandler = new StatementHandler(configuration);
		return statementHandler.query(method,parameter);
	}


}
