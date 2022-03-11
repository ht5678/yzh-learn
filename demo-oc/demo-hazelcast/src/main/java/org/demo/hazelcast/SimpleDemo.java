package org.demo.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月11日 上午11:44:29
 */
public class SimpleDemo {

	public static void main(String[] args) {
		Config config = new Config();
		
		HazelcastInstance hc1 = Hazelcast.newHazelcastInstance(config);
		HazelcastInstance hc2 = Hazelcast.newHazelcastInstance(config);
		
		hc1.getMap("testcache").put("name", "zhangsan");
		System.out.println(hc1.getMap("testcache"));
		System.out.println(hc2.getMap("testcache"));
		
		hc1.shutdown();
		hc2.shutdown();
	}
	
}
