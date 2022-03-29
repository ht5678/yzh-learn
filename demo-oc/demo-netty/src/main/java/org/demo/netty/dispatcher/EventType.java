package org.demo.netty.dispatcher;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午9:20:11
 */
public enum EventType {
	/**
	 * 访客请求
	 */
	CUSTOMER_REQ,
	/**
	 * 客服空闲
	 */
	WAITER_IDLE,
	/**
	 * 客服手动请求分配
	 */
	WAITER_MANUAL_REQ,
	/**
	 * 分配成功
	 */
	READY_DONE,
	/**
	 * 团队转接
	 */
	TRANSFER_TEAM;

	EventType() {}
}
