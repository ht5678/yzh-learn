package org.demo.netty.im.simple.v3.cache;

import java.util.Map.Entry;

import org.demo.netty.im.fake.cluster.collection.cache.Cache;
import org.demo.netty.im.fake.cluster.collection.cache.CacheFactory;
import org.demo.netty.im.fake.domain.Body;
import org.demo.netty.im.fake.domain.BodyType;
import org.demo.netty.im.fake.util.ClusterExternalizableUtil;
import org.demo.netty.im.fake.util.ExternalizableUtil;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月19日 下午10:15:18
 */
public class ClusterCacheDemo {
	
	
	public static void main(String[] args) throws Exception{
		
		Config config = new Config();
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
		
		//---
		ExternalizableUtil.getInstance().setStrategy(new ClusterExternalizableUtil());
		
		//开启cluster cache strategy
		CacheFactory.startCluster(hazelcastInstance);
		
		
		Cache<String, Body> packetCache =  CacheFactory.createCache("clustertest1");
		packetCache.put("22", new Body(BodyType.LOGIN,"cluster content test"));
		
		//
		System.out.println("cache is master member : "+CacheFactory.isMasterMember());
		
		//size指的是value对象的长度
		System.out.println("cache name xxx max size : "+CacheFactory.getMaxCacheSize("clustertest1"));
		System.out.println("cache max life time : "+CacheFactory.getMaxLifeTime("clustertest1"));
		
		System.out.println(packetCache.size());
		
		for(Entry<String, Body> entry : packetCache.entrySet()) {
			System.out.println(entry.getKey() +" : "+entry.getValue());
		}
		
		
		//lock demo , cluster model下 , 都是直接返回false , 
		boolean result = packetCache.lock("123", -1);
		System.out.println(result);
		boolean flag =  packetCache.unlock("123");
		System.out.println(flag);
		
		
		hazelcastInstance.shutdown();
	}
	

}
