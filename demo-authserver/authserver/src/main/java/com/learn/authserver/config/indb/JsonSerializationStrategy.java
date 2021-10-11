package com.learn.authserver.config.indb;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy;
import org.springframework.stereotype.Component;

/**
 * json序列化器
 * @author yuezh2@lenovo.com
 *	@date 2021年10月11日上午10:41:40
 */
@Component
public class JsonSerializationStrategy extends StandardStringSerializationStrategy{
	
	
	private Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class); 
	

	@Override
	protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
		return (T)jackson2JsonRedisSerializer.deserialize(bytes);
	}

	@Override
	protected byte[] serializeInternal(Object object) {
		return jackson2JsonRedisSerializer.serialize(object);
	}

	
	
}
