package org.demo.netty.queue.hazelcast;

import org.demo.netty.queue.CustomQueueFactoryStrategy;
import org.demo.netty.session.CustomQueue;

import com.hazelcast.config.QueueConfig;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月22日 下午2:57:46
 */
public class ClusteredCustomQueueFactoryStrategy implements CustomQueueFactoryStrategy{

	private final static String HZ_QUEUE_CONFIG_NAME = "default";
	
	private HazelcastInstance hazelcastInstance;
	
	
	
	/**
	 * 
	 * @param hazelcastInstance
	 */
	public ClusteredCustomQueueFactoryStrategy(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
	}
	
	
	@Override
	public <E> CustomQueue<E> createOCQueue(String name, int emptyQueueTtl) {
		QueueConfig queueConfig = hazelcastInstance.getConfig().getQueueConfig(HZ_QUEUE_CONFIG_NAME);
		queueConfig.setEmptyQueueTtl(emptyQueueTtl);
		return new ClusteredCustomQueue<>(name , hazelcastInstance.getQueue(name) , hazelcastInstance.getLock(name));
	}

	
	
}
