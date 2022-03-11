package org.demo.hazelcast.cluster;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hazelcast.core.Cluster;
import com.hazelcast.core.LifecycleEvent;
import com.hazelcast.core.LifecycleListener;
import com.hazelcast.core.Member;
import com.hazelcast.core.MemberAttributeEvent;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;
import com.hazelcast.core.LifecycleEvent.LifecycleState;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月11日 下午3:58:23
 */
public class ClusterListenerDemo implements MembershipListener,LifecycleListener{

	
	private final Cluster cluster;
	
	private final Map<String, ClusterNode> clusterNodeInfos = new ConcurrentHashMap<>();
	
	
	/**
	 * 
	 * @param cluster
	 */
	public ClusterListenerDemo(Cluster cluster) {
		this.cluster = cluster;
		
		for(Member member : cluster.getMembers()) {
			clusterNodeInfos.put(member.getUuid(), new LocalClusterNode(member, cluster.getClusterTime()));
		}
	}
	
	
	@Override
	public void stateChanged(LifecycleEvent event) {
		System.out.println("集群节点状态改变 , "+event.getState());
		if(event.getState() == LifecycleState.SHUTDOWN) {
			System.out.println("hazelcast关闭");
		}else if(event.getState() == LifecycleState.STARTED) {
			System.out.println("hazelcast启动成功");
		}
	}

	@Override
	public void memberAdded(MembershipEvent membershipEvent) {
		System.out.println(String.format("NodeID : % 节点加入集群", membershipEvent.getMember().getUuid().toString()));
		clusterNodeInfos.put(membershipEvent.getMember().getUuid(), new LocalClusterNode(membershipEvent.getMember() , cluster.getClusterTime()));
	}

	@Override
	public void memberRemoved(MembershipEvent membershipEvent) {
		String nodeIdStr = membershipEvent.getMember().getUuid();
		System.out.println(String.format("Node ID : %s 节点离开集群", nodeIdStr));
		clusterNodeInfos.remove(nodeIdStr);
	}

	@Override
	public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {
		System.out.println(String.format("Node ID : %s 节点属性变化", memberAttributeEvent.getMember().getUuid()));
		ClusterNode priorNodeInfo = clusterNodeInfos.get(memberAttributeEvent.getMember().getUuid());
		clusterNodeInfos.put(memberAttributeEvent.getMember().getUuid(), new LocalClusterNode(memberAttributeEvent.getMember(), priorNodeInfo.getJoinTime()));
	}

}
