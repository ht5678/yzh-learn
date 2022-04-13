package org.demo.netty.im.fake.session;

import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月15日 下午5:07:31
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