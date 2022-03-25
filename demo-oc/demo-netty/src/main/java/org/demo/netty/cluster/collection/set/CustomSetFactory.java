package org.demo.netty.cluster.collection.set;

import java.util.Map;

import org.demo.netty.cluster.collection.set.cluster.ClusteredSetFactoryStrategy;
import org.demo.netty.cluster.collection.set.local.DefaultCustomSetFactoryStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;

import io.netty.util.internal.PlatformDependent;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午9:29:13
 */
public class CustomSetFactory {

	private static Logger log = LoggerFactory.getLogger(CustomSetFactory.class);
	
	
	private static Map<String, CustomSet<?>> sets = PlatformDependent.newConcurrentHashMap();
	
	private static CustomSetFactoryStategy ocQueueFactoryStrategy;
	private static CustomSetFactoryStategy clusteredOCQueueFactoryStrategy;
	private static CustomSetFactoryStategy localOCQueueFactoryStrategy;
	
	
	/**
	 * 
	 */
	private CustomSetFactory() {}
	
	
	static {
		localOCQueueFactoryStrategy = new DefaultCustomSetFactoryStrategy();
		ocQueueFactoryStrategy = localOCQueueFactoryStrategy;
	}
	
	
	/**
	 * 
	 * @param hi
	 */
	public static synchronized void startCluster(HazelcastInstance hi) {
		log.info("SetFactory 开启HazelcastInstance集群模式");
		clusteredOCQueueFactoryStrategy = new ClusteredSetFactoryStrategy(hi);
		ocQueueFactoryStrategy = clusteredOCQueueFactoryStrategy;
	}
	
	
	/**
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T extends CustomSet<?>> T createQueue(String name) {
		T t = (T)sets.get(name);
		
		if(null == t) {
			t  = (T)ocQueueFactoryStrategy.createOCSet(name);
			sets.put(name, t);
		}
		return t;
	}
	
	
}
