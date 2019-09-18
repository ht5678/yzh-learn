package mybatis.session;

import java.io.IOException;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年9月18日 下午9:53:19  
 *
 */
public class SqlSessionFactoryBuilder {

	
	public SqlSessionFactory build(Configuration configuration)throws IOException {
		configuration.loadConfigurations();
		return new SqlSessionFactory();
	}
	
}
