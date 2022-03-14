package org.demo.netty.service;

import org.demo.netty.domain.WaiterLog;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午4:47:21
 */
public class WaiterLogServiceImpl implements WaiterLogService{

	
	/**
	 * 记录客服登录日志
	 * @param record
	 * @return
	 */
	@Override
	public int insert(WaiterLog record) {
		return 1;
	}

}