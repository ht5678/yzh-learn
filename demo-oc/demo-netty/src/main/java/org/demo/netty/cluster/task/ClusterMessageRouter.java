package org.demo.netty.cluster.task;

import org.demo.netty.cluster.collection.cache.CacheFactory;
import org.demo.netty.cluster.task.hazelcast.PacketExecution;
import org.demo.netty.cluster.task.hazelcast.PacketExecution.RemotePacketType;
import org.demo.netty.cluster.task.hazelcast.RmCustomerSessionExecution;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.RemoteTaskResult;
import org.demo.netty.im.OCIMServer;
import org.demo.netty.node.NodeID;
import org.demo.netty.register.Event;
import org.demo.netty.routing.CustomerRoute;
import org.demo.netty.routing.RemoteMessageRouter;
import org.demo.netty.routing.WaiterRoute;
import org.demo.netty.transfer.TransferWaiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月30日 下午10:31:14
 */
public class ClusterMessageRouter implements RemoteMessageRouter{
	
	private static final Logger log = LoggerFactory.getLogger(ClusterMessageRouter.class);
	
	
	/**
	 * 
	 */
	public ClusterMessageRouter() {}
	
	
	
	/**
	 * 路由消息
	 */
	@Override
	public RemoteTaskResult routePacket(NodeID nodeID, Packet packet, RemotePacketType type) {
		try {
			PacketExecution task = new PacketExecution(packet, type);
			log.info("[分发消息]-任务 taskId:{} - body:{} - sourceNode:{} - targetNode: {}",
					task.getTaskId(), packet, nodeID,
					OCIMServer.getInst().getNodeID());
		}catch(IllegalStateException e) {
			log.warn("消息路由到远程节点出错: "+e);
		}
		return new RemoteTaskResult(106, "未知错误");
	}
	
	
	/**
	 * 路由移除客户
	 */
	@Override
	public RemoteTaskResult routeRemoveCustomerSession(NodeID nodeID, CustomerRoute customerRoute) {
		try {
			RmCustomerSessionExecution task = new RmCustomerSessionExecution(customerRoute);
			log.info("[移除集群中存在客户信息]-任务 taskId:{} - body:{} - sourceNode:{} - targetNode: {}",
					task.getTaskId(), customerRoute,
					nodeID.toString(),
					OCIMServer.getInst().getNodeID());
			
			return CacheFactory.doSynchronousClusterTask(task, nodeID.getBytes());
		}catch(IllegalStateException e) {
			log.warn("消息路由到远程节点出错: "+e);
		}
		return null;
	}

	
	/**
	 * 路由移除客服
	 */
	@Override
	public RemoteTaskResult routeRemoveWaiterSessin(NodeID nodeID, WaiterRoute waiterRoute) {
		
		return null;
	}

	@Override
	public RemoteTaskResult routeTransferByWaiter(NodeID nodeID, TransferWaiter transferWaiter) {
		return null;
	}

	@Override
	public RemoteTaskResult routeEvent(NodeID nodeID, Event event) {
		return null;
	}

	
	
}
