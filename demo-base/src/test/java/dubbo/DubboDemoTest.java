package dubbo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年11月27日 下午3:37:08  
 *
 */
@ContextConfiguration(locations = {
//		"classpath:dubbo/applicationContext-resources.xml",
        "classpath:dubbo/dubbo-consumers.xml",
        "classpath:dubbo/dubbo-providers2.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class DubboDemoTest {

	
//	@Autowired
//	private Demo2DubboService demo2DubboServiceImpl;
	
	
	@Test
	public void test() throws Exception{
//		demo2DubboServiceImpl.test2();
		System.out.println("test");
		Thread.sleep(10000);
	}
	
}
