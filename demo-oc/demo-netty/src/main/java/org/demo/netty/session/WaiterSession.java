package org.demo.netty.session;

import org.demo.netty.domain.Waiter;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月22日 下午5:00:53
 */
public interface WaiterSession extends Session{

	/**
	 * 获取客服信息
	 * @return
	 */
	Waiter getWaiter();
	
}
