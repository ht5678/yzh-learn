package org.demo.netty.im.chain;

import org.demo.netty.dispatcher.EventType;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.Waiter;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.register.Event;
import org.demo.netty.session.WaiterSession;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月6日 下午10:30:51
 */
public class ReceptionChain {

	
	
	/**
	 * 
	 */
	public static void directReception(WaiterSession session , Packet packet) {
		Waiter waiter = session.getWaiter();
		String tenantCode = waiter.getTenantCode();
		String teamCode = waiter.getTeamCode();
		String waiterCode = waiter.getWaiterCode();
		String waiterName = waiter.getWaiterName();
		Event event = new Event(EventType.WAITER_MANUAL_REQ, null , tenantCode, teamCode , waiterName , waiterCode);
		OCIMServer.getInst().getDispatcher().registerAllotEvent(event);
	}
	
}
