package org.demo.netty.im.fake.service;

import org.demo.netty.im.fake.domain.Chat;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月2日 下午9:23:03
 */
public interface ChatService {
	
    int insert(Chat record);

    int updateChatClose(Chat record);
}