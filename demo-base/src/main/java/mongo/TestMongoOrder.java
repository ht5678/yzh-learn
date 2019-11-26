package mongo;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年11月26日 下午3:11:51  
 *
 */
public class TestMongoOrder {

	public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("mongo/spring-mongodb.xml");
        DemoMongoDao demoMongoDao = context.getBean(DemoMongoDao.class);
        System.out.println(demoMongoDao);
        
//        MongoTemplate mongoTemplate = context.getBean(MongoTemplate.class);
//        System.out.println(mongoTemplate);
        
	}
	
}
