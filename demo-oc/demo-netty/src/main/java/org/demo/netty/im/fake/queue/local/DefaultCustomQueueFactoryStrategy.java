package org.demo.netty.im.fake.queue.local;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.demo.netty.im.fake.queue.CustomQueueFactoryStrategy;
import org.demo.netty.im.fake.session.CustomQueue;
import org.demo.netty.im.fake.session.DefaultCustomQueue;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月22日 下午2:41:31
 */
public class DefaultCustomQueueFactoryStrategy implements CustomQueueFactoryStrategy{

	private static Map<String, CustomQueue<?>> queues = new ConcurrentHashMap<>();
	
	
	@Override
	public <E> CustomQueue<E> createOCQueue(String name, int emptyQueueTtl) {
		CustomQueue<?> queue = queues.get(name);
		
		if(null == queue) {
			queue = new DefaultCustomQueue<>(name);
			queues.put(name , queue);
		}
		
		return (CustomQueue<E>)queue;
	}

}
