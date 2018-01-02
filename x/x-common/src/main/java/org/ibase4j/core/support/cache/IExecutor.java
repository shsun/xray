package org.ibase4j.core.support.cache;

import redis.clients.jedis.ShardedJedis;

/**
 */
public interface IExecutor<K> {
	K execute(ShardedJedis jedis);
}
