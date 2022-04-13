package org.demo.netty.im.fake.routing;

import java.util.Collection;
import java.util.List;

import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.session.CustomerSession;
import org.demo.netty.im.fake.session.WaiterSession;
import org.demo.netty.im.fake.transfer.TransferTeam;
import org.demo.netty.im.fake.transfer.TransferWaiter;



/**
 * 路由管理中心
 * @author yuezh2
 * @date	  2022年3月28日 下午4:49:06
 */
public interface RoutingTable {

	/**
	 * 获取缓存客户路由信息
	 * @param uid
	 * @return
	 */
	CustomerRoute getCustomerRoute(String uid);

	/**
	 * 获取缓存客户路由信息
	 * @return
	 */
	List<CustomerRoute> getCustomerRoutes();

	/**
	 * 移除缓存客服路由信息
	 * @param uid
	 * @return
	 */
	WaiterRoute removeWaiterRoute(String uid);

	/**
	 * 移除缓存客户路由信息
	 * @param uid
	 * @return
	 */
	CustomerRoute removeCustomerRoute(String uid);

	/**
	 * 获取本地客服session
	 * @param uid
	 * @return
	 */
	WaiterSession getLocalWaiterSession(String uid);

	/**
	 * 获取本地客户session
	 * @param uid
	 * @return
	 */
	CustomerSession getLocalCustomerSession(String uid);

	/**
	 * 注册客户local session
	 * @param session
	 */
	void registerLocalCustomerSession(CustomerSession session);

	/**
	 * 注册客服local session
	 * @param session
	 */
	void registerLocalWaiterSession(WaiterSession session);

	/**
	 * 移除本地客服session
	 * @param session
	 */
	void removeLocalWaiterSession(WaiterSession session);

	/**
	 * 移除本地客户session
	 * @param session
	 */
	void removeLocalCustomerSession(CustomerSession session);

	/**
	 * 移除缓存客户session
	 * @param route
	 */
	void removeRemoteCustomerSession(CustomerRoute route);

	/**
	 * 移除缓存客服session
	 * @param route
	 */
	void removeRemoteWaiterSession(WaiterRoute route);

	/**
	 * 路由消息
	 * @param packet
	 */
	void routePacket(Packet packet);

	/**
	 * 路由转接信息（按客服）
	 * @param transferWaiter
	 */
	void routeTransferByWaiter(TransferWaiter transferWaiter);

	/**
	 * 路由转接信息（按团队）
	 * @param transferTeam
	 */
	void routeTransferByTeam(TransferTeam transferTeam);

	/**
	 * 关闭会话
	 * @param packet
	 */
	void routeChatClose(Packet packet);

	/**
	 * 获取本地客服session
	 * @return
	 */
	Collection<WaiterSession> getLocalWaiterSessions();

	/**
	 * 获取本地客户session
	 * @return
	 */
	Collection<CustomerSession> getLocalCustomerSessions();
}
