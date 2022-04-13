package org.demo.netty.im.fake.service;

import org.demo.netty.im.fake.domain.WaiterLog;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午4:46:38
 */
public interface WaiterLogService {
	
	int insert(WaiterLog record);
	
}