package org.ibase4j.core.support.cache;

import java.io.Serializable;
import java.util.Set;

public interface ICacheManager {
    Object get(final String key);

    Set<Object> getAll(final String pattern);

    void set(final String key, final Serializable value, int seconds);

    void set(final String key, final Serializable value);

    Boolean exists(final String key);

    void del(final String key);

    void delAll(final String pattern);

    String type(final String key);

    Boolean expire(final String key, final int seconds);

    Boolean expireAt(final String key, final long unixTime);

    Long ttl(final String key);

    Object getSet(final String key, final Serializable value);

    boolean setnx(final String key, final Serializable value);

    void unlock(String key);
}
