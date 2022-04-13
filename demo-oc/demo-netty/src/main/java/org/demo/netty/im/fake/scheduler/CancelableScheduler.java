package org.demo.netty.im.fake.scheduler;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午6:11:33
 */
public interface CancelableScheduler {

	void update(ChannelHandlerContext ctx);
	
	void cancel(SchedulerKey key);
	
	void schedulerCallback(SchedulerKey key, Runnable runnable, long delay, TimeUnit unit);
	
	void scheduler(Runnable runnable, long delay, TimeUnit unit);
	
	void scheduler(SchedulerKey key, Runnable runnable, long delay, TimeUnit unit);
	
	void shutdown();
}