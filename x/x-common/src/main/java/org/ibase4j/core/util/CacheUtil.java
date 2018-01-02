package org.ibase4j.core.util;

import java.io.Serializable;

import base.cache.ICacheManager;
import base.cache.RedissonHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheUtil {

    private static ICacheManager cacheManager;

    @Bean
    public ICacheManager setCache() {
        cacheManager = new RedissonHelper();
        return cacheManager;
    }

    public static ICacheManager getCache() {
        return cacheManager;
    }

    /**
     * 获取锁
     */
    public static boolean getLock(final String key) {
        if (!getCache().exists(key)) {
            synchronized (CacheUtil.class) {
                final Serializable value = String.valueOf(System.currentTimeMillis());
                if (!getCache().exists(key) && getCache().setnx(key, value)) {
                    return true;
                }
            }
        }
        final int expires = 1000 * 60 * 3;
        String currentValue = (String) getCache().get(key);
        if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis() - expires) {
            unlock(key);
            return getLock(key);
        }
        return false;
    }

    public static void unlock(String key) {
        getCache().unlock(key);
    }
}