package org.demo.dispatcher;

import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * 初始化组件
	 */
	public void init() {
		//我们到底是让接入系统主动跟分发系统建立连接?
		//还是让分发系统主动跟接入系统建立连接?
		//按照层和层的关系来说 , 应该是接入系统主动分发系统去建立连接
		//应该是让分发系统使用的是netty服务端的代码,监听一个端口 , 等待人家跟他建立连接
		//但凡建立连接之后 , 可以吧接入系统的长链接缓存在这个组件里
		
		
	}

}
