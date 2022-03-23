package org.demo.netty.cluster.collection.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月23日 下午10:11:57
 */
public class DefaultCache<K,V> implements Cache<K, V> {
	
	
	

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getMaxCacheSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxCacheSize(int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getMaxLifetime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setMaxLifetime(long maxLifetime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getCacheSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean lock(K key, long timeout) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unlock(K key) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
