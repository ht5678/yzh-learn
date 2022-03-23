package org.demo.netty.im.cs.chain;

import org.demo.netty.domain.AddressFrom;
import org.demo.netty.domain.AddressTo;
import org.demo.netty.domain.Body;
import org.demo.netty.domain.Identity;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.Properties;
import org.demo.netty.session.WaiterSession;
import org.demo.netty.store.local.LocalPropertiesStore;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月22日 下午4:59:06
 */
public class PacketChain {

	
	/**
	 * 
	 * @param waiterSession
	 * @param packet
	 */
	public static void dispatcherWaiterPacket(WaiterSession waiterSession , Packet packet) {
		if(null == waiterSession) {
			return;
		}
		
		//处理客服发给客户报文 , 附加报文来源, 保证报文完整性
		AddressFrom from = packet.getFrom() == null ? new AddressFrom() : packet.getFrom();
		from.setUid(waiterSession.getWaiter().getWaiterCode());
		from.setIdy(Identity.WAITER);
		packet.setFrom(from);
		packet.setTtc(waiterSession.getWaiter().getTenantCode());
		packet.setTmc(waiterSession.getWaiter().getTeamCode());
		
		resolverWaiterPacketBody(waiterSession, packet);
	}
	
	
	/**
	 * 
	 * @param session
	 * @param packet
	 */
	private static void resolverWaiterPacketBody(WaiterSession session  , Packet packet) {
		Body body = packet.getBody();
		if(null == body || body.getType() == null) {
			return;
		}
		
		Properties properties;
		switch (body.getType()) {
		case TIMEOUT_TIP:
			properties = LocalPropertiesStore.getInst()
						.getProperties(session.getWaiter().getTenantCode());
			body.setContent(properties.getTimeoutTipMessage());
			timeout
			break;

		default:
			break;
		}
		
	}
	
	
	/**
	 * 
	 * @param packet
	 */
	private static void timeoutPacketToWaiter(Packet packet) {
		AddressTo to = packet.getTo();
		AddressFrom from = packet.getFrom();
		AddressTo reverseTo = new AddressTo(from.getUid(), from.getName(), from.getIdy());
		AddressFrom reverseFrom = new AddressFrom(to.getUid() , to.getName() , to.getIdy());
		packet.setTo(reverseTo);
		packet.setFrom(reverseFrom);
		
		
	}
	
	
}
