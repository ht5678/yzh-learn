package org.demo.netty.cluster.collection.set.cluster;

import org.demo.netty.cluster.collection.set.CustomSet;
import org.demo.netty.cluster.collection.set.CustomSetFactoryStategy;

import com.hazelcast.config.SetConfig;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月25日 下午9:44:00
 */
public class ClusteredSetFactoryStrategy implements CustomSetFactoryStategy{

	
	private final static String HZ_SET_CONFIG_NAME = "default";
	
	private HazelcastInstance hazelcastInstance;
	
	
	/**
	 * 
	 * @param hazelcastInstance
	 */
	public ClusteredSetFactoryStrategy(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
	}
	
	
	/**
	 * 
	 */
	@Override
	public <E> CustomSet<E> createOCSet(String name) {
		SetConfig setConfig = hazelcastInstance.getConfig().getSetConfig(HZ_SET_CONFIG_NAME);
		setConfig.setStatisticsEnabled(false);
		return new ClusteredSet<>(name, hazelcastInstance.getSet(name));
	}

	
	
}
