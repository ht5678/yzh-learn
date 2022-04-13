package org.demo.netty.im.fake.domain;


/**
 * 统计消息数量
 * @author yuezh2
 * @date	  2022年4月2日 下午9:27:29
 */
public class ChatCollectHelper {

	private int waiterMsgCount = 0;
	
	private int customerMsgCount = 0;

	private int robotMsgCount = 0;

	public ChatCollectHelper() {}

	public ChatCollectHelper(int waiterMsgCount, int customerMsgCount) {
		this.waiterMsgCount = waiterMsgCount;
		this.customerMsgCount = customerMsgCount;
	}

	public int getWaiterMsgCount() {
		return waiterMsgCount;
	}

	public void setWaiterMsgCount(int waiterMsgCount) {
		this.waiterMsgCount = waiterMsgCount;
	}

	public int getCustomerMsgCount() {
		return customerMsgCount;
	}

	public void setCustomerMsgCount(int customerMsgCount) {
		this.customerMsgCount = customerMsgCount;
	}

	public int getRobotMsgCount() {
		return robotMsgCount;
	}

	public void setRobotMsgCount(int robotMsgCount) {
		this.robotMsgCount = robotMsgCount;
	}

	public String isEffective() {
		if (waiterMsgCount > 0) {
			return "1";
		} else {
			return "0";
		}
	}

	public int getTotalCount() {
		return customerMsgCount + waiterMsgCount;
	}
}
