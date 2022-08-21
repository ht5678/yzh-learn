package org.demo.dispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.socket.SocketChannel;

/**
 * 接入系统的网络连接管理组件
 * @author yue
 *
 */
public class GatewayManager {
	
//	//静态化写死接入系统的实例列表
//	public static final List<GatewayInstance> gatewayInstances = new ArrayList<>();
//	
//	//
//	static{
//		gatewayInstances.add(new GatewayInstance("localhost", "127.0.0.1", 8080));
//	}
	
	
	private GatewayManager(){
		
	}
	
	
	static class Singleton {
		private static GatewayManager instance = new GatewayManager();
	}
	
	
	/**
	 * 获取单例
	 * @return
	 */
	public static GatewayManager getInstance(){
		return Singleton.instance;
	}
	
	
	/*
	 * 储存接入系统的实例列表
	 */
	private ConcurrentHashMap<String, SocketChannel> gatewayInstances = new ConcurrentHashMap<>();
	
	
	/**
	 * 添加一个接入系统实例
	 * @param channelId	 	网络连接id
	 * @param channel			网络连接
	 */
	public void addGatewayInstance(String channelId , SocketChannel channel) {
		gatewayInstances.put(channelId, channel);
	}
	
	/**
	 * 
	 * @param channelId
	 */
	public void removeGatewayInstance(String channelId){
		gatewayInstances.remove(channelId);
	}

}
