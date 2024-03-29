package org.demo.netty.im.fake.cluster.collection.cache;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.demo.netty.im.fake.cluster.collection.cache.hazelcast.ClusteredCache;
import org.demo.netty.im.fake.cluster.node.ClusterNode;
import org.demo.netty.im.fake.cluster.node.LocalClusterNode;
import org.demo.netty.im.fake.cluster.task.ClusterTask;
import org.demo.netty.im.fake.domain.RemoteTaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.MaxSizeConfig.MaxSizePolicy;
import com.hazelcast.core.Cluster;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月24日 下午9:39:38
 */
public class ClusteredCacheFactoryStrategy implements CacheFactoryStrategy{
	
	
	private static final Logger log = LoggerFactory.getLogger(ClusteredCacheFactoryStrategy.class);
	
	private static final String HAZELCAST_EXECUTOR_SERVICE_NAME = "oc:cluster:executor";
	private static final long MAX_CLUSTER_EXECUTION_TIME = 5;
	
	private static final String HZ_SET_NAME="default";
	
	private HazelcastInstance hazelcastInstance;
	private Cluster cluster;
	
	
	/**
	 * 
	 */
	public ClusteredCacheFactoryStrategy(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
		this.cluster = hazelcastInstance.getCluster();
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public <K, V> Cache<K, V> createCache(String name) {
		//存活时间--毫秒转换成秒 , 说明hazelcast 0代表永久有效而不是-1 , 本地缓存-1代表永久有效
		long maxLifeTime = CacheFactory.getMaxLifeTime(name);
		final int hazelcastLifetimeInSeconds = maxLifeTime < 0 ? 0 : (int)maxLifeTime/1000;
		
		//缓存大小 hazelcast 必须设置 , -1表示Integer最大值
		long maxCacheSize = CacheFactory.getMaxCacheSize(name);
		
		return createCache(name, maxCacheSize, hazelcastLifetimeInSeconds);
	}

	
	
	/**
	 * 
	 */
	@Override
	public <K, V> Cache<K, V> createCache(String name, long maxLifetime, long maxCacheSize) {
		//存活时间--毫秒转换成秒, 说明hazelcast 0 代表永久有效而不是-1 , 本地缓存-1代表永久有效
		final int hazelcastLifeTimeInSeconds = maxLifetime<0?0:(int)maxLifetime/1000;
		
		//缓存大小 hazelcast必须设置 , -1表示Integer最大值
		return createCache(name, maxCacheSize, hazelcastLifeTimeInSeconds);
	}
	
	
	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param name
	 * @param maxCacheSize
	 * @param hazelcastLifeTimeInSeconds
	 * @return
	 */
	private <K,V> Cache<K, V> createCache(String name , long maxCacheSize , int hazelcastLifeTimeInSeconds){
		int hazelcastMaxCacheSize = maxCacheSize < 0? Integer.MAX_VALUE : (int)maxCacheSize;
		MapConfig mapConfig = hazelcastInstance.getConfig().getMapConfig(HZ_SET_NAME);
		mapConfig.setTimeToLiveSeconds(hazelcastLifeTimeInSeconds);
		mapConfig.setMaxSizeConfig(new MaxSizeConfig(hazelcastMaxCacheSize , MaxSizePolicy.USED_HEAP_SIZE));
		return new ClusteredCache<K,V>(name, hazelcastInstance.getMap(name), hazelcastLifeTimeInSeconds);
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public void destroyCache(Cache<?, ?> cache) {
		ClusteredCache<?, ?> clustered = (ClusteredCache<?, ?>)cache;
		clustered.destroy();
	}
	
	

	@Override
	public boolean isMasterClusterMember() {
		return cluster.getLocalMember().isLiteMember();
	}

	@Override
	public Collection<ClusterNode> getClusterNodeInfo() {
		return null;
	}

	@Override
	public byte[] getClusterMemberID() {
		return hazelcastInstance.getCluster().getLocalMember().getUuid().getBytes();
	}

	@Override
	public long getClusterTime() {
		return cluster == null ? System.currentTimeMillis() : cluster.getClusterTime();
	}

	@Override
	public boolean doClusterTask(ClusterTask<?> task) {
		return false;
	}

	
	/**
	 * 
	 */
	@Override
	public boolean doClusterTask(ClusterTask<?> task, byte[] nodeID) {
		if(null == cluster) {
			return false;
		}
		
		//
		Member member = getMember(nodeID);
		if(null != member) {
			hazelcastInstance.getExecutorService(HAZELCAST_EXECUTOR_SERVICE_NAME).submitToMember(new CallableTask<>(task), member);
			return true;
		}else {
			log.error("请求节点: {} 在集群中找不到" , new String(nodeID , StandardCharsets.UTF_8));
			return false;
		}
	}


	
	
	
	
	
	/**
	 * 
	 * @return
	 */
	private Member getMember(byte[] nodeID) {
		Member result = null;
		
		Set<Member> members = cluster.getMembers();
		for(Member member : members) {
			if(Arrays.equals(member.getUuid().getBytes(StandardCharsets.UTF_8), nodeID)) {
				result = member;
				break;
			}
		}
		return result;
	}
	
	
	
	/**
	 * 100执行成功 , 101执行缺失目标 .,  102集群不可用 , 103执行超时, 104执行被打断 , 105执行发生异常
	 */
	@Override
	public RemoteTaskResult doSynchronousClusterTask(ClusterTask<?> task, byte[] nodeID) {
		RemoteTaskResult result ;
		if(null == cluster) {
			result = new RemoteTaskResult(102, "集群不可用");
		}else {
			Member member = getMember(nodeID);
			if(null != member) {
				Future <?> future = hazelcastInstance.getExecutorService(HAZELCAST_EXECUTOR_SERVICE_NAME).submitToMember(new CallableTask<>(task), member);
				
				try {
					Object object = future.get(MAX_CLUSTER_EXECUTION_TIME , TimeUnit.SECONDS);
					if(future.isDone()) {
						result = new RemoteTaskResult(100, "执行成功" , object);
					}else {
						future.cancel(true);
						result = new RemoteTaskResult(106, "执行任务超时" , object);
					}
				}catch(TimeoutException e) {
					result = new RemoteTaskResult(103, "任务执行超时");
					log.error("未能执行集群任务在规定 "+MAX_CLUSTER_EXECUTION_TIME +" 秒时间内: {} ", e);
				}catch(InterruptedException e) {
					result = new RemoteTaskResult(104, "任务执行被打断");
					log.error("任务执行被打断异常 : {}" , e);
				}catch(ExecutionException e) {
					result = new RemoteTaskResult(105, "任务执行中发生异常");
					log.error("任务执行异常: {}" , e);
				}
				
			}else {
				result = new RemoteTaskResult(101, "没有找到服务节点 , 认为客户/客服已经离开");
				String msg = MessageFormat.format("请求节点{0}不能在集群中找到,判定当前节点已经离开集群", new String(nodeID , StandardCharsets.UTF_8));
				log.warn(msg);
			}
		}
		return result;
	}

	@Override
	public void updateClusterStats(Map<String, Cache<?, ?>> cachs) {
		
	}

	/**
	 * 
	 */
	@Override
	public ClusterNode getClusterNodeInfo(byte[] nodeID) {
		if(cluster == null) {
			return null;
		}
		
		ClusterNode result = null;
		Member member = getMember(nodeID);
		if(member!=null) {
			result = new LocalClusterNode(member , cluster.getClusterTime());
		}
		
		return result;
	}

	@Override
	public Lock getLock(Object key, Cache<?, ?> cache) {
		return new ClusterLock(key, (ClusteredCache)cache);
	}

	
	
	
	
	
	
	/**
	 * 
	 * @author yuezh2
	 * @date	  2022年3月25日 下午9:17:38
	 */
	private static class ClusterLock implements Lock{
		
		private Object key;
		private ClusteredCache cache;
		
		
		/**
		 * 
		 * @param key
		 * @param cache
		 */
		ClusterLock(Object key , ClusteredCache cache) {
			this.key = key;
			this.cache = cache;
		}
		

		@Override
		public void lock() {
			cache.lock(key, -1);
		}

		@Override
		public void lockInterruptibly() throws InterruptedException {
			cache.lock(key, -1);
		}

		@Override
		public boolean tryLock() {
			return cache.lock(key, 0);
		}

		@Override
		public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
			return cache.lock(key, unit.toMillis(time));
		}

		@Override
		public void unlock() {
			cache.unlock(key);
		}

		@Override
		public Condition newCondition() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	
	
	
	
	/**
	 * 
	 * @author yuezh2
	 * @date	  2022年3月24日 下午10:36:28
	 * @param <V>
	 */
	private static class CallableTask<V> implements Callable<V> , Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = -341010776738218279L;
		
		
		private ClusterTask<V> task;
		
		
		CallableTask(ClusterTask<V> task){
			this.task = task;
		}


		@Override
		public V call() throws Exception {
			task.run();
			log.debug("CallableTask["+task.getClass().getName()+"]  result : "+task.getResult());
			return task.getResult();
		}
		
		
		
	}
	
	
}
