package org.demo.netty.cluster.collection.cache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.demo.netty.cluster.node.ClusterNode;
import org.demo.netty.cluster.task.ClusterTask;
import org.demo.netty.domain.RemoteTaskResult;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月23日 下午9:59:52
 */
public class DefaultCacheFactoryStrategy implements CacheFactoryStrategy{
	
	//跟踪锁
	private Map<Object, LockAndCount> locks = new ConcurrentHashMap<>();
	
	
	

	@Override
	public <K, V> Cache<K, V> createCache(String name) {
		long maxCacheSize = CacheFactory.getMaxCacheSize(name);
		long maxLifttime = CacheFactory.getMaxLifeTime(name);
		return new defaultcache;
	}

	@Override
	public <K, V> Cache<K, V> createCache(String name, long maxLifetime, long maxCacheSize) {
		return null;
	}

	@Override
	public void destroyCache(Cache<?, ?> cache) {
		
	}

	@Override
	public boolean isMasterClusterMember() {
		return false;
	}

	@Override
	public Collection<ClusterNode> getClusterNodeInfo() {
		return null;
	}

	@Override
	public byte[] getClusterMemberID() {
		return null;
	}

	@Override
	public long getClusterTime() {
		return 0;
	}

	@Override
	public boolean doClusterTask(ClusterTask<?> task) {
		return false;
	}

	@Override
	public boolean doClusterTask(ClusterTask<?> task, byte[] nodeID) {
		return false;
	}

	@Override
	public RemoteTaskResult doSynchronousClusterTask(ClusterTask<?> task, byte[] nodeID) {
		return null;
	}

	@Override
	public void updateClusterStats(Map<String, Cache<?, ?>> cachs) {
		
	}

	@Override
	public ClusterNode getClusterNodeInfo(byte[] nodeID) {
		return null;
	}

	@Override
	public Lock getLock(Object key, Cache<?, ?> cache) {
		return null;
	}

	
	
	
	private static class LockAndCount {
		final ReentrantLock lock;
		int count;
		
		public LockAndCount(ReentrantLock lock) {
			this.lock = lock;
		}
	}
	
}
