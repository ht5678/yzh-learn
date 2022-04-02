package org.demo.netty.provider;

import java.util.List;

import org.demo.netty.domain.Chat;
import org.demo.netty.domain.ChatCollectHelper;
import org.demo.netty.domain.ChatRecord;
import org.demo.netty.domain.ChatRecordStatistics;
import org.demo.netty.service.ChatRecordService;
import org.demo.netty.service.ChatRecordServiceImpl;
import org.demo.netty.service.ChatService;
import org.demo.netty.service.ChatServiceImpl;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月2日 下午9:19:17
 */
public class ChatProvider {

//	private ChatService chatService = SpringContext.getBean(ChatServiceImpl.class);
	private ChatService chatService = new ChatServiceImpl();
	
//	private ChatRecordService chatRecordService = SpringContext.getBean(ChatRecordServiceImpl.class);
	private ChatRecordService chatRecordService = new ChatRecordServiceImpl();
	
	
	/**
	 * 获取客服 , 客户 , Robot消息数量
	 * @return
	 */
	public ChatCollectHelper obtainChatRecordStatistics(String chatId) {
		List<ChatRecordStatistics> chatRecordStatistics = chatRecordService.obtainChatRecordStatistics(chatId);
		ChatCollectHelper chatCollectHelper = new ChatCollectHelper();
		if(null != chatRecordStatistics) {
			for(ChatRecordStatistics crs : chatRecordStatistics) {
				if("1".equals(crs.getOwnerType())) {
					chatCollectHelper.setCustomerMsgCount(crs.getMsgCount());
				}else if("2".equals(crs.getOwnerType())) {
					chatCollectHelper.setWaiterMsgCount(crs.getMsgCount());
				}else {
					chatCollectHelper.setRobotMsgCount(crs.getMsgCount());
				}
			}
		}
		return chatCollectHelper;
	}
	
	
	/**
	 * 创建会话
	 * @return
	 */
	public int insertChat(Chat chat) {
		return chatService.insert(chat);
	}
	
	
	/**
	 * 保存聊天记录
	 * @return
	 */
	public int insertChatRecord(ChatRecord record) {
		return chatRecordService.insert(record);
	}
	
	
	/**
	 * 聊天内容撤回
	 * @return
	 */
	public int revocationChatRecord(ChatRecord record) {
		return chatRecordService.revocationChatRecord(record);
	}
	
	/**
	 * 结束会话
	 * @return
	 */
	public int updateChatClose(Chat record) {
		return chatService.updateChatClose(record);
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public static ChatProvider getInstance() {
		return Single.instance;
	}
	
	
	/**
	 * 
	 * @author yuezh2
	 * @date	  2022年4月2日 下午9:34:16
	 */
	private static class Single {
		private static ChatProvider instance = new ChatProvider();
	}
	
}
