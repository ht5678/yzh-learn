package org.demo.netty.im.fake.cluster.node;

import org.demo.netty.im.fake.node.NodeID;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月23日 下午5:48:23
 */
public interface ClusterNode {

	
	/**
	 * 返回主机名称
	 * @return
	 */
	String getHostName();
	
	/**
	 * 返回唯一的节点ID
	 * @return
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
