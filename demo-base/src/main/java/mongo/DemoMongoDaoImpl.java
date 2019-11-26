package mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年11月26日 下午3:09:44  
 *
 */
@Component
public class DemoMongoDaoImpl implements DemoMongoDao {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	
    public MongoOrder queryOne(Query query) {
        return this.mongoTemplate.findOne(query, MongoOrder.class);
    }
	

}
