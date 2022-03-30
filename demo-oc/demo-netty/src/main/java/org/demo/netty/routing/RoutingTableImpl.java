package org.demo.netty.routing;

import java.util.Collection;
import java.util.List;

import org.demo.netty.domain.Packet;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.im.chain.CloseChatChain;
import org.demo.netty.session.CustomerAssignStatus;
import org.demo.netty.session.CustomerSession;
import org.demo.netty.session.WaiterSession;
import org.demo.netty.transfer.TransferTeam;
import org.demo.netty.transfer.TransferWaiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月30日 下午3:56:32
 */
public class RoutingTableImpl implements RoutingTable{

	private static Logger log = LoggerFactory.getLogger(RoutingTableImpl.class);
	
	private final static String REMOTE_CUSTOMER_CACHES = "Remote Customer Caches";
	private final static String REMOTE_WAITER_CACHES = "Remote Waiter Caches";
	
	private ClientRouteManager<WaiterRoute> waiterRoutes;
	private ClientRouteManager<CustomerRoute> customerRoutes;
	
	private LocalRoutingTable<WaiterSession> waiterSessionLocalRoutingTable;
	private LocalRoutingTable<CustomerSession> customerSessionLocalRoutingTable;
	
	
	
	/**
	 * 
	 */
	public RoutingTableImpl() {
		waiterRoutes = new ClientRouteManager<>(REMOTE_WAITER_CACHES);
		customerRoutes = new ClientRouteManager<>(REMOTE_CUSTOMER_CACHES);
		
		waiterSessionLocalRoutingTable = new LocalRoutingTable<>();
		customerSessionLocalRoutingTable = new LocalRoutingTable<>();
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public CustomerRoute getCustomerRoute(String uid) {
		return customerRoutes.get(uid);
	}

	
	/**
	 * 
	 */
	@Override
	public List<CustomerRoute> getCustomerRoutes() {
		return customerRoutes.getRoutes();
	}

	/**
	 * 
	 */
	@Override
	public WaiterRoute removeWaiterRoute(String uid) {
		return waiterRoutes.remove(uid);
	}

	/**
	 * 
	 */
	@Override
	public CustomerRoute removeCustomerRoute(String uid) {
		return customerRoutes.remove(uid);
	}

	/**
	 * 
	 */
	@Override
	public WaiterSession getLocalWaiterSession(String uid) {
		return waiterSessionLocalRoutingTable.getSession(uid);
	}

	/**
	 * 
	 */
	@Override
	public CustomerSession getLocalCustomerSession(String uid) {
		return customerSessionLocalRoutingTable.getSession(uid);
	}

	
	/**
	 * 
	 */
	@Override
	public void registerLocalCustomerSession(CustomerSession customerSession) {
		String uid = customerSession.getUid();
		if(null == uid || uid.length() == 0) {
			throw new NullPointerException("访客uid不能为空");
		}
		//
		synchronized (customerSession.getUid()) {
			//判断是否已经存在session , 如果存在先关闭
			CustomerRoute existCustomerRoute = customerRoutes.get(uid);
			
			if(null != existCustomerRoute) {
				if(customerRoutes.isLocal(existCustomerRoute)) {
					CustomerSession existCustomerSession = getLocalCustomerSession(uid);
					if(null != existCustomerSession) {
						closeRepeatCustomerChat(existCustomerSession);
						removeLocalCustomerSession(existCustomerSession);
						existCustomerSession.closeChannel();
					}
				}else {
					OCIMServer.getInst().getclus
				}
			}
		}
	}

	@Override
	public void registerLocalWaiterSession(WaiterSession session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLocalWaiterSession(WaiterSession session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLocalCustomerSession(CustomerSession session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeRemoteCustomerSession(CustomerRoute route) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeRemoteWaiterSession(WaiterRoute route) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void routePacket(Packet packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void routeTransferByWaiter(TransferWaiter transferWaiter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void routeTransferByTeam(TransferTeam transferTeam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void routeChatClose(Packet packet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<WaiterSession> getLocalWaiterSessions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<CustomerSession> getLocalCustomerSessions() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	/**
	 * 通知客户和客服 , 客户重复轮训 , 关闭历史咨询 
	 */
	private void closeRepeatCustomerChat(CustomerSession customerSession) {
		if(customerSession.getStatus() == CustomerAssignStatus.ASSIGNED) {
			CloseChatChain.closeRepeatChatToWaiter(customerSession);
		}
		CloseChatChain.closeRepeatChatToCustomer(customerSession);
	}
	
	
}
