package org.demo.netty.domain;


/**
 * 
 * @author yue
 *
 */
public enum BodyType {

	LOGIN,//登录认证内容
	RESPONSE,//登录认证机制交互
	ABORT,//中断请求
	TEXT,//文本内容
	IMAGE,//图片地址内容
	ORDER,//订单信息
	GOODS,//商品信息
	LOGISTICS,//物流信息
	BUILDING_CHAT,//正在分配客服中
	TEAM_GREET,//团队欢迎语
	WAITER_GREET,//客服欢迎语
	TIMEOUT_TIP,//超时提示
	TIMEOUT_CLOSE,//超时关闭
	TRANSFER_WAITER,//按客服转接
	TRANSFER_TEAM,//按团队转接
	WAITTING_NO,//排队编号
	WAITING,//排队中
	WAITER_CLOSE,//客服关闭
	CUSTOMER_CLOSE,//客户关闭
	ROBOT,//机器人
	TIP,//提示信息, 不入库
	SUCCESS,//成功
	FAIL;//失败
	
	private BodyType(){}
	
}
