package org.demo.netty.im.fake.cluster.node;

import java.nio.charset.StandardCharsets;

import org.demo.netty.im.fake.node.NodeID;

import com.hazelcast.core.Member;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月23日 下午5:52:34
 */
public class LocalClusterNode implements ClusterNode{
	
	
	private String hostName;
	
	private NodeID nodeID;
	
	private long joinTime;
	
	private boolean masterMember;
	
	
	
	/**
	 * 
	 * @param member
	 */
	public LocalClusterNode(Member member) {
		this(member, System.currentTimeMillis());
	}
	
	
	/**
	 * 
	 */
	public LocalClusterNode(Member member , Long joinTime) {
		hostName = member.getSocketAddress().getHostName();
		nodeID = new NodeID(member.getUuid().getBytes(StandardCharsets.UTF_8));
	}
	
	
	

	@Override
	public String getHostName() {
		return hostName;
	}

	@Override
	public NodeID getNodeID() {
		return nodeID;
	}

	@Override
	public long getJoinTime() {
		return joinTime;
	}

	@Override
	public boolean isMasterMember() {
		return masterMember;
	}

}
