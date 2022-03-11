package org.demo.hazelcast.cluster;
public interface ClusterNode {
	
	/**
	 * 返回 主机名称
	 * @return 主机名称
	 */
	String getHostName();
	
	/**
	 * 返回唯一的节点ID
	 * @return 节点ID
	 */
	NodeID getNodeID();
	
	/**
	 * 获取加入时间
	 * @return 加入时间
	 */
	long getJoinTime();
	
	/**
	 * 如果是主节点，则返回true
	 * @return 是否是主节点，是返回true 
	 */
	boolean isMasterMember();
}