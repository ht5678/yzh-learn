package org.demo.netty.cluster.collection.cache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
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
		return new DefaultCache<>(name, maxCacheSize, maxLifttime);
	}

	@Override
	public <K, V> Cache<K, V> createCache(String name, long maxLifetime, long maxCacheSize) {
		return new DefaultCache<>(name, maxCacheSize, maxLifetime);
	}
	
	@Override
	public void destroyCache(Cache<?, ?> cache) {
		cache.clear();
	}
	
	/**
	 * 非集群模式 , 失效
	 */
	@Override
	public boolean isMasterClusterMember() {
		throw new IllegalStateException("集群服务不可用.");
	}
	
	/**
	 * 非集群模式 , 失效
	 */
	@Override
	public Collection<ClusterNode> getClusterNodeInfo() {
		throw new IllegalStateException("集群服务不可用.");
	}

	/**
	 * 非集群模式 , 失效
	 */
	@Override
	public byte[] getClusterMemberID() {
		throw new IllegalStateException("集群服务不可用.");
	}

	/**
	 * 非集群模式 , 失效
	 */
	@Override
	public long getClusterTime() {
		throw new IllegalStateException("集群服务不可用.");
	}

	@Override
	public boolean doClusterTask(ClusterTask<?> task) {
		throw new IllegalStateException("集群服务不可用.");
	}

	@Override
	public boolean doClusterTask(ClusterTask<?> task, byte[] nodeID) {
		throw new IllegalStateException("集群服务不可用.");
	}

	@Override
	public RemoteTaskResult doSynchronousClusterTask(ClusterTask<?> task, byte[] nodeID) {
		throw new IllegalStateException("集群服务不可用.");
	}

	@Override
	public void updateClusterStats(Map<String, Cache<?, ?>> cachs) {
		throw new IllegalStateException("集群服务不可用.");
	}

	@Override
	public ClusterNode getClusterNodeInfo(byte[] nodeID) {
		throw new IllegalStateException("集群服务不可用.");
	}

	@Override
	public Lock getLock(Object key, Cache<?, ?> cache) {
		return null;
	}

	
	
	
	
	private class LocalLock implements Lock{
		
		private final Object key;
		
		LocalLock(Object key){
			this.key = key;
		}
		

		@Override
		public void lock() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void lockInterruptibly() throws InterruptedException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean tryLock() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void unlock() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Condition newCondition() {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		
	}
	
	
	
	private static class LockAndCount {
		final ReentrantLock lock;
		int count;
		
		public LockAndCount(ReentrantLock lock) {
			this.lock = lock;
		}
	}
	
}
