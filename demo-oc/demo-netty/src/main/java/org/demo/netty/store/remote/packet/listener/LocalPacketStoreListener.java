package org.demo.netty.store.remote.packet.listener;

import org.demo.netty.domain.Chat;
import org.demo.netty.domain.ChatRecord;
import org.demo.netty.provider.ChatProvider;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月2日 下午9:17:37
 */
public class LocalPacketStoreListener implements PacketStoreListener{

	
	/**
	 * 会话记录
	 */
	@Override
	public void remoteChatRecord(ChatRecord chatRecord) {
		ChatProvider.getInstance().insertChatRecord(chatRecord);
	}

	
	/**
	 * 创建会话
	 */
	@Override
	public void remoteBuildChat(Chat chat) {
		ChatProvider.getInstance().insertChat(chat);
	}

	
	/**
	 * 结束会话
	 */
	@Override
	public void remoteEndChat(Chat chat) {
		ChatProvider.getInstance().updateChatClose(chat);
	}

	
	
}
