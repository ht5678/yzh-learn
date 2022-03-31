package org.demo.netty.routing;

import java.util.Collection;
import java.util.List;

import org.demo.netty.domain.AddressTo;
import org.demo.netty.domain.Body;
import org.demo.netty.domain.BodyType;
import org.demo.netty.domain.Identity;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.PacketType;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.im.chain.CloseChatChain;
import org.demo.netty.im.constants.Constants;
import org.demo.netty.session.CustomerAssignStatus;
import org.demo.netty.session.CustomerSession;
import org.demo.netty.session.WaiterSession;
import org.demo.netty.store.remote.packet.model.RemoteData;
import org.demo.netty.store.remote.packet.model.RemoteDateType;
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
					OCIMServer.getInst().getClusterMessageRouter().routeRemoveCustomerSession(existCustomerRoute.getNodeID(), existCustomerRoute);
				}
			}
			
			CustomerRoute customerRoute = new CustomerRoute(customerSession);
			customerRoutes.put(uid, customerRoute);
			customerSessionLocalRoutingTable.putSession(uid, customerSession);
		}
	}

	
	/**
	 * 
	 */
	@Override
	public void registerLocalWaiterSession(WaiterSession waiterSession) {
		String uid = waiterSession.getUid();
		synchronized (waiterSession.getUid()) {
			WaiterRoute existWaiterRoute = waiterRoutes.get(uid);
			//如果已经存在 , 则先清除再保存
			if(null != existWaiterRoute) {
				if(waiterRoutes.isLocal(existWaiterRoute)) {
					WaiterSession existWaiterSession = getLocalWaiterSession(uid);
					if(null != existWaiterSession) {
						closeRepeatWaiterClient(waiterSession);
						removeLocalWaiterSession(existWaiterSession);
						waiterSession.closeChannel();
					}
				}else {
					OCIMServer.getInst().getClusterMessageRouter().routeRemoveWaiterSessin(existWaiterRoute.getNodeID(), existWaiterRoute);
				}
			}
			
			//创建客服信息
			WaiterRoute waiterRoute = new WaiterRoute(waiterSession);
			waiterRoutes.put(uid, waiterRoute);
			waiterSessionLocalRoutingTable.putSession(uid, waiterSession);
		}
	}

	
	
	/**
	 * 
	 */
	@Override
	public void removeLocalWaiterSession(WaiterSession waiterSession) {
		String uid = waiterSession.getUid();
		WaiterRoute waiterRoute = waiterRoutes.get(uid);
		if(null != waiterRoute && waiterRoute.getVersion().equals(waiterSession.getVersion())) {
			waiterRoutes.remove(uid);
			waiterSessionLocalRoutingTable.remove(uid);
			OCIMServer.getInst().getDispatcher().logout(waiterSession);
		}
	}

	
	/**
	 * 
	 */
	@Override
	public void removeLocalCustomerSession(CustomerSession customerSession) {
		String uid = customerSession.getUid();
		CustomerRoute customerRoute = customerRoutes.get(uid);
		if(customerRoute != null && customerRoute.getVersion().equals(customerSession.getVersion())) {
			OCIMServer.getInst().getDispatcher().closeChat(customerSession);
			customerRoutes.remove(uid);
			customerSessionLocalRoutingTable.remove(uid);
			log.info("移除客户会话：{}", customerSession);
		}else {
			log.warn("客户会话已经被移除：{}", customerSession);
		}
	}

	
	/**
	 * 
	 */
	@Override
	public void removeRemoteCustomerSession(CustomerRoute customerRoute) {
		String uid = customerRoute.getUid();
		CustomerSession customerSession = getLocalCustomerSession(uid);
		if(null != customerSession && customerSession.getVersion().equals(customerRoute.getVersion())) {
			customerSessionLocalRoutingTable.remove(uid);
			closeRepeatCustomerChat(customerSession);
			customerSession.closeChannel();
		}
	}

	
	/**
	 * 
	 */
	@Override
	public void removeRemoteWaiterSession(WaiterRoute waiterRoute) {
		String uid = waiterRoute.getUid();
		WaiterSession waiterSession = getLocalWaiterSession(uid);
		if(null != waiterSession && waiterSession.getVersion().equals(waiterRoute.getVersion())) {
			closeRepeatWaiterClient(waiterSession);
			waiterSessionLocalRoutingTable.remove(uid);
		}
	}

	
	/**
	 * 
	 */
	@Override
	public void routePacket(Packet packet) {
		AddressTo to = packet.getTo();
		if(null == to) {
			log.error("请设置消息目的地 : {}", packet);
			return;
		}
		//
		switch (to.getIdy()) {
		case CUSTOMER:
			CustomerSession customerSession = customerSessionLocalRoutingTable.getSession(to.getUid());
			if(null != customerSession) {
				if(!isCustomerAssigned(customerSession)) {
					packet.getTo().setName(customerSession.getName());
					customerSession.sendPacket(packet);
					dealoff
				}
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void routeTransferByWaiter(TransferWaiter transferWaiter) {
		
	}

	@Override
	public void routeTransferByTeam(TransferTeam transferTeam) {
		
	}

	@Override
	public void routeChatClose(Packet packet) {
		
	}

	@Override
	public Collection<WaiterSession> getLocalWaiterSessions() {
		return null;
	}

	@Override
	public Collection<CustomerSession> getLocalCustomerSessions() {
		return null;
	}

	
	
	/**
	 * 处理客户不在线 , 留言信息
	 */
	private void dealOfflineWaiterPacket(Packet packet) {
		if(packet.getType() == PacketType.MESSAGE) {
			Body body = packet.getBody();
			if (null != body && body.getType() != null) {
				switch (packet.getBody().getType()) {
				case TIMEOUT_TIP:
				case TIMEOUT_CLOSE:
				case WAITER_CLOSE:
				case WAITER_GREAT:
				case BUILDING_CHAT:
					break;
				default:
					RemoteData remoteData = new RemoteData(RemoteDateType.OFFLINE, packet);
					packetstore
					break;
				}
			}
		}
	}
	
	
	
	/**
	 * 判断当前会话是否已经分配客服
	 * @return
	 */
	private boolean isCustomerAssigned(CustomerSession customerSession) {
		return customerSession.getStatus() == CustomerAssignStatus.ASSIGNED;
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
	
	
	/**
	 * 通知客服重复登录, 踢掉历史客户端
	 */
	private void closeRepeatWaiterClient(WaiterSession waiterSession) {
		AddressTo to = new AddressTo(Identity.SYS);
		Body body = new Body(BodyType.WAITER_CLOSE , Constants.REPEAT_MESSAGE);
		Packet packet = new Packet(PacketType.RE_LOGIN , to , body);
		waiterSession.sendPacket(packet);
	}
	
}
