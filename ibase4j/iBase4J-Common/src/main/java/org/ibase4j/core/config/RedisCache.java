package org.ibase4j.core.config;

import org.springframework.cache.Cache;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import org.slf4j.Logger;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.StringUtils;

import java.util.concurrent.Callable;

import static org.slf4j.LoggerFactory.getLogger;

public class RedisCache implements Cache {

    private static final Logger LOG = getLogger(RedisCache.class);

    private String name;

    private long timeout;

    private RedisTemplate<String, Object> redisTemplate;

    public RedisCache() {

    }

    private byte[] getByteKey(Object key) {
        if (key instanceof String) {
            String preKey = key.toString();
            return preKey.getBytes();
        } else {
            return SerializableUtils.serialize(key);
        }
    }

    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public ValueWrapper get(Object key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        } else {
            final String finalKey;
            if (key instanceof String) {
                finalKey = (String) key;
            } else {
                finalKey = key.toString();
            }
            Object object = null;

            object = redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] key = finalKey.getBytes();
                    byte[] value = connection.get(key);
                    if (value == null) {
                        return null;
                    }
                    return SerializableUtils.unserialize(value);
                }
            });
            return (object != null ? new SimpleValueWrapper(object) : null);
        }
    }

    @SuppressWarnings("unchecked")
    // @Override
    public <T> T get(Object key, Class<T> type) {
        if (StringUtils.isEmpty(key) || null == type) {
            return null;
        } else {
            final String finalKey;
            final Class<T> finalType = type;
            if (key instanceof String) {
                finalKey = (String) key;
            } else {
                finalKey = key.toString();
            }
            final Object object = redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] key = finalKey.getBytes();
                    byte[] value = connection.get(key);
                    if (value == null) {
                        return null;
                    }
                    return SerializableUtils.unserialize(value);
                }
            });
            if (finalType != null && finalType.isInstance(object) && null != object) {
                return (T) object;
            } else {
                return null;
            }
        }
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

    @Override
    public void put(final Object key, final Object value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        } else {
            final String finalKey;
            if (key instanceof String) {
                finalKey = (String) key;
            } else {
                finalKey = key.toString();
            }
            if (!StringUtils.isEmpty(finalKey)) {
                final Object finalValue = value;
                redisTemplate.execute(new RedisCallback<Boolean>() {
                    @Override
                    public Boolean doInRedis(RedisConnection connection) {
                        connection.set(finalKey.getBytes(), SerializableUtils.serialize(finalValue));
                        connection.expire(finalKey.getBytes(), timeout);
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object o, Object o1) {
        return null;
    }

    @Override
    public void evict(Object key) {
        if (null != key) {
            final String finalKey;
            if (key instanceof String) {
                finalKey = (String) key;
            } else {
                finalKey = key.toString();
            }
            if (!StringUtils.isEmpty(finalKey)) {
                LOG.info("删除---------------{}", finalKey);
                redisTemplate.execute(new RedisCallback<Long>() {
                    public Long doInRedis(RedisConnection connection) throws DataAccessException {
                        return connection.del(finalKey.getBytes());
                    }
                });
            }
        }
    }

    /*
     * 清楚系统缓存
     */
    @Override
    public void clear() {
        // TODO Auto-generated method stub
        // redisTemplate.execute(new RedisCallback<String>() {
        // public String doInRedis(RedisConnection connection) throws DataAccessException {
        // connection.flushDb();
        // return "ok";
        // }
        // });
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
