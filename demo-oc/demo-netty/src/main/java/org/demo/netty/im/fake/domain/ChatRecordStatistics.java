package org.demo.netty.im.fake.domain;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月2日 下午9:24:18
 */
public class ChatRecordStatistics {

	private String ownerType;
	
	private Integer msgCount = 0;

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public Integer getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(Integer msgCount) {
		this.msgCount = msgCount;
	}
}
