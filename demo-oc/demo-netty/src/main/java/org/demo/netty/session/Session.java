package org.demo.netty.session;

import org.demo.netty.domain.Identity;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.Transport;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.AttributeKey;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月15日 下午5:06:15
 */
public interface Session {
	
	AttributeKey<Session> CLIENT_SESSION = AttributeKey.valueOf("clientSession");

	/**
	 * 获取编码
	 * @return
	 */
	String getUid();

	/**
	 * 获取名称
	 * @return
	 */
	String getName();

	/**
	 * 获取会话版本
	 * @return
	 */
	String getVersion();

	/**
	 * 获取身份
	 * @return
	 */
	Identity getIdy();

	/**
	 * 绑定路由信息
	 */
	void bindRoute();

	/**
	 * 绑定通讯管道
	 * @param channel
	 */
	void bindChannel(Channel channel);

	/**
	 * 获取传输类型
	 * @return
	 */
	Transport getTransport();

	/**
	 * 消息本地缓存
	 * @return
	 */
	TransportStore transportStore();

	/**
	 * 缓存消息
	 * @param packet
	 */
	void cachePacket(Packet packet);

	/**
	 * 发送消息
	 * @param packet
	 * @return
	 */
	ChannelFuture sendPacket(Packet packet);

	/**
	 * 释放连接
	 */
	void disconnect();

	/**
	 * 关闭连接
	 */
	void closeChannel();
}
