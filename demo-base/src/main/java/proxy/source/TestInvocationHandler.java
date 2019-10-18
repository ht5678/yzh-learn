package proxy.source;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年10月18日 下午5:23:13  
 *
 */
public class TestInvocationHandler implements InvocationHandler{

	private TestDao testDao;
	
	
	public TestInvocationHandler(TestDao testDao) {
		this.testDao = testDao;
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("jdk");
		testDao.query();
		return null;
	}

}
