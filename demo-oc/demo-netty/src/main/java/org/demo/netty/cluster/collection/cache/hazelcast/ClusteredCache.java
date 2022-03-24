package org.demo.netty.cluster.collection.cache.hazelcast;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.demo.netty.cluster.collection.cache.Cache;
import org.demo.netty.cluster.collection.cache.CacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.IMap;
import com.hazelcast.monitor.LocalMapStats;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月24日 下午9:55:43
 */
public class ClusteredCache<K,V> implements Cache<K, V> {
	
	private static Logger log = LoggerFactory.getLogger(ClusteredCache.class);
	
	protected IMap<K, V> map;
	
	private String name;
	
	private final int hazelcastLifeTimeInSeconds;
	
	
	
	/**
	 * 
	 */
	public ClusteredCache(String name , IMap<K, V> cache , final int hazelcastLifeTimeInSeconds) {
		this.map = cache;
		this.hazelcastLifeTimeInSeconds = hazelcastLifeTimeInSeconds;
		setName(name);
	}
	
	
	

	@Override
	public int size() {
		LocalMapStats stats = map.getLocalMapStats();
		return (int)(stats.getOwnedEntryCount() + stats.getBackupCount());
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public V put(K key, V value) {
		if(value == null) {
			return null;
		}
		return map.put(key, value , this.hazelcastLifeTimeInSeconds , TimeUnit.SECONDS);
	}

	@Override
	public V remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public long getMaxCacheSize() {
		return CacheFactory.getMaxCacheSize(getName());
	}

	@Override
	public void setMaxCacheSize(int size) {
		
	}

	@Override
	public long getMaxLifetime() {
		return CacheFactory.getMaxLifeTime(getName());
	}

	@Override
	public void setMaxLifetime(long maxLifetime) {
		
	}

	@Override
	public long getCacheSize() {
		LocalMapStats stats = map.getLocalMapStats();
		return (int)(stats.getOwnedEntryMemoryCost() + stats.getBackupEntryMemoryCost());
	}

	@Override
	public void destroy() {
		map.destroy();
	}

	
	/**
	 * 
	 */
	@Override
	public boolean lock(K key, long timeout) {
		boolean result = false;
		if(timeout < 0) {
			map.lock(key);
		}else if(timeout ==0){
			result = map.tryLock(key);
		}else {
			try {
				result = map.tryLock(key , timeout , TimeUnit.MILLISECONDS);
			}catch(InterruptedException e) {
				log.error("获取集群锁失败");
				result = false;
			}
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public boolean unlock(K key) {
		boolean result = false;
		try {
			map.unlock(key);
		}catch(Exception e) {
			log.error("释放锁失败");
			result = false;
		}
		return result;
	}

	
}
