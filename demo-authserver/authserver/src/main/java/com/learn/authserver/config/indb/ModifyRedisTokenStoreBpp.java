package com.learn.authserver.config.indb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年10月11日上午10:54:54
 */
@Component
public class ModifyRedisTokenStoreBpp implements BeanPostProcessor{

	@Autowired
	private JsonSerializationStrategy strategy;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ModifyRedisTokenStoreBpp.class);

	
	@Nullable
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof TokenStore) {
			LOGGER.info("beanName : {}" , beanName);
			RedisTokenStore redisTokenStore = (RedisTokenStore)bean;
			redisTokenStore.setSerializationStrategy(strategy);
			return redisTokenStore;
		}
		return bean;
	}
	
	
	
}
