package org.demo.netty.im.fake.routing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.util.internal.PlatformDependent;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月30日 下午9:28:55
 */
public class LocalRoutingTable<T> {

	private static Logger log = LoggerFactory.getLogger(LocalRoutingTable.class);
	
	private Map<String, T> localSessions = PlatformDependent.newConcurrentHashMap();
	
	
	/**
	 * 
	 */
	public void putSession(String key , T session) {
		localSessions.put(key, session);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public T getSession(String uid) {
		if(null == uid) {
			return null;
		}
		return localSessions.get(uid);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean hasSession(String uid) {
		return localSessions.containsKey(uid);
	}
	
	
	/**
	 * 
	 */
	public void remove(String uid) {
		localSessions.remove(uid);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Collection<T> getRoutes() {
		List<T> sessions = new ArrayList<>();
		for(T session : localSessions.values()) {
			sessions.add(session);
		}
		return sessions;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Collection<T> getRoutes(String exceptKey) {
		List<T> sessions = new ArrayList<>();
		T session;
		for(String key : localSessions.keySet()) {
			session = localSessions.get(key);
			if(!key.equals(exceptKey)) {
				session = localSessions.get(key);
				sessions.add(session);
			}
		}
		return sessions;
	}
	
	
	
	/**
	 * 获取除去 (exceptKeys) 之外的本地全部 NettyChannelSession
	 * @return
	 */
	public Collection<T> getRoutes(String... exceptKeys){
		List<T> sessions = new ArrayList<>();
		T session;
		boolean exist;
		for(String key : localSessions.keySet()) {
			exist = false;
			session = localSessions.get(key);
			
			for(String exceptKey : exceptKeys) {
				if(key.equals(exceptKey)) {
					exist = true;
					break;
				}
			}
			
			if(!exist) {
				session = localSessions.get(key);
				sessions.add(session);
			}
		}
		return sessions;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean isLocalSession(String uid) {
		return localSessions.containsKey(uid);
	}
	
}
