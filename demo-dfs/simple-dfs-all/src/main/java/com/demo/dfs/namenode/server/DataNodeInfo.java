package com.demo.dfs.namenode.server;


/**
 * 用来描述datanode信息
 * @author yuezh2@lenovo.com
 *	@date 2021年12月21日下午9:45:29
 */
public class DataNodeInfo {

	private String ip;
	
	private String hostname;

	private Long latestHeartbeatTime;
	
	
	public DataNodeInfo(String ip, String hostname) {
		super();
		this.ip = ip;
		this.hostname = hostname;
	}

	
	
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Long getLatestHeartbeatTime() {
		return latestHeartbeatTime;
	}

	public void setLatestHeartbeatTime(Long latestHeartbeatTime) {
		this.latestHeartbeatTime = latestHeartbeatTime;
	}


	
}
