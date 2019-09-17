package mybatis.executor;

import java.util.List;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年9月17日 下午8:33:29  
 *
 */
public interface Executor {

	
	<T> T query();
	
}
