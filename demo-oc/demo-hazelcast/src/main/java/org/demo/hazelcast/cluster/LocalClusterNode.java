package org.demo.hazelcast.cluster;

import java.nio.charset.StandardCharsets;

import com.hazelcast.core.Member;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月11日 下午4:05:50
 */
public class LocalClusterNode implements ClusterNode {

	private String hostName;
	
	private NodeID nodeID;
	
	private long joinTime;
	
	private boolean masterMember;
	
	public LocalClusterNode(Member member) {
		this(member, System.currentTimeMillis());
	}
	
	public LocalClusterNode(Member member, Long joinTime) {
		hostName = member.getSocketAddress().getHostName();
		nodeID = new NodeID(member.getUuid().getBytes(StandardCharsets.UTF_8));
		this.joinTime = joinTime;
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
