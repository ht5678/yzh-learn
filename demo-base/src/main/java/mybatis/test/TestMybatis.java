package mybatis.test;

import java.io.InputStream;

import mybatis.mapper.UserMapper;
import mybatis.pojo.User;
import mybatis.session.Configuration;
import mybatis.session.DefaultSqlSession;
import mybatis.session.SqlSessionFactory;
import mybatis.session.SqlSessionFactoryBuilder;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年9月23日 下午6:00:15  
 *
 */
public class TestMybatis {

	
	public static void main(String[] args) throws Exception{
		InputStream inputStream = TestMybatis.class.getClassLoader().getResourceAsStream("mybatis/demo/quickstart/mybatis-config.xml");
		Configuration configuration = new Configuration();
		configuration.setInputStream(inputStream);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		DefaultSqlSession sqlSession = (DefaultSqlSession)sqlSessionFactory.openSession(configuration);
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = userMapper.getUser(1);
		System.out.println(user);
		
	}
	
	
}
