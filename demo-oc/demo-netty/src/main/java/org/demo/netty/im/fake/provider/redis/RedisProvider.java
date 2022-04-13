package org.demo.netty.im.fake.provider.redis;

import org.demo.netty.im.fake.provider.context.SpringContext;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 
 * @author yue
 *
 */
public class RedisProvider {

    private static StringRedisTemplate stringRedisTemplate = SpringContext.getBean(StringRedisTemplate.class);

    private RedisProvider() {}

    public StringRedisTemplate getRedisTmp() {
        return stringRedisTemplate;
    }

    public static  RedisProvider getInst() {
        return Single.inst;
    }

    private static class Single {
        private static RedisProvider inst = new RedisProvider();
    }
}
