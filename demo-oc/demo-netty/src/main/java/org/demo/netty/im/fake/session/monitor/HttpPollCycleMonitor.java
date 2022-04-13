package org.demo.netty.im.fake.session.monitor;

import java.util.concurrent.TimeUnit;

import org.demo.netty.im.fake.scheduler.CancelableScheduler;
import org.demo.netty.im.fake.scheduler.HashedWheelTimeoutScheduler;
import org.demo.netty.im.fake.scheduler.SchedulerKey;
import org.demo.netty.im.fake.scheduler.SchedulerKey.SchedulerType;
import org.demo.netty.im.fake.session.CustomerSession;

/**
 * 会话声明周期监听
 * @author yuezh2
 * @date	  2022年3月29日 下午6:07:59
 */
public class HttpPollCycleMonitor {
	
	private final static long POLL_TIMEOUT_MILLISECOND = 72 * 1000;
	
	private final static CancelableScheduler scheduler = new HashedWheelTimeoutScheduler();
	private CustomerSession customerSession;
	private SchedulerKey pollTimeoutScheduler;
	
	
	/**
	 * 
	 */
	public HttpPollCycleMonitor(CustomerSession customerSession) {
		this.customerSession = customerSession;
		pollTimeoutScheduler = new SchedulerKey(SchedulerType.TIMEOUT_TIP, customerSession.getUid());
	}
	
	
	
	/**
	 * 
	 */
	public void reset() {
		scheduler.cancel(pollTimeoutScheduler);
		excPollTimeoutScheduler();
	}
	
	
	
	/**
	 * 
	 */
	private void excPollTimeoutScheduler() {
		scheduler.scheduler(pollTimeoutScheduler, () -> {
			customerSession.disconnect();
		}, POLL_TIMEOUT_MILLISECOND , TimeUnit.MILLISECONDS);
	}
	

}
