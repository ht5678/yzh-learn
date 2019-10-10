package transaction;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import transaction.service.UserSerivce;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月4日 下午11:30:53
 *
 */
public class UserSerivceImplTest {

	@Test
    public void createUser() throws Exception {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("transaction/spring-tx.xml");
        UserSerivce service = context.getBean(UserSerivce.class);
        service.createUser("wangwu");
    }

}