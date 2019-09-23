package mybatis.mapper;

import mybatis.pojo.User;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年9月23日 下午6:13:10  
 *
 */
public interface UserMapper {

	public User getUser(Integer id);
	
}
