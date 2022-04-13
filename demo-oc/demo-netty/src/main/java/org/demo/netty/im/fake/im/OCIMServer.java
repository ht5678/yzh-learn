package org.demo.netty.im.fake.im;

import org.demo.netty.im.fake.cluster.manager.ClusterManager;
import org.demo.netty.im.fake.cluster.task.ClusterMessageRouter;
import org.demo.netty.im.fake.dispatcher.AllotDispatcher;
import org.demo.netty.im.fake.dispatcher.Dispatcher;
import org.demo.netty.im.fake.im.server.BSServer;
import org.demo.netty.im.fake.im.server.Server;
import org.demo.netty.im.fake.node.NodeID;
import org.demo.netty.im.fake.routing.RoutingTable;
import org.demo.netty.im.fake.routing.RoutingTableImpl;
import org.demo.netty.im.fake.scheduler.CancelableScheduler;
import org.demo.netty.im.fake.scheduler.HashedWheelTimeoutScheduler;
import org.demo.netty.im.fake.util.ClusterExternalizableUtil;
import org.demo.netty.im.fake.util.DummyExternalizableUtil;
import org.demo.netty.im.fake.util.ExternalizableUtil;
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
	
//	private Thread csThread;
	private Thread bsThread;
	
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
		this.dispatcher = new AllotDispatcher();
		this.scheduler = new HashedWheelTimeoutScheduler();
		this.clusterMessageRouter = new ClusterMessageRouter();
		
		//
		try {
			bsServer = new BSServer();
		} catch (Exception e) {
			log.error("创建B/S 应用失败.");
			throw new Exception("创建B/S 应用失败", e);
		}
		
		bsThread = new Thread(new ServerStart(bsServer));
		bsThread.setName(BS_THREAD_NAME);
		
		bsThread.start();
		
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
	
	
	/**
	 * 后台启动
	 */
	private class ServerStart implements Runnable {
		private final Server server;

		public ServerStart(Server server) {
			this.server = server;
		}

		@Override
		public void run() {
			server.start();
		}
	}	
	
}
