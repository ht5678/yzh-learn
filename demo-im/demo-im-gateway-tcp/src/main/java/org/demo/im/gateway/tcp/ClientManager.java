package org.demo.im.gateway.tcp;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.socket.SocketChannel;

/**
 * 管理netty长链接的组件
 * 
 * @author yue
 *
 */
public class ClientManager {
	
	/**
	 * 
	 */
	private ClientManager(){
		
	}
	
	/**
	 * 单例内部类 
	 */
	static class Singleton{
		public static ClientManager instance = new ClientManager();
	}
	
	
	/**
	 * 获取单例
	 * @return
	 */
	public static ClientManager getInstance() {
		return Singleton.instance;
	}
	
	
	/**
	 * 
	 * 存储uid到客户端连接的映射
	 */
	private ConcurrentHashMap<String, SocketChannel> uid2client = new ConcurrentHashMap<>();
	
	/**
	 * 存储channelId到uid的映射
	 */
	private ConcurrentHashMap<String, String> channelid2uid = new ConcurrentHashMap<>();
	

	/**
	 * 放入一个客户端连接到缓存中
	 * 添加一个建立好连接的客户端
	 */
	public void addChannel(String userId , SocketChannel socketChannel) {
		channelid2uid.put(socketChannel.remoteAddress().getHostName(), userId);
		uid2client.put(userId, socketChannel);
	}
	
	
	/**
	 * 判断认证过的客户端连接是否存在
	 * @return
	 */
//	public Boolean existChannel(String userId) {
//		return uid2client.containsKey(userId);
//	}
	public Boolean isCientConnection(String uid) {
		return uid2client.containsKey(uid);
	}
	
	
	
	/**
	 * 根据用户id获取对应的客户端连接
	 * @return
	 */
//	public SocketChannel getChannel(String userId) {
//		return uid2client.get(userId);
//	}
	public SocketChannel getClient(String uid) {
		return uid2client.get(uid);
	}
	
	
	
	/**
	 * 删除一个客户端的连接
	 */
	public void removeChannel(SocketChannel socketChannel) {
//		String userId = channelid2uid.get(socketChannel.remoteAddress().getHostName());
		String userId = channelid2uid.get(socketChannel.id().asLongText());
//		channelid2uid.remove(socketChannel.remoteAddress().getHostName());
		channelid2uid.remove(socketChannel.id().asLongText()); 
		uid2client.remove(userId);
	}
	
}
