package org.demo.netty.im.chain;

import org.demo.netty.domain.AddressFrom;
import org.demo.netty.domain.AddressTo;
import org.demo.netty.domain.Body;
import org.demo.netty.domain.BodyType;
import org.demo.netty.domain.Identity;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.PacketType;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.im.constants.Constants;
import org.demo.netty.session.CustomerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月29日 下午9:22:21
 */
public class CloseChatChain {

	private static Logger log = LoggerFactory.getLogger(CloseChatChain.class);
	
	
	/**
	 * 关闭客户
	 */
	public static void closeDispatcher(Packet packet) {
		OCIMServer.getInst().getRoutingTable().routeChatClose(packet);
	}
	
	
	/**
	 * 客户关闭会话 , 通知客服
	 */
	public static void closeChatToWaiter(CustomerSession customerSession , String content) {
		buildCloseChat(customerSession, content, PacketType.CLOSE_CHAT);
	}
	
	
	/**
	 * 发送客户会话重复 , 通知客服
	 */
	public static void closeRepeatChatToWaiter(CustomerSession customerSession) {
		AddressTo to = new AddressTo(Identity.SYS);
		Body body = new Body(BodyType.CUSTOMER_CLOSE , Constants.REPEAT_MESSAGE);
		Packet packet = new Packet(PacketType.RE_LOGIN, to , body);
		customerSession.sendPacket(packet);
	}
	
	
	
	/**
	 * 发送客户会话重复 , 通知客户
	 * @param customerSession
	 */
	public static void closeRepeatChatToCustomer(CustomerSession customerSession) {
		AddressTo to = new AddressTo(Identity.SYS);
		Body body = new Body(BodyType.CUSTOMER_CLOSE , Constants.REPEAT_MESSAGE);
		Packet packet = new Packet(PacketType.RE_LOGIN , to  , body);
		customerSession.sendPacket(packet);
	}
	
	
	
	/**
	 * 
	 */
	private static void buildCloseChat(CustomerSession customerSession , String content , PacketType packetType) {
		Body body = new Body(BodyType.CUSTOMER_CLOSE, content);
		AddressFrom from = new AddressFrom(customerSession.getUid() , customerSession.getIdy());
		AddressTo to = new AddressTo(customerSession.getWaiterCode(), customerSession.getWaiterName() , Identity.WAITER);
		Packet packet = new Packet(packetType , from , to , body);
		packet.setCid(customerSession.getCid());
		OCIMServer.getInst().getRoutingTable().routePacket(packet);
		log.info("客户关闭连天窗口 , packet : {}" ,  packet);
	}
	
}
