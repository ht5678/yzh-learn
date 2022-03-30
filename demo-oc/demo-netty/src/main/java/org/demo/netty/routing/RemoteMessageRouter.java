package org.demo.netty.routing;

import org.demo.netty.cluster.task.hazelcast.PacketExecution.RemotePacketType;
import org.demo.netty.domain.Packet;
import org.demo.netty.domain.RemoteTaskResult;
import org.demo.netty.node.NodeID;
import org.demo.netty.register.Event;
import org.demo.netty.transfer.TransferWaiter;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月30日 下午9:59:28
 */
public interface RemoteMessageRouter {

	/**
	 * 路由分发消息 
	 * @param nodeID
	 * @param packet
	 * @param type
	 * @return
	 */
	public RemoteTaskResult routePacket(NodeID nodeID , Packet packet , RemotePacketType type);
	
	
	/**
	 * 删除集群中已经存在的客户session
	 * @param nodeID
	 * @param customerRoute
	 * @return
	 */
	RemoteTaskResult routeRemoveCustomerSession(NodeID nodeID , CustomerRoute customerRoute);

	
	/**
	 * 删除集群中已经存在的客服Session
	 * @param nodeID
	 * @param waiterRoute
	 * @return
	 */
	RemoteTaskResult routeRemoveWaiterSessin(NodeID nodeID , WaiterRoute waiterRoute);

	
	/**
	 * 路由分发转接
	 * @param nodeID
	 * @param transferWaiter
	 * @return
	 */
	public RemoteTaskResult routeTransferByWaiter(NodeID nodeID , TransferWaiter transferWaiter);
	
	
	/**
	 * 路由分发分配事件
	 * @param nodeID
	 * @param event
	 * @return
	 */
	RemoteTaskResult routeEvent(NodeID nodeID , Event event);
	
}



