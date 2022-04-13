package org.demo.netty.im.fake.queue;

import org.demo.netty.im.fake.session.CustomQueue;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月22日 下午2:36:05
 */
public interface CustomQueueFactoryStrategy {
	
	<E> CustomQueue<E> createOCQueue(String name,  int emptyQueueTtl);
	

}
