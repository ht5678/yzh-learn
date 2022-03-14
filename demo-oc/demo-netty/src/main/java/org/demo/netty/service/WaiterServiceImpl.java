package org.demo.netty.service;

import org.demo.netty.domain.Waiter;

/**
 * 客服操作
 * @author yuezh2
 * @date	  2022年3月14日 下午4:30:22
 */
public class WaiterServiceImpl implements WaiterService {


	@Override
	public Waiter login(String waiterName, String password) {
		return null;
	}

	@Override
	public int updateCurReception(Long id, Integer reception) {
		return 1;
	}

	@Override
	public int updateCurReceptionByCode(String waiterCode, Integer reception) {
		return 1;
	}

	@Override
	public int updateStatus(Long id, String status) {
		return 1;
	}

	@Override
	public Waiter obtainWaiterByCode(String waiterCode) {
		return null;
	}

	@Override
	public int waiterLogout(Long id) {
		return 1;
	}
}
