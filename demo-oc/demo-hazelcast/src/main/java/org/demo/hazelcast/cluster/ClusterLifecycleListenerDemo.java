package org.demo.hazelcast.cluster;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Cluster;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月11日 下午3:57:13
 */
public class ClusterLifecycleListenerDemo {

	public static void main(String[] args) {
		Config config = new ClasspathXmlConfig("hazelcast-dev.xml");
		
		
		//node1
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
		
		Cluster cluster = hazelcastInstance.getCluster();
		ClusterListenerDemo clusterListener = new ClusterListenerDemo(cluster);
		
		hazelcastInstance.getLifecycleService().addLifecycleListener(clusterListener);
		cluster.addMembershipListener(clusterListener);
		
		//node2
		HazelcastInstance hazelcastInstance2 = Hazelcast.newHazelcastInstance(config);
		Cluster cluster2 = hazelcastInstance2.getCluster();
		ClusterListenerDemo clusterListener2 = new ClusterListenerDemo(cluster2);
		
		hazelcastInstance2.getLifecycleService().addLifecycleListener(clusterListener2);
		cluster2.addMembershipListener(clusterListener2);
		
		
		//
		System.out.println(cluster.getMembers());
		 Hazelcast.newHazelcastInstance();
		 System.out.println(cluster.getMembers());

		 
		//shutdown
		hazelcastInstance.shutdown();
		hazelcastInstance2.shutdown();
	}
	
}
