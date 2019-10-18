package proxy.source;

import java.io.FileOutputStream;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年10月18日 下午5:25:09  
 *
 */
public class TestMain {
	
	
	
	public static void main(String[] args) throws Exception{
		//
		byte[] bytes = ProxyGenerator.generateProxyClass("$ProxyTl", new Class[] {TestDao.class});
		FileOutputStream fos = new FileOutputStream("d://$ProxyTl.class");
		fos.write(bytes);
		fos.flush();
		fos.close();
		
		//
		TestDao dao = (TestDao)Proxy.newProxyInstance(TestMain.class.getClassLoader(), new Class[] {TestDao.class}, new TestInvocationHandler(new TestDaoImpl()));
		dao.query();
	}

}
