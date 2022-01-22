package com.demo.register.server;

/**
 * 自我保护机制
 * @author yue
 *
 */
public class SelfProtectionPolicy {
	
	private static SelfProtectionPolicy instance = new SelfProtectionPolicy();

	/**
	 * 期望的心跳次数 , 如果有10个服务实例 , 这个数值就是 , 10 * 2 = 20
	 */
	private long expectedHeartbeatRate = 0L;
	
	/**
	 * 期望的心跳次数阈值 ,  10* 2 * 0.85 = 17 , 每分钟至少17次心跳 , 才不用进入保护机制
	 */
	private long expectedHeartbeatThreshold = 0L;
	
	
	/**
	 * 返回一个实例
	 * @return
	 */
	public static SelfProtectionPolicy getInstance(){
		return instance;
	}
	
	
	/**
	 * 
	 */
	public void increment() {
		
	}


	public long getExpectedHeartbeatRate() {
		return expectedHeartbeatRate;
	}


	public void setExpectedHeartbeatRate(long expectedHeartbeatRate) {
		this.expectedHeartbeatRate = expectedHeartbeatRate;
	}


	public long getExpectedHeartbeatThreshold() {
		return expectedHeartbeatThreshold;
	}


	public void setExpectedHeartbeatThreshold(long expectedHeartbeatThreshold) {
		this.expectedHeartbeatThreshold = expectedHeartbeatThreshold;
	}
	
	
	
	
}
