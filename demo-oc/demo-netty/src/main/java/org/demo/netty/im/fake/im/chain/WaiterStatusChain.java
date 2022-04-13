package org.demo.netty.im.fake.im.chain;

import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.Waiter;
import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.session.WaiterSession;

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
