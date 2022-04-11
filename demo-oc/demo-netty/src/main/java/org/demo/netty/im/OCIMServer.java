package org.demo.netty.im;

import org.demo.netty.cluster.manager.ClusterManager;
import org.demo.netty.cluster.task.ClusterMessageRouter;
import org.demo.netty.dispatcher.Dispatcher;
import org.demo.netty.im.server.BSServer;
import org.demo.netty.node.NodeID;
import org.demo.netty.routing.RoutingTable;
import org.demo.netty.routing.RoutingTableImpl;
import org.demo.netty.scheduler.CancelableScheduler;
import org.demo.netty.util.ClusterExternalizableUtil;
import org.demo.netty.util.DummyExternalizableUtil;
import org.demo.netty.util.ExternalizableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月23日 下午4:51:50
 */
public class OCIMServer {

	private static Logger log = LoggerFactory.getLogger(OCIMServer.class);
	
	private static String BS_THREAD_NAME = "OCIM-BS-Thread-V1";
	private static String CS_THREAD_NAME = "OCIM-CS-Thread-V1";
			
	private NodeID nodeID;
	private static OCIMServer instance;
	private static final NodeID DEFAULT_NODE_ID= new NodeID(new byte[0]);
	
	private ClusterManager clusterManager;
	private HazelcastInstance hazelcastInstance;
	
	private RoutingTable routingTable;
	
	private Dispatcher dispatcher;
	
	private CancelableScheduler scheduler;
	
	private ClusterMessageRouter clusterMessageRouter;
	
	
	private BSServer bsServer;
	
	
	
	/**
	 * 
	 * @return
	 */
	public static OCIMServer getInst() {
		return instance;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public OCIMServer() throws Exception {
		this(null);
	}
	
	/**
	 * 
	 * @param hazelcastInstance
	 * @throws Exception
	 */
	public OCIMServer(HazelcastInstance hazelcastInstance)throws Exception {
		if(null != hazelcastInstance) {
			throw new IllegalStateException("已经存在一个OCServer正在运行中 ... ");
		}
		instance = this;
		this.hazelcastInstance = hazelcastInstance;
	}
	
	
	
	/**
	 * 
	 */
	public void startCluster(boolean isCluster)throws Exception {
		if(isCluster && null != hazelcastInstance) {
			ExternalizableUtil.getInstance().setStrategy(new ClusterExternalizableUtil());
			clusterManager = new ClusterManager(hazelcastInstance);
			nodeID =  clusterManager.startCluster();
		}else {
			ExternalizableUtil.getInstance().setStrategy(new DummyExternalizableUtil());
		}
		
		//
		this.routingTable = new RoutingTableImpl();
		this.dispatcher = new allot
		
		
	}
	
	
	
	
	public NodeID getNodeID() {
		return nodeID == null ? DEFAULT_NODE_ID : nodeID;
	}

	public void setNodeID(NodeID nodeID) {
		this.nodeID = nodeID;
	}
	
	public RoutingTable getRoutingTable() {
		return routingTable;
	}
	
	public Dispatcher getDispatcher() {
		return dispatcher;
	}	
	
	public CancelableScheduler getScheduler() {
		return scheduler;
	}
	
	public ClusterMessageRouter getClusterMessageRouter() {
		return clusterMessageRouter;
	}
	
}