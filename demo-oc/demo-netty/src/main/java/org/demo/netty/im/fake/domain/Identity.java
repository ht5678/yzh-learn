package org.demo.netty.im.fake.domain;


/**
 * 
 * @author yue
 *
 */
public enum Identity {

	SYS,//系统身份
	NULL,//空身份
	CUSTOMER,//客户身份
	WAITER;//客服身份
	
	
	private Identity(){ }
	
}
