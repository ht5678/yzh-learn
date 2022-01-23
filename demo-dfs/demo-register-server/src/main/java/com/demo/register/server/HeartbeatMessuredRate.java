package com.demo.register.server;


/**
 * 心跳测量计数器
 * @author yue
 *
 */
public class HeartbeatMessuredRate {
	
	/*
	 * 单例实例
	 */
	private static HeartbeatMessuredRate instance = new HeartbeatMessuredRate();
	
	/*
	 * 最近一次心跳次数
	 */
	private long latestMinuteHeartbeatRate = 0L;
	
	/*
	 * 最近一分钟代表的时间戳
	 */
	private long latestMinuteTimestamp = System.currentTimeMillis();
	
	
	private HeartbeatMessuredRate(){
		Daemon daemon = new Daemon();
		daemon.setDaemon(true);
		daemon.start();
	}
	
	
	
	/**
	 * 获取单例实例
	 * @return
	 */
	public static HeartbeatMessuredRate getInstance(){
		return instance;
	}
	
	
	/**
	 * 增加一次最近一分钟的心跳次数
	 */
	public synchronized void increment(){
//		long currentTime = System.currentTimeMillis();
//		
//		if(currentTime - latestMinuteTimestamp >60 * 1000L){
//			latestMinuteHeartbeatRate = 0L;
//			this.latestMinuteTimestamp = System.currentTimeMillis();
//		}
		
		synchronized (HeartbeatMessuredRate.class) {
			latestMinuteHeartbeatRate++;
		}
		
	}
	
	
	/**
	 * 获取最近1分钟心跳次数
	 */
	public synchronized long get(){
		return latestMinuteHeartbeatRate;
	}
	
	
	
	private class Daemon extends Thread{
		
		@Override
		public void run() {
			try{
				synchronized (HeartbeatMessuredRate.class) {
					
					long currentTime = System.currentTimeMillis();
					
					if(currentTime - latestMinuteTimestamp >60 * 1000L){
						latestMinuteHeartbeatRate = 0L;
						latestMinuteTimestamp = System.currentTimeMillis();
					}
				}
				
				Thread.sleep(1000);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
}
