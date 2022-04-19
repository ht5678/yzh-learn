package org.demo.netty.im.simple.v3.cache;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.MaxSizeConfig.MaxSizePolicy;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

/**
 * 
 * @author yuezh2
 * @date	  2022年4月19日 下午10:37:28
 */
public class HiMapDemo {

	
	
	
	public static void main(String[] args) {
		//
		Config config = new ClasspathXmlConfig("hazelcast-dev.xml");
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
		
		
		MapConfig mapConfig = hazelcastInstance.getConfig().getMapConfig("default");
		System.out.println("HazelcastInstance config [backup count] :"+mapConfig.getBackupCount());
		
		
		//
		int maxCacheSize = 10;
		int hazelcastMaxCacheSize = maxCacheSize < 0? Integer.MAX_VALUE : (int)maxCacheSize;
		
		
		mapConfig.setTimeToLiveSeconds(100);
		mapConfig.setMaxSizeConfig(new MaxSizeConfig(hazelcastMaxCacheSize , MaxSizePolicy.USED_HEAP_SIZE));
		
		
	 	IMap<String, String> imap = hazelcastInstance.getMap("himaptest");
	 	System.out.println(imap.size());
	 	
	 	imap.put("111", "cluister value");
	 	
	 	System.out.println(imap.size());
		
	 	hazelcastInstance.shutdown();
	}
	
}
