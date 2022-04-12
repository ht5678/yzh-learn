package org.demo.netty.provider.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yue
 *
 */
public class NearServiceProvider {


	private static Logger log = LoggerFactory.getLogger(NearServiceProvider.class);

	public void cacheNearWaiter(String teamCode, String customerCode, String waiterCode) {
		try {
			String key = createKey(teamCode, customerCode);
			RedisProvider.getInst().getRedisTmp().opsForValue().set(key, waiterCode);
		} catch (Exception e) {
			log.error("更新客户近期服务客服信息失败，{}", e);
		}
	}
	
	public String getNearWaiter(String teamCode, String customerCode) {
		try {
			String key = createKey(teamCode, customerCode);
			return RedisProvider.getInst().getRedisTmp().opsForValue().get(key);
		} catch (Exception e) {
			log.error("获取近期服务客服信息失败，{}", e);
			return null;
		}
	}

	private String createKey(String teamCode, String customerCode) {
		return RedisSubKey.REDIS_KEY_PREFIX + teamCode + RedisSubKey.SEPARATOR + customerCode;
	}
	
	public static NearServiceProvider getInst() {
		return Single.instance;
	}
	
	private static class Single {
		private static NearServiceProvider instance = new NearServiceProvider();
	}
}
