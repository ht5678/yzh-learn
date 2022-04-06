package org.demo.netty.routing;

import java.util.Collection;
import java.util.List;

import org.demo.netty.cluster.task.hazelcast.PacketExecution;
import org.demo.netty.domain.AddressFrom;
import org.demo.netty.domain.AddressTo;
import org.demo.netty.domain.Body;
import org.demo.netty.domain.BodyType;
import org.demo.netty.domain.Identity;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.PacketType;
import org.demo.netty.domain.RemoteTaskResult;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.im.chain.CloseChatChain;
import org.demo.netty.im.constants.Constants;
import org.demo.netty.session.CustomerAssignStatus;
import org.demo.netty.session.CustomerSession;
import org.demo.netty.session.WaiterSession;
import org.demo.netty.store.remote.packet.PacketStoreManager;
import org.demo.netty.store.remote.packet.model.RemoteData;
import org.demo.netty.store.remote.packet.model.RemoteDataType;
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
					dealOfflineWaiterPacket(packet);
					return;
				}
				boolean valid = validateCustomerChatEffective(customerSession, packet);
				//
				if(valid) {
					packet.setCid(customerSession.getCid());
					packet.getTo().setName(customerSession.getName());
					customerSession.sendPacket(packet);
					resolverToCustomerBody(customerSession, packet.getBody());
					RemoteData remoteData = new RemoteData(RemoteDataType.NORMAL, packet , customerSession);
					PacketStoreManager.getInst().addRemoteData(remoteData);
				}
			}else {
				CustomerRoute customerRoute = customerRoutes.get(to.getUid());
				if(null != customerRoute && !isLocalRoute(customerRoute)) {
					RemoteTaskResult remoteTaskResult = OCIMServer.getInst().getClusterMessageRouter().routePacket(customerRoute.getNodeID(), packet, PacketExecution.RemotePacketType.CHAT_CLOSE);
					if(remoteTaskResult.getCode() != 100) {
						dealOfflineWaiterPacket(packet);
						log.warn("无法路由客户消息  code : {} - msg : {}" , remoteTaskResult.getCode() , remoteTaskResult.getMsg());
					}
				}else {
					dealOfflineWaiterPacket(packet);
				}
			}
			break;
		case WAITER:
			WaiterSession waiterSession = waiterSessionLocalRoutingTable.getSession(to.getUid());
			if(null != waiterSession) {
				waiterSession.sendPacket(packet);
				RemoteData remoteData = new RemoteData(RemoteDataType.NORMAL, packet , waiterSession);
				PacketStoreManager.getInst().addRemoteData(remoteData);
			}else {
				WaiterRoute waiterRoute = waiterRoutes.get(to.getUid());
				if(null != waiterRoute && !isLocalRoute(waiterRoute)) {
					RemoteTaskResult remoteTaskResult = OCIMServer.getInst().getClusterMessageRouter().routePacket(waiterRoute.getNodeID(), packet, PacketExecution.RemotePacketType.NORMAL);
					if(remoteTaskResult.getCode() != 100) {
						unFoundWaiterToCloseChat(packet);
						log.warn("无法路由客服消息  code: {} - msg: {}", remoteTaskResult.getCode(), remoteTaskResult.getMsg());
					}
				}else {
					unFoundWaiterToCloseChat(packet);
					log.warn("客服列表中没有检测到当前客服 , 关闭当前会话");
				}
			}
			break;
		case SYS:
			//ignore
			break;

		default:
			log.error("无法路由此类型消息 packet : {}" , packet);
			break;
		}
	}

	
	/**
	 * 
	 */
	@Override
	public void routeTransferByWaiter(TransferWaiter transferWaiter) {
		
	}

	/**
	 * 
	 */
	@Override
	public void routeTransferByTeam(TransferTeam transferTeam) {
		OCIMServer.getInst().getDispatcher().transferByTeam(transferTeam);
	}

	/**
	 * 
	 */
	@Override
	public void routeChatClose(Packet packet) {
		AddressTo to = packet.getTo();
		if(to.getIdy() == Identity.CUSTOMER) {
			CustomerSession customerSession = customerSessionLocalRoutingTable.getSession(to.getUid());
			if(null != customerSession) {
				if(customerSession.getStatus() == CustomerAssignStatus.ASSIGNED  && customerSession.getWaiterCode().equals(packet.getFrom().getUid())) {
					packet.setCid(customerSession.getCid());
					customerSession.sendPacket(packet);
					OCIMServer.getInst().getDispatcher().closeChat(customerSession);
					//存储消息
					RemoteData remoteData = new RemoteData(RemoteDataType.NORMAL, packet , customerSession);
					PacketStoreManager.getInst().addRemoteData(remoteData);
				}
			}else {
				CustomerRoute customerRoute = customerRoutes.get(to.getUid());
				if(null != customerRoute && !isLocalRoute(customerRoute)) {
					RemoteTaskResult remoteTaskResult = OCIMServer.getInst().getClusterMessageRouter().routePacket(customerRoute.getNodeID(), packet, PacketExecution.RemotePacketType.CHAT_CLOSE);
					if(remoteTaskResult.getCode() == 101) {
						log.info("客户已经离开，无需再次关闭");
					}
				}else {
					log.info("客户已经离开，无需再次关闭");
				}
			}
		}
	}

	
	
	/**
	 * 
	 */
	@Override
	public Collection<WaiterSession> getLocalWaiterSessions() {
		return waiterSessionLocalRoutingTable.getRoutes();
	}

	
	
	/**
	 * 
	 */
	@Override
	public Collection<CustomerSession> getLocalCustomerSessions() {
		return customerSessionLocalRoutingTable.getRoutes();
	}
	
	
	
	/**
	 * 判断是否是同一个客服, 如果不是一个客服, 则通知后续客服客户正在咨询中
	 * @param customerSession 客户信息
	 * @param packet 消息
	 * @return 效验消息 true通过 false未通过
	 */
	private boolean validateCustomerChatEffective(CustomerSession customerSession , Packet packet) {
		PacketType type = packet.getType();
		AddressFrom from = packet.getFrom();
		//消息类型不能为空
		if(null == type) {
			return false;
		}
		//广播消息直接校验通过
		if(type == PacketType.BROADCAST) {
			return true;
		}
		//校验发送者不能为空
		if(null == from) {
			return false;
		}
		//
		if(type == PacketType.MESSAGE && !customerSession.getWaiterCode().equals(from.getUid())) {
			Body body = new Body(BodyType.FAIL , "客户正在和客服工号 : "+customerSession.getWaiterCode()+" 咨询中 , 请稍后再试!");
			AddressTo to = new AddressTo(packet.getFrom().getUid() , Identity.WAITER);
			from = new AddressFrom(customerSession.getUid() , customerSession.getName() , customerSession.getIdy());
			packet = new Packet(PacketType.CHATTING, from , to , body);
			OCIMServer.getInst().getRoutingTable().routePacket(packet);
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * 解析客户消息类型
	 */
	private void resolverToCustomerBody(CustomerSession customerSession , Body body) {
		if(null == body || null == body.getType()) {
			return;
		}
		
		if(body.getType() == BodyType.TIMEOUT_CLOSE) {
			timeoutClose(customerSession);
		}
	}
	
	
	/**
	 * 判断路由是否在本地
	 * @return
	 */
	private boolean isLocalRoute(Route route) {
		return route.getNodeID().equals(OCIMServer.getInst().getNodeID());
	}

	
	
	/**
	 * 未找到客服 , 关闭当前会话
	 */
	private void unFoundWaiterToCloseChat(Packet packet) {
		Packet closePacket = new Packet(PacketType.CLOSE_CHAT);
		AddressFrom from = new AddressFrom(packet.getTo().getUid() , Identity.WAITER);
		AddressTo to = new AddressTo(packet.getFrom().getUid() , packet.getFrom().getName() , Identity.CUSTOMER);
		closePacket.setTo(to);
		closePacket.setFrom(from);
		closePacket.setBody(new Body(BodyType.FAIL , "客服状态有误"));
		routeChatClose(closePacket);
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
				case WAITER_GREET:
				case BUILDING_CHAT:
					break;
				default:
					RemoteData remoteData = new RemoteData(RemoteDataType.OFFLINE, packet);
					PacketStoreManager.getInst().addRemoteData(remoteData);
					break;
				}
				log.warn("客户列表中没有找到客户 , 判定已经离开 , 离线留言 . packet : {}" , packet);
			}else {
				log.info("不需要处理的留言信息 , packet : {}" , packet);
			}
		}else if(packet.getType() == PacketType.REVOCATION) {
			RemoteData remoteData = new RemoteData(RemoteDataType.REVOCATION, packet);
			PacketStoreManager.getInst().addRemoteData(remoteData);
		}else {
			log.info("不需要处理的留言信息 : packet : {}" , packet);
		}
	}
	
	
	
	/**
	 * 处理超时关闭
	 */
	private void timeoutClose(CustomerSession customerSession) {
		OCIMServer.getInst().getDispatcher().closeChat(customerSession);
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
