package org.demo.netty.cluster.collection.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.demo.netty.cluster.collection.cache.hazelcast.ClusteredCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;

import io.netty.util.internal.PlatformDependent;

/**
 * 创建缓存对象 , 根据不同的缓存策略, 返回的缓存要么是本地 , 要么是远程
 * @author yuezh2
 * @date	  2022年3月23日 下午6:27:00
 */
public class CacheFactory {

	private final static Logger log = LoggerFactory.getLogger(CacheFactory.class);
	
	public static final int DEFAULT_MAX_CACHE_SIZE = 1024*256;
	public static final int DEFAULT_MAX_CACHE_LIFETIME = -1;
	
	private static CacheFactoryStrategy cacheFactoryStrategy;
	private static CacheFactoryStrategy localCacheFactoryStrategy;
	private static CacheFactoryStrategy clusterCacheFactoryStrategy;
	
	private static List<String> localOnly = Collections.synchronizedList(new ArrayList<String>());
	
	//缓存名称 , 并与属性文件进行映射配置缓存
	private static final Map<String, String> cacheNames = PlatformDependent.newConcurrentHashMap();
	
	//默认缓存配置属性
	private static final Map<String, Long> cacheProps = PlatformDependent.newConcurrentHashMap();
	
	static {
		localCacheFactoryStrategy = new DefaultCacheFactoryStrategy();
		//默认采用本地缓存策略
		cacheFactoryStrategy = localCacheFactoryStrategy;
	}
	
	
	/**
	 * 
	 * @param hi
	 */
	public static synchronized void startCluster(HazelcastInstance hi) {
		log.info("CacheFactory 开启HazelcastInstance集群模式");
		clusterCacheFactoryStrategy = new ClusteredCache<K,V>(name, hi, DEFAULT_MAX_CACHE_LIFETIME)
	}
	
	
	
	public static long getMaxCacheSize(String cacheName) {
		return CacheFactory.getCacheProperty(cacheName, ".size", DEFAULT_MAX_CACHE_SIZE);
	}
	
	public static long getMaxLifeTime(String cacheName) {
		return CacheFactory.getCacheProperty(cacheName, ".size", DEFAULT_MAX_CACHE_LIFETIME);
	}
	
	
	private static long getCacheProperty(String cacheName , String suffix , long defaultValue) {
		String propName = "cache."+cacheNames.get(cacheName) + suffix;
		Long defaultSize = cacheProps.get(propName);
		return defaultSize == null ? defaultValue:defaultSize;
	}
	
	
	
	
	
}
