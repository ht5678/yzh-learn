package org.demo.netty.im.simple.v3.cache;

import org.demo.netty.im.fake.cluster.manager.ClusterManager;
import org.demo.netty.im.fake.node.NodeID;
import org.demo.netty.im.fake.util.ClusterExternalizableUtil;
import org.demo.netty.im.fake.util.DummyExternalizableUtil;
import org.demo.netty.im.fake.util.ExternalizableUtil;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月18日 上午10:50:21
 */
public class OCIMServerDemo {

	
	private HazelcastInstance hazelcastInstance;
	
	private ClusterManager clusterManager;
	
	private NodeID nodeID;
	
	
	
	/**
	 * 
	 * @param hazelcastInstance
	 * @throws Exception
	 */
	public OCIMServerDemo(HazelcastInstance hazelcastInstance)throws Exception {
		this.hazelcastInstance = hazelcastInstance;
	}
	
	
	/**
	 * 
	 * @param isCluster
	 * @throws Exception
	 */
	public void startCluster(boolean isCluster)throws Exception {
		if(isCluster && null != hazelcastInstance) {
			ExternalizableUtil.getInstance().setStrategy(new ClusterExternalizableUtil());
			clusterManager = new ClusterManager(hazelcastInstance);
			nodeID =  clusterManager.startCluster();
		}else {
			ExternalizableUtil.getInstance().setStrategy(new DummyExternalizableUtil());
		}
		
		System.out.println(nodeID.toString());
	}
	
	
	public static void main(String[] args) throws Exception{
		boolean isCluster = true;
		
		Config config = new Config();
		
		HazelcastInstance hc1 = Hazelcast.newHazelcastInstance(config);
		
		OCIMServerDemo demo = new OCIMServerDemo(hc1);
		demo.startCluster(isCluster);
		
	}
	
}
