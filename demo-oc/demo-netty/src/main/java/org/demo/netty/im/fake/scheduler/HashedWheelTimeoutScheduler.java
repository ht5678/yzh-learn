package org.demo.netty.im.fake.scheduler;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import io.netty.util.internal.PlatformDependent;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午6:13:09
 */
public class HashedWheelTimeoutScheduler implements CancelableScheduler{
	
	private final ConcurrentMap<SchedulerKey, Timeout> scheduledFutures = PlatformDependent.newConcurrentHashMap();
	private final HashedWheelTimer executorService;
	
	private volatile ChannelHandlerContext ctx;
	
	
	/**
	 * 
	 */
	public HashedWheelTimeoutScheduler() {
		executorService = new HashedWheelTimer();
	}
	
	
	/**
	 * 
	 * @param threadFactory
	 */
	public HashedWheelTimeoutScheduler(ThreadFactory threadFactory) {
		executorService = new HashedWheelTimer(threadFactory);
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public void update(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	/**
	 * 
	 */
	@Override
	public void cancel(SchedulerKey key) {
		if(null == key) {
			return;
		}
		Timeout timeout = scheduledFutures.remove(key);
		if(null != timeout) {
			timeout.cancel();
		}
	}

	/**
	 * 
	 */
	@Override
	public void schedulerCallback(SchedulerKey key, Runnable runnable, long delay, TimeUnit unit) {
		Timeout timeout = executorService.newTimeout(new TimerTask() {
			
			@Override
			public void run(Timeout timeout) throws Exception {
				try {
					runnable.run();
				}finally {
					scheduledFutures.remove(key);
				}
			}
		}, delay, unit);
		
		//
		replaceSchedulerFuture(key, timeout);
	}

	/**
	 * 
	 */
	@Override
	public void scheduler(Runnable runnable, long delay, TimeUnit unit) {
		executorService.newTimeout(new TimerTask() {
			
			@Override
			public void run(Timeout timeout) throws Exception {
				runnable.run();
			}
		}, delay, unit);
	}

	
	/**
	 * 
	 */
	@Override
	public void scheduler(SchedulerKey key, Runnable runnable, long delay, TimeUnit unit) {
		Timeout newTimeout = executorService.newTimeout(new TimerTask() {
			
			@Override
			public void run(Timeout timeout) throws Exception {
				try {
					runnable.run();
				}finally {
					scheduledFutures.remove(key);
				}
			}
		}, delay, unit);
		
		//
		replaceSchedulerFuture(key, newTimeout);
	}

	/**
	 * 
	 */
	@Override
	public void shutdown() {
		executorService.stop();
	}
	
	
	
	/**
	 * 
	 */
	private void replaceSchedulerFuture(final SchedulerKey key , final Timeout timeout) {
		final Timeout oldTimeout;
		
		if(timeout.isExpired()) {
			oldTimeout = scheduledFutures.remove(key);
		}else {
			oldTimeout = scheduledFutures.put(key, timeout);
		}
		
		if(null != oldTimeout) {
			oldTimeout.cancel();
		}
	}

	
	
}
