package org.demo.netty.im.fake.service;

import java.util.List;

import org.demo.netty.im.fake.domain.ChatRecord;
import org.demo.netty.im.fake.domain.ChatRecordStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月2日 下午9:26:12
 */
@Service
public class ChatRecordServiceImpl implements ChatRecordService{
	
//	@Autowired
//	private ChatRecordMapper chatRecordMapper;

	@Override
	public List<ChatRecordStatistics> obtainChatRecordStatistics(String chatId) {
//		return chatRecordMapper.obtainChatRecordStatistics(chatId);
		return null;
	}

	@Override
	public int insert(ChatRecord record) {
//		return chatRecordMapper.insert(record);
		return 1;
	}

	@Override
	public int revocationChatRecord(ChatRecord chatRecord) {
//		return chatRecordMapper.revocationChatRecord(chatRecord);
		return 1;
	}
}
