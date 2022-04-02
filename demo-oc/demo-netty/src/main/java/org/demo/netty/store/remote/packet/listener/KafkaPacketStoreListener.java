package org.demo.netty.store.remote.packet.listener;

import java.io.IOException;

import org.apache.kafka.clients.KafkaClient;
import org.demo.netty.domain.Chat;
import org.demo.netty.domain.ChatRecord;
import org.demo.netty.provider.context.SpringContext;
import org.demo.netty.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月2日 下午9:36:25
 */
public class KafkaPacketStoreListener implements PacketStoreListener {
	
	private static Logger log = LoggerFactory.getLogger(KafkaPacketStoreListener.class);
	
	private static String END_CHAT_TOPIC = "ocim-chat-info";
	private static KafkaClient kafkaClient = SpringContext.getBean(KafkaClient.class);
	
	public KafkaPacketStoreListener() {
//		kafkaClient.start();
	}
	
	/**
	 * 会话记录
	 * @param chatRecord
	 */
	@Override
	public void remoteChatRecord(ChatRecord chatRecord) {
		//ignore
	}

	/**
	 * 创建会话
	 * @param chat
	 */
	@Override
	public void remoteBuildChat(Chat chat) {
		//ignore
	}

	/**
	 * 会话结束
	 * @param chat
	 */
	@Override
	public void remoteEndChat(Chat chat) {
//		try {
//			kafkaClient.sendMsg(END_CHAT_TOPIC, JsonUtils.getJson().writeString(chat));
//		} catch (IOException e) {
//			log.info("Object to json string error: {}", e.getMessage());
//		}
	}

}
