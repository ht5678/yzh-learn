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
 * @date 2019年11月27日 下午1:53:18  
 *
 */
@ContextConfiguration(locations = {
//		"classpath:dubbo/applicationContext-resources.xml",
        "classpath:dubbo/dubbo-consumers2.xml",
//        "classpath:dubbo/dubbo-providers.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class DubboConsumerTest2{
	
	@Autowired
	private Demo2DubboService demoDubbo;
	
	
	@Test
	public void test() throws Exception{
		demoDubbo.test2();
		System.out.println("consumer test2");
		Thread.sleep(100000);
	}
	
	
}
