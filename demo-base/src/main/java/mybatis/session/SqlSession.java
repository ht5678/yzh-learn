package mybatis.session;

import mybatis.binding.MapperMethod;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年9月17日 下午8:33:45  
 *
 */
public interface SqlSession {


	public <T> T selectOne(MapperMethod mapperMethod ,Object statement)throws Exception;
	
	
	
}
