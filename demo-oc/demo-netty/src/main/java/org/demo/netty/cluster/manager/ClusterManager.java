package org.demo.netty.cluster.manager;

import org.demo.netty.node.NodeID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.Cluster;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月23日 下午5:00:07
 */
public class ClusterManager {

	private static Logger log = LoggerFactory.getLogger(ClusterManager.class);
	
	private HazelcastInstance hazelcastInstance;
	private Cluster cluster;
	private ClusterListener clusterListener;
	
	private State state = State.stopped;
	
	
	/**
	 * 
	 * @param hi
	 */
	public ClusterManager(HazelcastInstance hi) {
		hazelcastInstance = hi;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public NodeID startCluster() {
		log.info("正在启动hazelcast集群");
		state = State.starting;
		
		cluster = hazelcastInstance.getCluster();
		clusterListener = new ClusterListener(cluster);
		
		hazelcastInstance.getLifecycleService().addLifecycleListener(clusterListener);
		cluster.addMembershipListener(clusterListener);
		
		cachefactory
		
		return null;
	}
	
	
	
	
	/**
	 * 
	 * @return
	 */
	public State getClusterState() {
		return state;
	}
	
	
	
	
	public enum State{
		stopped,//已经停止
		starting,//启动中
		started;//已启动
	}
	
}
