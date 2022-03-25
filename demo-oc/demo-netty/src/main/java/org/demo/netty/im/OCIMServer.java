package org.demo.netty.im;

import org.demo.netty.cluster.manager.ClusterManager;
import org.demo.netty.node.NodeID;
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
	
	private bsser
	
	
}
