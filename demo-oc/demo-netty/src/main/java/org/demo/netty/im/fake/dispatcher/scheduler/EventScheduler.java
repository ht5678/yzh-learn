package org.demo.netty.im.fake.dispatcher.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.demo.netty.im.fake.dispatcher.Dispatcher;
import org.demo.netty.im.fake.dispatcher.EventType;
import org.demo.netty.im.fake.dispatcher.register.EventRegister;
import org.demo.netty.im.fake.domain.Waiter;
import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.register.Event;
import org.demo.netty.im.fake.routing.CustomerRoute;
import org.demo.netty.im.fake.session.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstanceNotActiveException;

/**
 * 调度中心
 * @author yuezh2
 * @date	  2022年4月11日 下午8:54:05
 */
public class EventScheduler {

	private static Logger log = LoggerFactory.getLogger(EventScheduler.class);
	
	private volatile static int threadCount = 1;
	
	private final Dispatcher dispatcher;
	private final EventRegister register;
	
	private ExecutorService es;
	private static int waitTime = 100;
	

	
	/**
	 * 
	 */
	public EventScheduler(final Dispatcher dispatcher , final EventRegister register) {
		this.dispatcher = dispatcher;
		this.register = register;
		createThreadPools();
	}
	
	
	
	/**
	 * 开启调度
	 */
	public void start() {
		Thread thread = new Thread(new EventThread());
		thread.setName("Scheduler-WC");
		thread.start();
	}
	
	
	
	/**
	 * 
	 */
	private void createThreadPools() {
		int defaultPoolSize = 5;
		int maxPoolSize = 10;
		
		es = new ThreadPoolExecutor(defaultPoolSize,
				maxPoolSize,
				30L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(),
				runnable -> {
					Thread thread = new Thread(runnable);
					thread.setName(createThreadName());
					return thread;
				});
	}
	
	
	/**
	 * 
	 * @return
	 */
	private synchronized String createThreadName() {
		return "Scheduler-Waiter-"+threadCount++;
	}
	
	
	
	/**
	 * 
	 * @author yuezh2
	 * @date	  2022年4月11日 下午9:03:07
	 */
	private class EventThread implements Runnable{

		@Override
		public void run() {
			while(true) {
				try {
					Event event = register.acquireEvent();
					if(null == event) {
						int waitTimeLimit = 500;
						if(waitTime > waitTimeLimit) {
							waitTime = waitTimeLimit;
						}else {
							int step = 100;
							waitTime += step;
						}
						TimeUnit.MILLISECONDS.sleep(waitTime);
					}else {
						waitTime = 100;
						es.execute(new EventExecutor(event));
					}
				} catch (HazelcastInstanceNotActiveException activeException){
					log.error("请关注当前集群已关闭，如非手动停止，请检测应用", activeException);
					return;
				}catch (Exception e) {
					e.printStackTrace();
					log.error("处理事件发生异常：{}", e);
				}
			}
		}
		
	}
	
	
	
	/**
	 * 
	 * @author yuezh2
	 * @date	  2022年4月11日 下午9:06:08
	 */
	private class EventExecutor implements Runnable {
		
		private Event event;
		private String tenantCode;
		private String teamCode;
		
		/**
		 * 
		 */
		public EventExecutor(Event event) {
			this.event = event;
			this.teamCode = event.getTeamCode();
			this.tenantCode = event.getTenantCode();
		}
		

		@Override
		public void run() {
			if(StringUtils.isEmpty(tenantCode) || StringUtils.isEmpty(teamCode)) {
				log.warn("事件未绑定具体任务 : {}" , event);
				return;
			}
			
			switch (event.getType()) {
			case WAITER_IDLE:
				while(dispatcher.existQueueWait(teamCode)) {
					Waiter waiter = dispatcher.acquireWaiter(teamCode);
					if(null != waiter) {
						Customer customer = dispatcher.acquireCustomer(teamCode);
						if(null != customer) {
							event = new Event(EventType.READY_DONE, customer.getUid(), tenantCode, teamCode , waiter.getWaiterName() , waiter.getWaiterCode());
							allot(event);
						}else {
							dispatcher.directReleaseRelation(waiter.getWaiterCode());
						}
					}else {
						break;
					}
				}
				break;
			case CUSTOMER_REQ:
			case TRANSFER_TEAM:
				allot(event);
				break;
			case WAITER_MANUAL_REQ:
				Customer customer = dispatcher.acquireCustomer(teamCode);
				while(customer != null) {
					event.setUid(customer.getUid());
					if(allot(event)) {
						break;
					}else {
						customer = dispatcher.acquireCustomer(teamCode);
					}
				}
				break;
			default:
				log.warn("分配事件类型不存在 : Event : {}" , event);
				break;
			}
		}
		
		
		/**
		 * 
		 * @return
		 */
		private boolean allot(Event event) {
			CustomerRoute customerRoute = OCIMServer.getInst().getRoutingTable().getCustomerRoute(event.getUid());
			if(null != customerRoute) {
				if(customerRoute.getNodeID().equals(OCIMServer.getInst().getNodeID())) {
					dispatcher.buildRelation(event);
				}else {
					OCIMServer.getInst().getClusterMessageRouter().routeEvent(customerRoute.getNodeID(), event);
				}
			}else {
				//分配过程中发现客户离开 , READY_DONE表示已经获取客服资源 , 需要释放客服资源
				if(event.getType() == EventType.READY_DONE) {
					dispatcher.directReleaseRelation(event.getWaiterCode());
				}
				log.info("客户已经离开 , 无需为当前客户分配客服 , Event : {} , nodeID : {}" , event , OCIMServer.getInst().getNodeID() );
				return false;
			}
			return true;
		}
		
	}
	
	
}
