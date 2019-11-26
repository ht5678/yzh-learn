package mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.WriteResult;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年11月26日 下午3:43:54  
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:mongo/spring-mongodb.xml"})
public class TestBase {
    @Autowired
    MongoTemplate mongoTemplate;

    
    @Test
    public void testUpdateOrder() {
    	Query query = new Query();
        query.addCriteria(new Criteria("orderCode").is("11140421108215").and("activeStatus").is("1"));
        Update update = new Update();


        //添加惠商新字段 支付积分
        update.set("payCash", "10560.90"); //现金支付

        update.set("payStatus", "1");//3--支付尾款，转换为1--已支付
        update.set("payDatetime", "20191125175505");
        update.set("payOrderNo", "2019112522001467640574582319");
        int result = mongoTemplate.updateFirst(query, update, MongoOrder.class).getN();
        System.out.println(result);
    }
    
    
    
    @Test
    public void testBase() {
        Query query = new Query();
        query.addCriteria(Criteria.where("test").is("test"));
        MongoOrder order = this.mongoTemplate.findOne(query, MongoOrder.class);
        System.out.println(order);
//        List<mongo> users = userRepository.findAll(query);
//        System.out.println(users.size());
    }
}
