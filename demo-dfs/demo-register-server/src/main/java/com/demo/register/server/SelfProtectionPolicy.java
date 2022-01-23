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
	 * 是否需要开启自我保护机制
	 * @return
	 */
	public Boolean isEnable(){
		HeartbeatMessuredRate heartbeatMessuredRate = HeartbeatMessuredRate.getInstance();
		long latestMinuteHeartbeatRate = heartbeatMessuredRate.get();
		
		if(latestMinuteHeartbeatRate < this.expectedHeartbeatThreshold){
			System.out.println("自我保护机制开启 ,  最近一次心跳次数 = " + latestMinuteHeartbeatRate + " , 期望心跳次数 : "+expectedHeartbeatThreshold);
			return true;
		}
		System.out.println("自我保护机制没有开启 ,  最近一次心跳次数 = " + latestMinuteHeartbeatRate + " , 期望心跳次数 : "+expectedHeartbeatThreshold);
		return false;
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
