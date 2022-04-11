package org.demo.netty.dispatcher;

import java.util.Collection;
import java.util.List;

import org.demo.netty.dispatcher.register.EventRegister;
import org.demo.netty.dispatcher.room.CustomerRoom;
import org.demo.netty.dispatcher.room.CustomerRoomImpl;
import org.demo.netty.dispatcher.room.WaiterRoom;
import org.demo.netty.dispatcher.room.WaiterRoomImpl;
import org.demo.netty.dispatcher.scheduler.EventScheduler;
import org.demo.netty.domain.Waiter;
import org.demo.netty.register.Event;
import org.demo.netty.session.Customer;
import org.demo.netty.session.CustomerSession;
import org.demo.netty.session.NameFactory;
import org.demo.netty.session.WaiterSession;
import org.demo.netty.transfer.TransferTeam;
import org.demo.netty.transfer.TransferWaiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 调度中心
 * @author yuezh2
 * @date	  2022年4月11日 下午4:32:04
 */
public class AllotDispatcher implements Dispatcher{

	private static Logger log = LoggerFactory.getLogger(AllotDispatcher.class);
	
	
	private NameFactory nameFactory;
	private EventRegister eventRegister;
	private WaiterRoom waiterRoom;
	private CustomerRoom customerTable;
	private EventScheduler eventScheduler;
	
	
	
	/**
	 * 
	 */
	public AllotDispatcher() {
		nameFactory = new NameFactory();
		eventRegister = new EventRegister(nameFactory);
		waiterRoom = new WaiterRoomImpl(nameFactory, eventRegister);
		customerTable = new CustomerRoomImpl(nameFactory);
		eventScheduler = new EventScheduler(this, eventRegister);
		eventScheduler.start();
	}
	
	
	
	
	@Override
	public void login(Waiter waiter) {
		waiterRoom.login(waiter);
	}

	@Override
	public void logout(WaiterSession session) {
		if(null != session) {
			waiterRoom.logout(session.getWaiter().getTeamCode(), session.getUid());
		}
	}

	@Override
	public boolean changeWaiterStatus(String teamCode, String waiterCode, String status) {
		return waiterRoom.changeStatus(teamCode, waiterCode, status);
	}

	@Override
	public Collection<Waiter> getWaiters(String teamCode) {
		return waiterRoom.getWaiters(teamCode);
	}

	@Override
	public Waiter acquireWaiter(String teamCode) {
		return waiterRoom.acquire(teamCode);
	}

	@Override
	public Waiter acquireWaiter(String teamCode, String waiterCode) {
		return waiterRoom.acquire(teamCode , waiterCode);
	}

	@Override
	public Customer acquireCustomer(String teamCode) {
		return customerTable.acquire(teamCode);
	}

	@Override
	public int addQueueWait(Customer customer) {
		dd
		return 0;
	}

	@Override
	public List<Customer> getQueueWaits(String teamCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeQueueWait(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existQueueWait(String code) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registerAllotEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasAllotEvent(Event event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancelAllotRegister(Event event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void buildRelation(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void directReleaseRelation(String waiterCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean transferByWaiter(TransferWaiter transferWaiter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean transferByTeam(TransferTeam transferTeam) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void closeChat(CustomerSession session) {
		// TODO Auto-generated method stub
		
	}

	
	
}
