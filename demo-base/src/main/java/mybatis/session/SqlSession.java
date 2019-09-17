package mybatis.session;


/**
 * 
 * @author yuezh2
 *
 * @date 2019年9月17日 下午8:33:45  
 *
 */
public interface SqlSession {


	public <T> T selectOne(String statement);
	
	
	
}
