package org.demo.netty.dispatcher;

import java.util.Collection;
import java.util.List;

import org.demo.netty.domain.Waiter;
import org.demo.netty.register.Event;
import org.demo.netty.session.Customer;
import org.demo.netty.session.CustomerSession;
import org.demo.netty.session.WaiterSession;
import org.demo.netty.transfer.TransferTeam;
import org.demo.netty.transfer.TransferWaiter;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午9:19:07
 */
public interface Dispatcher {

	/**
	 * 客服登录
	 * @param waiter
	 */
	void login(Waiter waiter);

	/**
	 * 客服登出
	 * @param session
	 */
	void logout(WaiterSession session);

	/**
	 * 客服变更服务状态
	 * @param teamCode
	 * @param waiterCode
	 * @param status
	 * @return
	 */
	boolean changeWaiterStatus(String teamCode, String waiterCode, String status);

	/**
	 * 按团队获取客服列表
	 * @param teamCode
	 * @return
	 */
	Collection<Waiter> getWaiters(String teamCode);

	/**
	 * 获取客服
	 * @param teamCode
	 * @return
	 */
	Waiter acquireWaiter(String teamCode);

	/**
	 * 获取客服
	 * @param teamCode
	 * @param waiterCode
	 * @return
	 */
	Waiter acquireWaiter(String teamCode, String waiterCode);

	/**
	 * 获取客户
	 * @param code
	 * @return
	 */
	Customer acquireCustomer(String code);

	/**
	 * 客户加入等待队列
	 * @param customer
	 * @return
	 */
	int addQueueWait(Customer customer);

	/**
	 * 获取排队客户列表
	 * @param teamCode
	 * @return
	 */
	List<Customer> getQueueWaits(String teamCode);

	/**
	 * 客户移除排队队列
	 * @param customer
	 */
	void removeQueueWait(Customer customer);

	/**
	 * 是否存在排队客户
	 * @param code
	 * @return
	 */
	boolean existQueueWait(String code);

	/**
	 * 注册分配事件
	 * @param event
	 */
	void registerAllotEvent(Event event);

	/**
	 * 事件是否存在
	 * @param event
	 * @return
	 */
	boolean hasAllotEvent(Event event);

	/**
	 * 取消事件
	 * @param event
	 * @return
	 */
	boolean cancelAllotRegister(Event event);

	/**
	 * 构建客服、客户服务关系
	 * @param event
	 */
	void buildRelation(Event event);

	/**
	 * 直接释放客服、客户关系
	 * @param waiterCode
	 */
	void directReleaseRelation(String waiterCode);

	/**
	 * 按客服转接
	 * @param transferWaiter
	 * @return
	 */
	boolean transferByWaiter(TransferWaiter transferWaiter);

	/**
	 * 按团队转接
	 * @param transferTeam
	 * @return
	 */
	boolean transferByTeam(TransferTeam transferTeam);

	/**
	 * 关闭会话
	 * @param session
	 */
	void closeChat(CustomerSession session);
}