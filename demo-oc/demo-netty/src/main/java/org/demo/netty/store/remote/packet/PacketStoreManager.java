package org.demo.netty.store.remote.packet;

import java.util.ArrayList;
import java.util.List;

import org.demo.netty.domain.Body;
import org.demo.netty.domain.BodyType;
import org.demo.netty.domain.Chat;
import org.demo.netty.domain.ChatCollectHelper;
import org.demo.netty.domain.ChatRecord;
import org.demo.netty.domain.Identity;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.PacketType;
import org.demo.netty.domain.Waiter;
import org.demo.netty.provider.ChatProvider;
import org.demo.netty.session.WaiterSession;
import org.demo.netty.store.remote.packet.listener.KafkaPacketStoreListener;
import org.demo.netty.store.remote.packet.listener.LocalPacketStoreListener;
import org.demo.netty.store.remote.packet.listener.PacketStoreListener;
import org.demo.netty.store.remote.packet.model.RemoteData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.remote;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月2日 下午9:10:54
 */
public class PacketStoreManager {
	
	private static Logger log = LoggerFactory.getLogger(PacketStoreManager.class);
	
	//
	private static List<PacketStoreListener> listeners = new ArrayList<>();
	
	
	/**
	 * 
	 */
	private PacketStoreManager() {
		registerListener(new LocalPacketStoreListener());
		registerListener(new KafkaPacketStoreListener());
	}
	
	
	/**
	 * 注册消息监听
	 */
	public void registerListener(PacketStoreListener listener) {
		listeners.add(listener);
	}
	
	
	
	/**
	 * 
	 */
	public void addRemoteData(RemoteData remoteData) {
		if(null != remoteData) {
			switch (remoteData.getType()) {
			case NORMAL:
				remotePacket
				break;

			default:
				break;
			}
		}
	}
	
	
	
	
	/**
	 * 处理消息
	 */
	private void remotePacket(RemoteData remoteData) {
		Identity identity = remoteData.getIdentity();
		Packet packet = remoteData.getPacket();
		PacketType packetType = packet.getType();
		
		if(packetIsPutStore(packet)) {
			return;
		}
		
		//
		Body body = packet.getBody();
		BodyType bodyType = body.getType();
		
		//
		if(identity == Identity.WAITER) {
			try {
				switch (packetType) {
				case MESSAGE:
					dispatcherChatRecord(remoteData);
					break;
				case CLOSE_CHAT:
					dispatcherEndChat(remoteData);
					break;
				case TRANSFER:
					if(bodyType == BodyType.SUCCESS) {
						remoteData.setIdentity(Identity.NULL);
						dispatcherEndChat(remoteData);
					}
					break;
				default:
					break;
				}
			}catch(Exception e) {
				log.error("保存客服信息 : {}  , 发生异常 : {}" , remoteData , e);
			}
			
		}else if(identity == Identity.CUSTOMER) {
			try {
				switch (packetType) {
				case TRANSFER:
					
					break;

				default:
					break;
				}
			}catch(Exception e) {
				log.error("保存客户信息: {} , 发生异常: {}" , remoteData , e);
			}
		}
		
	}
	
	
	
	/**
	 * 
	 */
	private synchronized void dispatcherChatRecord(RemoteData remoteData) {
		ChatRecord chatRecord = new ChatRecord();
		Packet packet = remoteData.getPacket();
		Body body = packet.getBody();
		String messageType = transMessageType(body.getType());
		String ownerType;
		if(packet.getCid() == null || packet.getCid().length()==0) {
			log.warn("当前消息没有chat id , packet : {}" , packet);
			return;
		}
		
		if(remoteData.getIdentity() == Identity.CUSTOMER) {
			chatRecord.setMessageId(packet.getPid());
			chatRecord.setChatId(packet.getCid());
			ownerType = transOwnerType(body.getType(), "2");
			
			createChatRecord(packet, chatRecord);
			
			chatRecord.setOwnerType(ownerType);
			chatRecord.setMessageType(messageType);
			chatRecord.setOffline("0");
			chatRecord.setRevocation(1);
			chatRecord.setMessages(body.getContent());
			chatRecord.setCreateTime(packet.getDatetime());
		}else if(remoteData.getIdentity() == Identity.WAITER) {
			ownerType = transOwnerType(body.getType(), "1");
			//客服助手消息 , 忽略客服发送
			if("3".equals(ownerType)) {
				return;
			}
			
			WaiterSession session = remoteData.getWaiterSession();
			Waiter waiter = session.getWaiter();
			String tenantCode = waiter.getTenantCode();
			String teamCode = waiter.getTeamCode();
			
			chatRecord.setMessageId(packet.getPid());
			chatRecord.setChatId(packet.getCid());
			chatRecord.setTenantCode(tenantCode);
			chatRecord.setTeamCode(teamCode);
			chatRecord.setWaiterCode(packet.getTo().getUid());
			chatRecord.setWaiterName(packet.getTo().getName());
			chatRecord.setCustomerCode(packet.getFrom().getUid());
			chatRecord.setCustomerName(packet.getFrom().getName());
			chatRecord.setOwnerType(ownerType);
			chatRecord.setMessageType(messageType);
			chatRecord.setOffline("0");
			chatRecord.setRevocation(1);
			chatRecord.setMessages(body.getContent());
			chatRecord.setCreateTime(packet.getDatetime());
		}
		
		//同步消息到监听者
		for(PacketStoreListener remotePacketStoreListener : listeners) {
			remotePacketStoreListener.remoteChatRecord(chatRecord);
		}
	}
	
	
	
