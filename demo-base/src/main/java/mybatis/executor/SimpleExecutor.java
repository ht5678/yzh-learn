package mybatis.executor;

import java.util.List;

import mybatis.session.Configuration;

/**
 */
public class SimpleExecutor implements Executor {

	private Configuration configuration;
	
	
	public SimpleExecutor(Configuration configuration) {
		this.configuration = configuration;
	}
	
	
	@Override
	public <T> T query() {
		return null;
	}


}
