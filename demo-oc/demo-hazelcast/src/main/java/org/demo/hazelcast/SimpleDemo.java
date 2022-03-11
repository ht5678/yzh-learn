package org.demo.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月11日 上午11:44:29
 */
public class SimpleDemo {

	public static void main(String[] args) {
		HazelcastInstance hc1 = Hazelcast.newHazelcastInstance();
		HazelcastInstance hc2 = Hazelcast.newHazelcastInstance();
		
		hc1.getMap("testcache").put("name", "zhangsan");
		System.out.println(hc1.getMap("testcache"));
		System.out.println(hc2.getMap("testcache"));
	}
	
}
