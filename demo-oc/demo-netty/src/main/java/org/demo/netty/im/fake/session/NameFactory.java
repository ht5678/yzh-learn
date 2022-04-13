package org.demo.netty.im.fake.session;

import org.demo.netty.im.fake.cluster.collection.cache.Cache;
import org.demo.netty.im.fake.cluster.collection.cache.CacheFactory;
import org.demo.netty.im.fake.queue.CustomQueueFactory;

/**
 * 管理缓存
 * @author yuezh2
 * @date	  2022年3月22日 下午2:24:20
 */
public class NameFactory {

	private final static String SEPARATOR = ":";
	
	
	private String fitName(String prefix , String... ids) {
		for(String id : ids) {
			prefix = prefix  + SEPARATOR +id;
		}
		return prefix;
	}
	
	
	/**
	 * 
	 * @param <T>
	 * @return
	 */
	public <T extends Cache<?, ?>> T getCache(String prefix , String... ids ) {
		String cacheName = fitName(prefix, ids);
		Cache<?, ?> createCache = CacheFactory.createCache(cacheName , -1 , -1);
		return (T)createCache;
	}
	
	
	/**
	 * 
	 * @param <T>
	 * @return
	 */
	public <T extends Cache<?, ?>> T getCache(String name) {
		Cache<?, ?> createCache = CacheFactory.createCache(name, -1, -1);
		return (T)createCache;
	}
	
	
	
	public <T extends CustomQueue<?>> T getQueue(String prefix , String... ids) {
		String queueName = fitName(prefix, ids);
		CustomQueue<?> createQueue = CustomQueueFactory.createQueue(queueName, -1);
		return (T)createQueue;
	}
	
	
	public <T extends CustomQueue<?>> T getQueue(String name) {
		CustomQueue<?> createQueue = CustomQueueFactory.createQueue(name, -1);
		return (T)createQueue;
	}
	
}
