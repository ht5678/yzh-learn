package org.demo.netty.im.fake.routing;

import java.util.ArrayList;
import java.util.List;

import org.demo.netty.im.fake.cluster.collection.cache.Cache;
import org.demo.netty.im.fake.cluster.collection.cache.CacheFactory;
import org.demo.netty.im.fake.im.OCIMServer;


/**
 * 
 * @author yuezh2
 * @date	  2022年3月30日 下午4:06:43
 */
public class ClientRouteManager<T> {

	private Cache<String, T> routeCaches;
	
	
	
	/**
	 * 
	 * @param cacheName
	 */
	public ClientRouteManager(String cacheName) {
		routeCaches = CacheFactory.createCache(cacheName);
	}
	
	
	
	/**
	 * 
	 */
	public void put(String key , T value) {
		routeCaches.put(key, value);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public T get(String key) {
		return routeCaches.remove(key);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public T remove(String key) {
		return routeCaches.remove(key);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean containsKey(String key) {
		return routeCaches.containsKey(key);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public List<T> getRoutes() {
		List<T> routes = new ArrayList<>();
		for(T route : routes) {
			routes.add(route);
		}
		return routes;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean isLocal(T route) {
		if(null != route) {
			if(route instanceof WaiterRoute) {
				return OCIMServer.getInst().getNodeID().equals(((WaiterRoute)route).getNodeID());
			}else if(route instanceof CustomerRoute) {
				return OCIMServer.getInst().getNodeID().equals(((CustomerRoute)route).getNodeID());
			}
		}
		return false;
	}
	
	
	/**
	 * 
	 */
	public void lock(String key , long timeout) {
		routeCaches.lock(key, timeout);
	}
	
	
	/**
	 * 
	 */
	public void unlock(String key) {
		routeCaches.unlock(key);
	}
	
	
}
