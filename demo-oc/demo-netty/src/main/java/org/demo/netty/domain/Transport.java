package org.demo.netty.domain;


/**
 * 
 * @author yue
 *
 */
public enum Transport {

	WEBSOCKET,//websocket通讯
	POLLING,//长轮训
	SOCKET;//socket通讯
	
	Transport(){}
}
