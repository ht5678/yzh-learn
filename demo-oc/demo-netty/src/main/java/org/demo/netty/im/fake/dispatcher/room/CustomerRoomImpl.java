/**
 * 
 */
package org.demo.netty.im.fake.dispatcher.room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.demo.netty.im.fake.collection.CustomQueue;
import org.demo.netty.im.fake.domain.AddressFrom;
import org.demo.netty.im.fake.domain.AddressTo;
import org.demo.netty.im.fake.domain.Body;
import org.demo.netty.im.fake.domain.BodyType;
import org.demo.netty.im.fake.domain.Identity;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.PacketType;
import org.demo.netty.im.fake.domain.Waiter;
import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.session.Customer;
import org.demo.netty.im.fake.session.NameFactory;
import org.demo.netty.im.fake.store.waiting.CustomerWaitStoreManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 客户的彼岸
 * @author pengzq1
 * @createDate 2018年11月2日
 * @version v 1.0
 */
public final class CustomerRoomImpl implements CustomerRoom{
	
	private static Logger log = LoggerFactory.getLogger(CustomerRoomImpl.class); 
	
	private final static String CUSTOMER_WAIT_QUEUE = "CustomerWaitingQueue";
	private NameFactory nameFactory;
	
	private static Set<String> sets = new HashSet<>();
	
	public CustomerRoomImpl(NameFactory nameFactory) {
		this.nameFactory = nameFactory;
		Thread thread = new Thread(new PushCustomerMessage());
		thread.setName("Thread-PushWaitingMessage-01");
		thread.setDaemon(true);
		thread.start();
	}
	
	@Override
	public synchronized int addWait(Customer customer) {
		String teamCode = customer.getTeamCode();
		CustomQueue<Customer> customers = nameFactory.getQueue(CUSTOMER_WAIT_QUEUE, teamCode);
		int site = fixQueue(customers, customer);
		CustomerWaitStoreManager.getInst().enterQueue(customer, customers.size());
		return site;
	}
	
	@Override
	public synchronized void removeWait(Customer customer) {
		String teamCode = customer.getTeamCode();
		CustomQueue<Customer> customers = nameFactory.getQueue(CUSTOMER_WAIT_QUEUE, teamCode);
		if (customers != null && customers.size() > 0) {
			customers.remove(customer);
			// 更新客户编号
			addEvent(customers.getName());
			// 更新客服排队数
			broadcastWaiter(customer.getTeamCode(), customers.size());
			log.info("排队过程中，客户:{} 离开队列，进行移除!", customer.getUid());
		}
		CustomerWaitStoreManager.getInst().leaveQueue(customer, customers.size());
	}
	
	@Override
	public Customer acquire(String teamCode) {
		CustomQueue<Customer> customers = nameFactory.getQueue(CUSTOMER_WAIT_QUEUE, teamCode);
		Customer customer = customers.poll();
		if (customers.size() > 0) {
			addEvent(customers.getName());
		}
		while (null != customer) {
			Long enterTime = customer.getTime();
			Long waitTime = (System.currentTimeMillis() - enterTime) / 1000;
			// 如果排队时间超过2个小时，说明数据存在问题，直接过滤掉
			if (waitTime < 2 * 60 * 60) {
				broadcastWaiter(teamCode, customers.size());
				CustomerWaitStoreManager.getInst().leaveQueue(customer, customers.size());
				return customer;
			}
			customer = customers.poll();
		}
		return null;
	}
	
	@Override
	public List<Customer> getWaits(String teamCode) {
		CustomQueue<Customer> queue = nameFactory.getQueue(CUSTOMER_WAIT_QUEUE, teamCode);
		if (null != queue && queue.size() > 0) {
			return new ArrayList<>(queue);
		}
		return null;
	}
	
	@Override
	public int size(String teamCode) {
		CustomQueue<Customer> queue = nameFactory.getQueue(CUSTOMER_WAIT_QUEUE, teamCode);
		return queue.size();
	}
	
	private int fixQueue(Queue<Customer> queue, Customer customer) {
		queue.add(customer);
		broadcastWaiter(customer.getTeamCode(), queue.size());
		return queue.size();
	}
	
	private void broadcastWaiter(String teamCode, Integer queueCount) {
		Collection<Waiter> waiters = OCIMServer.getInst().getDispatcher().getWaiters(teamCode);
		Body body = new Body(BodyType.WAITING_NO, queueCount.toString());
		Packet packet = new Packet(PacketType.BROADCAST, body);
		AddressTo to = null;
		for (Waiter waiter : waiters) {
			to = new AddressTo(waiter.getWaiterCode(), waiter.getWaiterName(), Identity.WAITER);
			packet.setTo(to);
			if (!"4".equals(waiter.getStatus())) {
				OCIMServer.getInst().getRoutingTable().routePacket(packet);
			}
		}
	}
	
	private void addEvent(String queueName) {
		sets.add(queueName);
	}
	
	/**
	 * 更新推送客户排队编号
	 */
	private class PushCustomerMessage implements Runnable {

		@Override
		public void run() {
			List<String> removeSets = new LinkedList<>();
			while(true) {
				try {
					for (String queueName : sets) {
						removeSets.add(queueName);
						CustomQueue<Customer> queue = nameFactory.getQueue(queueName);
						pushWaitMessage(queue);
					}
					if (!removeSets.isEmpty()) {
						sets.removeAll(removeSets);
						removeSets.clear();
					}
					TimeUnit.SECONDS.sleep(5);
				} catch (Exception e) {
					sets.removeAll(removeSets);
					removeSets.clear();
					log.error("更新排队编号发生异常：{}", e.getMessage());
				}
			}
		}

		private void pushWaitMessage(CustomQueue<Customer> queue) {
			AddressFrom from = new AddressFrom(Identity.WAITER);
			int i = 0;
			for (Customer customer : queue) {
				Packet packet = new Packet(PacketType.BROADCAST);
				packet.setBody(new Body(BodyType.WAITING_NO, String.valueOf(++i)));
				packet.setFrom(from);
				packet.setTo(new AddressTo(customer.getUid(), customer.getName(), Identity.CUSTOMER));
				OCIMServer.getInst().getRoutingTable().routePacket(packet);
			}
		}
	}
}
