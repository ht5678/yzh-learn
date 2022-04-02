package org.demo.netty.store.remote.packet.listener;

import org.demo.netty.domain.Chat;
import org.demo.netty.domain.ChatRecord;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月2日 下午9:12:27
 */
public interface PacketStoreListener {

	
	/**
	 * 保存聊天详情
	 * @param chatRecord
	 */
	void remoteChatRecord(ChatRecord chatRecord);
	
	
	/**
	 * 处理会话创建
	 * @param chat
	 */
	void remoteBuildChat(Chat chat);
	
	
	/**
	 * 处理会话结束
	 * @param chat
	 */
	void remoteEndChat(Chat chat);
	
}
