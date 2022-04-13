package org.demo.netty.im.fake.store.local;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.demo.netty.im.fake.domain.Properties;
import org.demo.netty.im.fake.properties.PropertiesService;
import org.demo.netty.im.fake.properties.PropertiesServiceImpl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月22日 下午5:14:59
 */
public class LocalPropertiesStore {
	
	private LoadingCache<String, Properties> properties;
	
	private static PropertiesService propertiesService = new PropertiesServiceImpl();
	//缓存过期时间 s
	private static long expireOfSeconds = 60 * 5;
	
	
	
	/**
	 * 
	 */
	private LocalPropertiesStore() {
		initCache();
	}
	
	
	/**
	 * 
	 */
	private void initCache() {
		properties = CacheBuilder.newBuilder()
					.expireAfterWrite(expireOfSeconds , TimeUnit.SECONDS)
					.build(new CacheLoader<String, Properties>() {

						@Override
						public Properties load(String tenantCode) throws Exception {
							return propertiesService.obtainProperties(tenantCode);
						}
						
					});
	}
	
	
	
	/**
	 * 
	 * @param tenantCode
	 * @return
	 */
	public Properties getProperties(String tenantCode) {
		try {
			return properties.get(tenantCode);
		}catch(ExecutionException e) {
			return null;
		}
	}
	
	
	private static class Single {
		private static LocalPropertiesStore store = new LocalPropertiesStore();
	}
	
	
	
	public static LocalPropertiesStore getInst() {
		return Single.store;
	}
	

}
