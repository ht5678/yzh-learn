package org.demo.netty.im.fake.routing;

import org.demo.netty.im.fake.node.NodeID;

/**
 * 路由信息
 * @author yuezh2
 * @date	  2022年3月28日 下午4:49:32
 */
public interface Route {
    /**
     * 获取用户ID
     * @return
     */
    String getUid();

    /**
     * 获取集群节点ID
     * @return
     */
    NodeID getNodeID();

    /**
     * 获取版本信息
     * @return
     */
    String getVersion();
}
