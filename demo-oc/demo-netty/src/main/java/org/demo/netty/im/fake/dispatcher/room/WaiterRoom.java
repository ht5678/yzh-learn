package org.demo.netty.im.fake.dispatcher.room;

import java.util.Collection;

import org.demo.netty.im.fake.domain.Waiter;
import org.demo.netty.im.fake.transfer.TransferWaiter;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月11日 下午5:07:07
 */
public interface WaiterRoom {

	
	/**
	 * 客服登录
	 * @param waiter
	 */
	void login(Waiter waiter);
	
	/**
	 * 客服登出
	 * @param teamCode
	 * @param waiterCode
	 */
	void logout(String teamCode , String waiterCode);

	/**
	 * 获取客服
	 * @return
	 */
	Waiter acquire(String teamCode);
	
	/**
	 * 获取客服 
	 * @return
	 */
	Waiter acquire(String teamCode , String waiterCode);
	
	/**
	 * 直接获取客服
	 * @param teamCode
	 * @param waiterCode
	 * @return
	 */
	Waiter directAcquire(String teamCode , String waiterCode);
	
	/**
	 * 按团队获取所有客服
	 * @return
	 */
	Collection<Waiter> getWaiters(String teamCode);
	
	/**
	 * 客服变更服务状态
	 * @param teamCode
	 * @param waiterCode
	 * @param status
	 * @return
	 */
	boolean changeStatus(String teamCode , String waiterCode , String status);
	
	
	/**
	 * 尝试按客服转接客户
	 * @return
	 */
	boolean tryTransferByWaiter(TransferWaiter transferWaiter);
	
	/**
	 * 释放客服资源
	 */
	void release(String teamCode , String waiterCode);
	
	/**
	 * 获取客服
	 * @return
	 */
	Waiter getWaiter(String teamCode , String waiterCode);
}

