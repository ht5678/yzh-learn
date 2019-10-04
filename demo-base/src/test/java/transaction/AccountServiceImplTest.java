package transaction;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import transaction.service.AccountService;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月4日 下午11:30:16
 *
 */
public class AccountServiceImplTest {

	@org.junit.Test
    public void addAccount() throws Exception {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring-tx.xml");
        AccountService service = context.getBean(AccountService.class);
        service.addAccount("luban", 10000);
    }
}
