package org.demo.netty.im.simple.v3.cache;

import org.demo.netty.im.fake.cluster.collection.cache.Cache;
import org.demo.netty.im.fake.cluster.collection.cache.CacheFactory;
import org.demo.netty.im.fake.cluster.collection.set.CustomSetFactory;
import org.demo.netty.im.fake.domain.Body;
import org.demo.netty.im.fake.domain.BodyType;
import org.demo.netty.im.fake.domain.Packet;
import org.demo.netty.im.fake.domain.PacketType;
import org.demo.netty.im.fake.queue.CustomQueueFactory;
import org.demo.netty.im.fake.util.ClusterExternalizableUtil;
import org.demo.netty.im.fake.util.ExternalizableUtil;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月19日 下午9:19:57
 */
public class LocalCacheDemo {

	
	
	public static void main(String[] args) throws Exception{
		
		Config config = new Config();
//		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
		
//		CacheFactory.startCluster(hazelcastInstance);
//		CustomQueueFactory.startCluster(hazelcastInstance);
//		CustomSetFactory.startCluster(hazelcastInstance);
		
		//---
		ExternalizableUtil.getInstance().setStrategy(new ClusterExternalizableUtil());
		
		//默认是local cache - concurrent hash map
		Cache<String, Body> packetCache =  CacheFactory.createLocalCache("test");
		packetCache.put("22", new Body(BodyType.LOGIN,"content test"));
		
//		System.out.println("cache is master member : "+CacheFactory.isMasterMember());
		
		//size指的是value对象的长度
		System.out.println("cache name xxx max size : "+CacheFactory.getMaxCacheSize("test"));
		System.out.println("cache max life time : "+CacheFactory.getMaxLifeTime("test"));
		
		System.out.println(packetCache.size());
		
		
		
		//lock demo , timeout没有实际作用 ,  lock , unlock实际是ReentrantLock的方法调用
		boolean result = packetCache.lock("123", -1);
		System.out.println(result);
		boolean flag =  packetCache.unlock("123");
		System.out.println(flag);
	}
	
}
