package org.demo.dispatcher;


/**
 * 接入系统的一个部署实例
 * @author yue
 *
 */
public class GatewayInstance {

	//接入系统部署机器的主机名
	private String hostname;
	//接入系统部署机器的地址
	private String ip;
	//接入系统部署机器对外建立监听的端口
	private int port;
	
	
	
	public GatewayInstance(String hostname, String ip, int port) {
		super();
		this.hostname = hostname;
		this.ip = ip;
		this.port = port;
	}
	
	
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	
	
	
}
