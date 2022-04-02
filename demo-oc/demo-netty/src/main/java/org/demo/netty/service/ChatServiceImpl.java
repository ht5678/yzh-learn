package org.demo.netty.service;

import org.demo.netty.domain.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月2日 下午9:21:50
 */
@Service
public class ChatServiceImpl implements ChatService{
	
//	@Autowired
//	private ChatMapper chatMapper;

	@Override
	public int insert(Chat record) {
//		return chatMapper.insert(record);
		return 1;
	}

	@Override
	public int updateChatClose(Chat record) {
//		return chatMapper.updateChat(record);
		return 1;
	}
}
