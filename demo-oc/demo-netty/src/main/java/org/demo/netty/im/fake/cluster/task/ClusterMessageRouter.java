package org.demo.netty.im.fake.cluster.task;

import org.demo.netty.im.fake.cluster.collection.cache.CacheFactory;
import org.demo.netty.im.fake.cluster.task.hazelcast.EventExecution;
import org.demo.netty.im.fake.cluster.task.hazelcast.PacketExecution;
import org.demo.netty.im.fake.cluster.task.hazelcast.RmCustomerSessionExecution;
import org.demo.netty.im.fake.cluster.task.hazelcast.RmWaiterSessionExecution;
import org.demo.netty.im.fake.cluster.task.hazelcast.TransferExecution;
import org.demo.netty.im.fake.cluster.task.hazelcast.PacketExecution.RemotePacketType;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.RemoteTaskResult;
import org.demo.netty.im.fake.im.OCIMServer;
import org.demo.netty.im.fake.node.NodeID;
import org.demo.netty.im.fake.register.Event;
import org.demo.netty.im.fake.routing.CustomerRoute;
import org.demo.netty.im.fake.routing.RemoteMessageRouter;
import org.demo.netty.im.fake.routing.WaiterRoute;
import org.demo.netty.im.fake.transfer.TransferWaiter;
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
		return new RemoteTaskResult(106, "未知错误");
	}

	
	/**
	 * 路由移除客服
	 */
	@Override
	public RemoteTaskResult routeRemoveWaiterSessin(NodeID nodeID, WaiterRoute waiterRoute) {
		try {
			RmWaiterSessionExecution task = new RmWaiterSessionExecution(waiterRoute);
			log.info("[移除集群中存在的客服信息]-任务 taskId:{} - body:{} - sourceNode:{} - targetNode: {}",
					task.getTaskId(), waiterRoute,
					nodeID.toString(),
					OCIMServer.getInst().getNodeID());
			
			return CacheFactory.doSynchronousClusterTask(task, nodeID.getBytes());
		}catch(IllegalStateException e) {
			log.warn("消息路由到远程节点出错 : "+e);
		}
		return new RemoteTaskResult(106, "未知错误");
	}

	
	/**
	 * 路由注册事件
	 */
	@Override
	public RemoteTaskResult routeEvent(NodeID nodeID, Event event) {
		try {
			EventExecution task = new EventExecution(event);
			log.info("[分发分配事件]-任务 taskId:{} - body:{} - sourceNode:{} - targetNode: {}",
					task.getTaskId(), event,
					nodeID.toString(),
					OCIMServer.getInst().getNodeID());
			return CacheFactory.doSynchronousClusterTask(task, nodeID.getBytes());
		}catch(IllegalStateException e) {
			log.warn("消息路由到远程节点时出错 , {} , 事件 Event : {}" , e , event);
		}
		return new RemoteTaskResult(106, "未知错误");
	}
	
	
	/**
	 * 路由转接信息
	 */
	@Override
	public RemoteTaskResult routeTransferByWaiter(NodeID nodeID, TransferWaiter transferWaiter) {
		try {
			TransferExecution task = new TransferExecution(transferWaiter);
			log.info("[分发转接事件]-任务 taskId:{} - body:{} - sourceNode:{} - targetNode: {}",
					task.getTaskId(), transferWaiter,
					nodeID.toString(),
					OCIMServer.getInst().getNodeID());
			return CacheFactory.doSynchronousClusterTask(task, nodeID.getBytes());
		}catch(IllegalStateException e) {
			log.warn("消息路由到远程节点报错 : "+e);
		}
		return new RemoteTaskResult(106 , "未知错误");
	}



	
	
}
