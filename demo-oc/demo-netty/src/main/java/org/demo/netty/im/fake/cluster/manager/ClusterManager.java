package org.demo.netty.im.fake.cluster.manager;

import java.nio.charset.StandardCharsets;

import org.demo.netty.im.fake.cluster.collection.cache.CacheFactory;
import org.demo.netty.im.fake.cluster.collection.set.CustomSetFactory;
import org.demo.netty.im.fake.node.NodeID;
import org.demo.netty.im.fake.queue.CustomQueueFactory;
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
		
		CacheFactory.startCluster(hazelcastInstance);
		CustomQueueFactory.startCluster(hazelcastInstance);
		CustomSetFactory.startCluster(hazelcastInstance);
		
		state = State.started;
		log.info("成功启动Hazelcast集群");
		
		return new NodeID(cluster.getLocalMember().getUuid().getBytes(StandardCharsets.UTF_8));
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
