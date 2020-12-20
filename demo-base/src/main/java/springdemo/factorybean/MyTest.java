package springdemo.factorybean;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年12月10日 下午8:03:13  
 *
 */
public class MyTest {
    @Before
    public void before() {
        System.out.println("---测试开始---\n");
    }

    @After
    public void after() {
        System.out.println("\n---测试结束---");
    }

    @Test
    public void testStudentFactoryBean() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springdemo/testfactorybean.xml");
        System.out.println(applicationContext.getBean("student"));
        
        //BeanFactory中有一个字符常量String FACTORY_BEAN_PREFIX = "&"; 
        //当我们去获取BeanFactory类型的bean时，如果beanName不加&则获取到对应bean的实例；
        //如果beanName加上&，则获取到BeanFactory本身的实例；
        System.out.println(applicationContext.getBean("&student"));
    }
}