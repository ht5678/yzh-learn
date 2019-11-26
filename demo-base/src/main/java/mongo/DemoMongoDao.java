package mongo;

import org.springframework.data.mongodb.core.query.Query;

/**
 * 
 * @author yuezh2
 *
 * @date 2019年11月26日 下午3:09:14  
 *
 */
public interface DemoMongoDao{

	public MongoOrder queryOne(Query query);
	
}
