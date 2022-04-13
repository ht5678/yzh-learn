package org.demo.netty.im.fake.provider.redis;

/**
 * 
 * @author yue
 *
 */
public class IDProvider {

	public String getChatId() {
		Long value = RedisProvider.getInst().getRedisTmp().opsForValue().increment(RedisSubKey.CHAT_ID, 1);
		if (null == value) {
			value = 100000L;
			RedisProvider.getInst().getRedisTmp().opsForValue().set(RedisSubKey.CHAT_ID, value.toString());
		}
		return value.toString();
	}
	
	public static IDProvider getInstance() {
		return Single.instance;
	}
	
	private static class Single {
		private static IDProvider instance = new IDProvider();
	}
}
