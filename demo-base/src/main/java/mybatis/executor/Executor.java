package mybatis.executor;

import mybatis.binding.MapperMethod;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年9月17日 下午8:33:29  
 *
 */
public interface Executor {

	
	<T> T query(MapperMethod method , Object parameter)throws Exception;
	
}
