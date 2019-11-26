package mongo;

import java.util.ArrayList;
import java.util.List;

import org.HdrHistogram.WriterReaderPhaser;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

public class TestMongoDBShards {

    public static void main(String[] args) {
        try {
            List<ServerAddress> addresses = new ArrayList<ServerAddress>();
            ServerAddress address1 = new ServerAddress("test" , 27017);
//            ServerAddress address2 = new ServerAddress("192.168.221.131" , 23000);
//            ServerAddress address3 = new ServerAddress("192.168.221.132" , 23000);
            addresses.add(address1);
//            addresses.add(address2);
//            addresses.add(address3);
            MongoClient client = new MongoClient(addresses);
//            DB db = client.getDB( "b2c" );
            MongoCredential credential = MongoCredential.createScramSha1Credential("test", "test", "test".toCharArray()); 
            
            List credentials = new ArrayList(); 
            credentials.add(credential); 

            //通过连接认证获取MongoDB连接 

            MongoClient mongoClient = new MongoClient(addresses,credentials); 
            
            
            
//            DBCollection coll = db.getCollection( "mongoorder" );
//            BasicDBObject object = new BasicDBObject();
//            object.append( "id" , 100);
//            DBObject dbObject = coll.findOne(object);
//            System.out .println(dbObject);
            DB db = mongoClient.getDB("test");
//            Collection<DB> dbs = mongoClient.getUsedDatabases();
//            for(DB db : dbs) {
//            	System.out.println(db.getName());
//            }
//            MongoDatabase mongoDatabase = mongoClient.getDatabase("b2c"); 

            System.out.println("Connect to database successfully"); 
            
            DBCollection collection = db.getCollection("mongoorder");
            
            BasicDBObject object = new BasicDBObject();
            object.append( "id" , 100);
            DBObject dbObject = collection.findOne(object);
            
            BasicDBObject object2 = new BasicDBObject();
            object2.append( "id" , 1200);
//            dbObject.put("xx", "ss");
            
            
            
            WriteResult wr = collection.update(dbObject, object2);
            System.out.println(wr);
//            collection.updateMany(Filters.eq("Age",30), new Document("$set",new Document("Age",50)));  
//            //检索查看结果  
//            FindIterable<Document> findIterable = collection.find();  
//            MongoCursor<Document> mongoCursor = findIterable.iterator();  
//            while(mongoCursor.hasNext()){  
//               System.out.println(mongoCursor.next());  
//            }  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
