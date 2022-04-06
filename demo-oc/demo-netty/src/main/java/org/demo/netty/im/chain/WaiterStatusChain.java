package org.demo.netty.im.chain;

import org.demo.netty.domain.Packet;
import org.demo.netty.domain.Waiter;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.session.WaiterSession;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月6日 下午10:34:49
 */
public class WaiterStatusChain {

	
	
	/**
	 * 
	 */
	public static void changeStatus(WaiterSession session , Packet packet) {
		Waiter waiter = session.getWaiter();
		String teamCode = waiter.getTeamCode();
		String waiterCode = waiter.getWaiterCode();
		String status = packet.getBody().getContent();
		OCIMServer.getInst().getDispatcher().changeWaiterStatus(teamCode, waiterCode, status);
	}
	
}
