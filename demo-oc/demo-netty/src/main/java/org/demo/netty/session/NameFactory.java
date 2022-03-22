package org.demo.netty.session;

import org.demo.netty.queue.CustomQueueFactory;

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
