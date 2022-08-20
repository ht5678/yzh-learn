package org.demo.im.gateway.tcp;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.socket.SocketChannel;

/**
 * 管理netty长链接的组件
 * 
 * @author yue
 *
 */
public class NettyChannelManager {
	
	/**
	 * 
	 */
	private NettyChannelManager(){
		
	}
	
	/**
	 * 单例内部类 
	 */
	static class Singleton{
		public static NettyChannelManager instance = new NettyChannelManager();
	}
	
	
	/**
	 * 获取单例
	 * @return
	 */
	public static NettyChannelManager getInstance() {
		return Singleton.instance;
	}
	
	
	/**
	 * 存储所有客户端的连接
	 */
	private ConcurrentHashMap<String, SocketChannel> channels = new ConcurrentHashMap<>();
	
	/**
	 * 保存客户端的id
	 */
	private ConcurrentHashMap<String, String> channelIds = new ConcurrentHashMap<>();
	

	/**
	 * 放入一个客户端连接到缓存中
	 */
	public void addChannel(String userId , SocketChannel socketChannel) {
		channelIds.put(socketChannel.remoteAddress().getHostName(), userId);
		channels.put(userId, socketChannel);
	}
	
	
	/**
	 * 判断认证过的客户端连接是否存在
	 * @return
	 */
	public Boolean existChannel(String userId) {
		return channels.containsKey(userId);
	}
	
	
	/**
	 * 根据用户id获取对应的客户端连接
	 * @return
	 */
	public SocketChannel getChannel(String userId) {
		return channels.get(userId);
	}
	
	
	/**
	 * 删除一个客户端的连接
	 */
	public void removeChannel(SocketChannel socketChannel) {
		String userId = channelIds.get(socketChannel.remoteAddress().getHostName());
		channelIds.remove(socketChannel.remoteAddress().getHostName());
		channels.remove(userId);
	}
	
}
