package base.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import base.utils.PropertiesUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 */
@Component
public class JedisTemplate implements ApplicationContextAware {
    private static final Logger logger = LogManager.getLogger();

    private static ShardedJedisPool shardedJedisPool = null;

    private static Integer EXPIRE = PropertiesUtil.getInt("redis.expiration");

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取线程
     * 
     * @return
     */
    private ShardedJedis getJedis() {
        if (shardedJedisPool == null) {
            synchronized (EXPIRE) {
                if (shardedJedisPool == null) {
                    shardedJedisPool = applicationContext.getBean(ShardedJedisPool.class);
                }
            }
        }
        return shardedJedisPool.getResource();
    }

    public <K> K run(String key, IExecutor<K> executor, Integer...expire) {
        return run(key.getBytes(), executor, expire);
        /*
        ShardedJedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        try {
            K result = executor.execute(jedis);
            if (jedis.exists(key)) {
                if (expire == null || expire.length == 0) {
                    jedis.expire(key, EXPIRE);
                } else if (expire.length == 1) {
                    jedis.expire(key, expire[0]);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
        */
    }

    public <K> K run(byte[] key, IExecutor<K> executor, Integer...expire) {
        ShardedJedis jedis = getJedis();
        if (jedis == null) {
            return null;
        }
        try {
            K result = executor.execute(jedis);
            if (jedis.exists(key)) {
                if (expire == null || expire.length == 0) {
                    jedis.expire(key, EXPIRE);
                } else if (expire.length == 1) {
                    jedis.expire(key, expire[0]);
                }
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }
}