	/**
	 * 
	 */
	private void dispatcherEndChat(RemoteData remoteData) {
		Chat chat = new Chat();
		Packet packet = remoteData.getPacket();
		
		chat.setChatId(packet.getCid());
		
		if(remoteData.getIdentity() == Identity.CUSTOMER) {
			chat.setCloseType("2");
		}else if(remoteData.getIdentity() == Identity.WAITER) {
			chat.setCloseType("1");
		}else if(remoteData.getIdentity() == Identity.SYS) {
			chat.setCloseType("3");
		}else {
			chat.setCloseType("4");
		}
		
		//
		ChatCollectHelper helper = ChatProvider.getInstance().obtainChatRecordStatistics(packet.getCid());
		if(null != helper) {
			chat.setIsEffective(helper.isEffective());
			chat.setMsgTotal(helper.getTotalCount());
			chat.setWaiterMsgTotal(helper.getWaiterMsgCount());
			chat.setCustomerMsgTotal(helper.getCustomerMsgCount());
		}else {
			chat.setIsEffective("0");
			chat.setMsgTotal(0);
			chat.setWaiterMsgTotal(0);
			chat.setCustomerMsgTotal(0);
		}
		
		//同步消息到监听者
		for(PacketStoreListener remotePacketStoreListener : listeners) {
			remotePacketStoreListener.remoteEndChat(chat);
		}
	}
	
	
	
	/**
	 * 消息类型:
	 * 
	 * 1.普通消息
	 * 2.图片消息
	 * 3.提示即将关闭
	 * 4.提示并关闭
	 * 5.客服欢迎语
	 * 6.系统消息
	 * 7.客户关闭
	 * 8,客服关闭
	 * 9.团队欢迎语
	 * 10.商品信息
	 * 11.订单信息
	 * 12.物流信息
	 * 13.机器人信息
	 * 
	 * @return
	 */
	private String transMessageType(BodyType type) {
		String messageType ;
		switch (type) {
			case IMAGE:
				messageType = "2";
				break;
			case WAITER_GREET:
				messageType = "5";
				break;
			case TIMEOUT_TIP:
				messageType = "3";
				break;
			case TIMEOUT_CLOSE:
				messageType = "4";
				break;
			case CUSTOMER_CLOSE:
				messageType = "7";
				break;
			case WAITER_CLOSE:
				messageType = "8";
				break;
			case TEAM_GREET:
				messageType = "9";
				break;
			case GOODS:
				messageType = "10";
				break;
			case ORDER:
				messageType = "11";
				break;
			case LOGISTICS:
				messageType = "12";
				break;
			case ROBOT:
				messageType = "13";
				break;
			default:
				messageType = "1";
		}
		return messageType;
	}
	
	
	/**
	 * 触发者：
	 *  1. 客服
	 *  2. 客户
	 *  3. robot
	 * @param type 消息内容类型
	 * @param defaultValue 默认值
	 * @return 返回转换类型
	 */
	private String transOwnerType(BodyType type, String defaultValue) {
		switch (type) {
			case TEAM_GREET:
			case WAITER_GREET:
			case TIMEOUT_TIP:
			case TIMEOUT_CLOSE:
			case ROBOT:
				defaultValue = "3";
				break;
			default:
				break;
		}
		return defaultValue;
	}
	
	
	
	/**
	 * 
	 * @param packet
	 */
	private void createChatRecord(Packet packet , ChatRecord chatRecord) {
		chatRecord.setTenantCode(packet.getTtc());
		chatRecord.setTeamCode(packet.getTmc());
		chatRecord.setWaiterCode(packet.getFrom().getUid());
		chatRecord.setWaiterName(packet.getFrom().getName());
		chatRecord.setCustomerCode(packet.getTo().getUid());
		chatRecord.setCustomerName(packet.getTo().getName());
	}
	
	
	/**
	 * 是否需要持久化
	 * @return
	 */
	private boolean packetIsPutStore(Packet packet) {
		if(packet.getType() == PacketType.REVOCATION) {
			return false;
		}
		
		Body body = packet.getBody();
		if(null != body && null != body.getType()) {
			return body.getType() != BodyType.TIP;
		}
		return true;
	}
	

}
