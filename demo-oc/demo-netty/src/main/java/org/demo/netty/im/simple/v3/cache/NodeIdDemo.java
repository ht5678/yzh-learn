package org.demo.netty.im.simple.v3.cache;

import java.nio.charset.StandardCharsets;

import org.demo.netty.im.fake.node.NodeID;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 生成nodeid
 * @author yuezh2
 * @date	  2022年4月19日 下午9:18:43
 */
public class NodeIdDemo {

	
	
	
	public static void main(String[] args) {
		Config config = new Config();
		
		HazelcastInstance hc1 = Hazelcast.newHazelcastInstance(config);
		//生成nodeid
		System.out.println(new NodeID(hc1.getCluster().getLocalMember().getUuid().getBytes(StandardCharsets.UTF_8)));
	}
	
}
