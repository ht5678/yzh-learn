package org.demo.netty.im.fake.dispatcher.register;

import java.util.concurrent.TimeUnit;

import org.demo.netty.im.fake.collection.CustomQueue;
import org.demo.netty.im.fake.register.Event;
import org.demo.netty.im.fake.session.NameFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月11日 下午4:40:32
 */
public class EventRegister {

	private final Logger log = LoggerFactory.getLogger(EventRegister.class);
	
	private final static String REG_EVENT_QUEUE = "RegEventQueue";
	private static CustomQueue<Event> registerQueue;
	
	
	/**
	 * 
	 */
	public EventRegister(NameFactory nameFactory) {
		registerQueue = nameFactory.getQueue(REG_EVENT_QUEUE);
	}

	
	/**
	 * 
	 */
	public void register(Event event) {
		registerQueue.add(event);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasEvent(Event event) {
		return registerQueue.contains(event);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean cancel(Event event) {
		return registerQueue.remove(event);
	}
	
	/**
	 * 
	 * @return
	 */
	public Event acquireEvent() {
		Event event = registerQueue.poll();
		return event;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Event tryAcquireEvent(long timeout) {
		try {
			Event event = registerQueue.poll(timeout, TimeUnit.MILLISECONDS);
			return event;
		} catch (Exception e) {
			log.warn("获取事件被中断 : {}" , e);
		}
		return null;
	}
	
}
