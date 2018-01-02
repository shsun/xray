package base.cache;

import redis.clients.jedis.ShardedJedis;

/**
 */
public interface IExecutor<K> {
	K execute(ShardedJedis jedis);
}
