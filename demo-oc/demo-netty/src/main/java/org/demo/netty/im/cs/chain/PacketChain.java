package org.demo.netty.im.cs.chain;

import org.demo.netty.dispatcher.EventType;
import org.demo.netty.domain.AddressFrom;
import org.demo.netty.domain.AddressTo;
import org.demo.netty.domain.Body;
import org.demo.netty.domain.BodyType;
import org.demo.netty.domain.Identity;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.PacketType;
import org.demo.netty.domain.Properties;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.im.constants.Constants;
import org.demo.netty.register.Event;
import org.demo.netty.session.Customer;
import org.demo.netty.session.CustomerAssignStatus;
import org.demo.netty.session.CustomerSession;
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
				timeoutPacketToWaiter(packet);
				break;
			case TIMEOUT_CLOSE:
				properties = LocalPropertiesStore.getInst()
							.getProperties(session.getWaiter().getTeamCode());
				body.setContent(properties.getTimeoutCloseMessage());
				timeoutPacketToWaiter(packet);
				break;
			default:
				break;
			}
		
	}
	
	
	
	/**
	 * 分发客户消息包
	 * @param customerSession
	 * @param packet
	 * @throws IllegalAccessException
	 */
	public static void dispatcherCustomerPacket(CustomerSession customerSession, Packet packet) throws IllegalAccessException {
		if (null == customerSession) {
			return;
		}
		packet.setTtc(customerSession.getTenantCode());
		packet.setTmc(customerSession.getTeamCode());
		packet.setCid(customerSession.getCid());
		AddressFrom from = new AddressFrom(customerSession.getUid(), customerSession.getName(), customerSession.getIdy());
		packet.setFrom(from);

		switch (customerSession.getStatus()) {
			case UNASSIGNED:
				customerSession.setStatus(CustomerAssignStatus.ASSIGNING);
				customerSession.cachePacket(packet);
				registerAssignEvent(customerSession);
				break;
			case ASSIGNED:
				packet.getTo().setIdy(Identity.WAITER);
				packet.getTo().setUid(customerSession.getWaiterCode());
				packet.getTo().setName(customerSession.getWaiterName());
				OCIMServer.getInst().getRoutingTable().routePacket(packet);
				break;
			case ASSIGNING:
				customerSession.cachePacket(packet);
				break;
			default:
				throw new IllegalAccessException("错误的状态");
		}
	}
	
	
	
	/**
	 * 为客户注册分配客服事件，并通知客户正在分配客服
	 * @param session
	 */
	private static void registerAssignEvent(CustomerSession session) {
		Customer customer = session.getCustomer();
		String uid = customer.getUid();
		String teamCode = customer.getTeamCode();
		//注册分配事件
		Event event = new Event(EventType.CUSTOMER_REQ, uid, customer.getTenantCode(), teamCode);
		OCIMServer.getInst().getDispatcher().registerAllotEvent(event);
		//通知客户正在分配客服
		Body body = new Body(BodyType.BUILDING_CHAT, Constants.ASSIGNING_MESSAGE);
		Packet assignPacket = new Packet(PacketType.BUILD_CHAT, body);
		session.sendPacket(assignPacket);
	}
	
	
	/**
	 * 超时提示或者关闭
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
