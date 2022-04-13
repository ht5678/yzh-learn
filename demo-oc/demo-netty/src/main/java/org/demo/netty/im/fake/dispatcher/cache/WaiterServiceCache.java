package org.demo.netty.im.fake.dispatcher.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.demo.netty.im.fake.cluster.collection.cache.Cache;
import org.demo.netty.im.fake.dispatcher.cache.pojo.ConciseWaiter;
import org.demo.netty.im.fake.session.NameFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客服正常服务管理
 * @author yuezh2
 * @date	  2022年4月11日 下午5:15:30
 */
public class WaiterServiceCache {

	private static Logger log = LoggerFactory.getLogger(WaiterServiceCache.class);
	
	private final static Long LOCK_TIMEOUT = 1500L;
	private final static String SERVICE_CACHE_PREFIX = "WaiterServiceCache";
	
	private NameFactory nameFactory;
	
	
	/**
	 * 
	 */
	public WaiterServiceCache(NameFactory nameFactory) {
		this.nameFactory = nameFactory;
	}
	
	/**
	 * 
	 * @return
	 */
	public ConciseWaiter get(String teamCode , String waiterCode) {
		Cache<String, ConciseWaiter> caches = getCache(teamCode);
		return caches.get(waiterCode);
	}
	
	/**
	 * 
	 * @param teamCode
	 * @return
	 */
	public List<ConciseWaiter> get(String teamCode) {
		Cache<String, ConciseWaiter> caches = getCache(teamCode);
		List<ConciseWaiter> values = new ArrayList<>(caches.values());
		Collections.sort(values , new Comparator<ConciseWaiter>() {

			@Override
			public int compare(ConciseWaiter before, ConciseWaiter after) {
				return before.getScore() > after.getScore() ? 0 : -1;
			}
			
		});
		return values;
	}
	
	/**
	 * 
	 * @param teamCode
	 * @param conciseWaiter
	 */
    public void put(String teamCode, ConciseWaiter conciseWaiter) {
        if (null == teamCode || null == conciseWaiter || null == conciseWaiter.getWaiterCode()) {
            log.warn("WaiterServiceCache put opt params has NULL");
            return;
        }
        Cache<String, ConciseWaiter> caches = getCache(teamCode);
        String waiterCode = conciseWaiter.getWaiterCode();
        try {
            caches.lock(waiterCode, LOCK_TIMEOUT);
            caches.put(waiterCode, conciseWaiter);
        } finally {
            caches.unlock(waiterCode);
        }
    }

    
    /**
     * 
     * @param teamCode
     * @param waiterCode
     */
    public void put(String teamCode, String waiterCode) {
        if (null == teamCode || null == waiterCode) {
            log.warn("WaiterServiceCache put opt params has NULL");
            return;
        }
        Cache<String, ConciseWaiter> caches = getCache(teamCode);
        ConciseWaiter conciseWaiter = caches.get(waiterCode);
        if (conciseWaiter == null) {
            conciseWaiter  = new ConciseWaiter(waiterCode, teamCode);
            try {
                caches.lock(waiterCode, LOCK_TIMEOUT);
                caches.put(waiterCode, conciseWaiter);
            } finally {
                caches.unlock(waiterCode);
            }
        }
    }

    
    /**
     * 
     * @param teamCode
     * @param conciseWaiter
     */
    public void update(String teamCode, ConciseWaiter conciseWaiter) {
        if (null == teamCode) {
            log.warn("团队编码不能为空");
            return;
        }
        Cache<String, ConciseWaiter> cache = getCache(teamCode);
        if (null != cache) {
            cache.put(conciseWaiter.getWaiterCode(), conciseWaiter);
        }
    }

    
    /**
     * 
     * @param teamCode
     * @param waiterCode
     * @return
     */
    public ConciseWaiter remove(String teamCode, String waiterCode) {
        Cache<String, ConciseWaiter> caches = getCache(teamCode);
        ConciseWaiter conciseWaiter;
        try {
            caches.lock(waiterCode, LOCK_TIMEOUT);
            conciseWaiter = caches.remove(waiterCode);
        } finally {
            caches.unlock(waiterCode);
        }
        return conciseWaiter;
    }	
	
	
	/**
	 * 
	 * @return
	 */
	private Cache<String, ConciseWaiter> getCache(String teamCode){
		return nameFactory.getCache(SERVICE_CACHE_PREFIX, teamCode);
	}
	
	
}
