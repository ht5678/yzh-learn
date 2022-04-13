package org.demo.netty.im.fake.queue;

import java.util.Map;

import org.demo.netty.im.fake.queue.hazelcast.ClusteredCustomQueueFactoryStrategy;
import org.demo.netty.im.fake.queue.local.DefaultCustomQueueFactoryStrategy;
import org.demo.netty.im.fake.session.CustomQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;

import io.netty.util.internal.PlatformDependent;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月22日 下午2:34:05
 */
public class CustomQueueFactory {
	
	private static Logger log = LoggerFactory.getLogger(CustomQueueFactory.class);
	
	private static Map<String, CustomQueue<?>> queues = PlatformDependent.newConcurrentHashMap();
	
	
	private static CustomQueueFactoryStrategy queueFactoryStrategy;
	private static CustomQueueFactoryStrategy clusteredQueueFactoryStartegy;
	private static CustomQueueFactoryStrategy localQueueFactoryStrategy;
	
	
	private CustomQueueFactory() {}
	
	
	static {
		localQueueFactoryStrategy = new DefaultCustomQueueFactoryStrategy();
		queueFactoryStrategy = localQueueFactoryStrategy;
	}
	
	
	public static synchronized void startCluster(HazelcastInstance hi) {
		log.info("QueueFactory开启Hazelcast集群模式");
		clusteredQueueFactoryStartegy = new ClusteredCustomQueueFactoryStrategy(hi);
		queueFactoryStrategy = clusteredQueueFactoryStartegy;
	}
	

	
	public static <T extends CustomQueue<?>> T createQueue(String name , int emptyQueueTtl) {
		T t = (T)queues.get(name);
		if(null == t) {
			t = (T)queueFactoryStrategy.createOCQueue(name, emptyQueueTtl);
			queues.put(name, t);
		}
		return t;
	}
	
	
}
