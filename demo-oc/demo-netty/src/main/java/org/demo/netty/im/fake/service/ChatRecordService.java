package org.demo.netty.im.fake.service;

import java.util.List;

import org.demo.netty.im.fake.domain.ChatRecord;
import org.demo.netty.im.fake.domain.ChatRecordStatistics;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月2日 下午9:23:47
 */
public interface ChatRecordService {

	/**
	 * 获取客服、客户、Robot消息数量
	 * @param chatId
	 * @return
	 */
	List<ChatRecordStatistics> obtainChatRecordStatistics(String chatId);

	/**
	 * 保存聊天记录
	 * @param record
	 * @return
	 */
	int insert(ChatRecord record);

	/**
	 * 撤回聊天记录
	 * @param chatRecord
	 * @return
	 */
	int revocationChatRecord(ChatRecord chatRecord);
}
