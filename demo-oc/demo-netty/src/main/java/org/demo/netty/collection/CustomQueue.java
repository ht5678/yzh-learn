package org.demo.netty.collection;

import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月15日 下午5:21:57
 * @param <E>
 */
public interface CustomQueue<E> extends BlockingQueue<E>{

	String getName();
	
	void setName(String name);
	
	long getQueueSize();
	
	void lock();
	
	void tryLock(long timeout) throws InterruptedException;
	
	void unlock();
	
}