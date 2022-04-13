package org.demo.netty.im.fake.service;

import org.demo.netty.im.fake.domain.Waiter;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月14日 下午4:30:14
 */
public interface WaiterService {
	/**
	 * 客服登录
	 * @param waiterName
	 * @param password
	 * @return
	 */
	Waiter login(String waiterName, String password);

	/**
	 * 根据id更新当前接待量
	 * @param id
	 * @param reception
	 * @return
	 */
	int updateCurReception(Long id, Integer reception);

	/**
	 * 根据工号更新当前接待量
	 * @param waiterCode
	 * @param reception
	 * @return
	 */
	int updateCurReceptionByCode(String waiterCode, Integer reception);

	/**
	 * 更新工作状态
	 * @param id
	 * @param status
	 * @return
	 */
	int updateStatus(Long id, String status);


	/**
	 * 根据code 获取客服信息
	 * @param waiterCode
	 * @return
	 */
    Waiter obtainWaiterByCode(String waiterCode);

	/**
	 * 客服登出
	 * @param id
	 * @return
	 */
    int waiterLogout(Long id);
}
