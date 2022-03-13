package org.demo.netty.domain;


/**
 * 
 * @author yue
 *
 */
public enum PacketType {

	PING,//心跳
	PONG,//心跳响应
	CLOSE,//关闭
	AUTH,//权限验证
	BIND,//认证绑定
	POLL,//长轮训
	MESSAGE,//消息
	CHANGE_STATUS,//变更状态
	TRANSFER,//转接
	BUILD_TRANSFER_CHAT,//已转接
	RECEPTION,//接待
	EVALUATE,//评价
	REVOCATION,//撤回
	BUILD_CHAT,//创建会话
	RE_LOGIN,//重新登录
	CHATTING,//洽谈中
	CLOSE_WAIT,//关闭会话
	BROADCAST,//广播
	NON_WORKING_TIME;//非工作时间
	
	
	private PacketType() { }
	
}
