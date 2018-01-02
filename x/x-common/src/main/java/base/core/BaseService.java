package base.core;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import base.IConstants;
import org.ibase4j.CacheUtil;
import base.utils.DataUtil;
import base.utils.InstanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;

/**
 * MUST set CacheConfig(cacheNames="") in sub-class
 */
public abstract class BaseService<T extends BaseModel> implements ApplicationContextAware {

    protected Logger logger = LogManager.getLogger(getClass());

    protected ApplicationContext applicationContext;

    @Autowired
    protected IBaseMapper<T> mapper;

    /**
     *
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 分页查询
     */
    public static Page<Long> getPage(Map<String, Object> params) {
        Page<Long> page;
        Integer current = 1;
        Integer size = 10;
        String orderBy = "id_";
        if (DataUtil.isNotEmpty(params.get("pageNum"))) {
            current = Integer.valueOf(params.get("pageNum").toString());
        }
        if (DataUtil.isNotEmpty(params.get("pageSize"))) {
            size = Integer.valueOf(params.get("pageSize").toString());
        }
        if (DataUtil.isNotEmpty(params.get("orderBy"))) {
            orderBy = (String) params.get("orderBy");
            params.remove("orderBy");
        }
        if (size == -1) {
            page = new Page<Long>();
        } else {
            page = new Page<Long>(current, size, orderBy);
            page.setAsc(false);
        }
        return page;
    }

    /**
     * 根据Id查询(默认类型T)
     *
     * @param ids
     * @return
     */
    public Page<T> getPage(Page<Long> ids) {
        if (ids != null) {
            Page<T> page = new Page<T>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<T> records = InstanceUtil.newArrayList();
            for (Long id : ids.getRecords()) {
                records.add(this.queryById(id));
            }
            page.setRecords(records);
            return page;
        }
        return new Page<T>();
    }

    /**
     * 根据Id查询(默认类型T)
     */
    public Page<Map<String, Object>> getPageMap(Page<Long> ids) {
        Page<Map<String, Object>> page;
        if (ids != null) {
            page = new Page<Map<String, Object>>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<Map<String, Object>> records = InstanceUtil.newArrayList();
            for (Long id : ids.getRecords()) {
                records.add(InstanceUtil.transBean2Map(this.queryById(id)));
            }
            page.setRecords(records);
        } else {
            page = new Page<Map<String, Object>>();
        }
        return page;
    }

    /** 根据Id查询(cls返回类型Class) */
    public <K> Page<K> getPage(Page<Long> ids, Class<K> cls) {
        Page<K> page;
        if (ids != null) {
            page = new Page<K>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<K> records = InstanceUtil.newArrayList();
            for (Long id : ids.getRecords()) {
                T t = this.queryById(id);
                K k = InstanceUtil.to(t, cls);
                records.add(k);
            }
            page.setRecords(records);
        } else {
            page = new Page<K>();
        }
        return page;
    }

    /**
     * 根据Id查询(默认类型T)
     */
    public List<T> getList(List<Long> ids) {
        List<T> list = InstanceUtil.newArrayList();
        if (ids != null) {
            for (Long id : ids) {
                list.add(this.queryById(id));
            }
        }
        return list;
    }

    /** 根据Id查询(cls返回类型Class) */
    public <K> List<K> getList(List<Long> ids, Class<K> cls) {
        List<K> list = InstanceUtil.newArrayList();
        if (ids != null) {
            for (Long id : ids) {
                T t = this.queryById(id);
                K k = InstanceUtil.to(t, cls);
                list.add(k);
            }
        }
        return list;
    }

    @Transactional
    public void del(Long id, Long userId) {
        try {
            T record = this.queryById(id);
            record.setEnable(0);
            record.setUpdateTime(new Date());
            record.setUpdateBy(userId);
            mapper.updateById(record);
            CacheUtil.getCache().set(getCacheKey(id), record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            mapper.deleteById(id);
            CacheUtil.getCache().del(getCacheKey(id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * update the existed record if id is not null, otherwise create a new record.
     *
     * @param record
     * @return
     */
    @Transactional
    public T update(T record) {
        try {
            record.setUpdateTime(new Date());
            if (record.getId() == null) {
                // create
                record.setCreateTime(new Date());
                mapper.insert(record);
            } else {
                // update
                String lockKey = getClass().getName() + record.getId();
                if (CacheUtil.getLock(lockKey)) {
                    try {
                        T org = queryById(record.getId());
                        T update = InstanceUtil.getDiff(org, record);
                        update.setId(record.getId());
                        mapper.updateById(update);
                    } finally {
                        CacheUtil.unlock(lockKey);
                    }
                }
            }
            // retrieve the certain record out while update/create done and flush it to cache
            record = mapper.selectById(record.getId());
            CacheUtil.getCache().set(getCacheKey(record.getId()), record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return record;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public T queryById(Long id) {
        T record = null;
        try {
            String key = getCacheKey(id);
            record = (T) CacheUtil.getCache().get(key);
            if (record == null) {
                record = mapper.selectById(id);
                CacheUtil.getCache().set(key, record);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return record;
    }

    public Page<T> query(Map<String, Object> params) {
        logger.debug("query", params);
        Page<Long> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPage(page);
    }

    public Page<Map<String, Object>> queryMap(Map<String, Object> params) {
        Page<Long> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPageMap(page);
    }

    public List<T> queryList(Map<String, Object> params) {
        logger.debug("queryList", params);
        List<Long> ids = mapper.selectIdPage(params);
        List<T> list = getList(ids);
        return list;
    }

    protected <P> Page<P> query(Map<String, Object> params, Class<P> cls) {
        logger.debug("query", params, cls);
        Page<Long> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPage(page, cls);
    }

    protected String getCacheKey(Object id) {
        logger.debug("getCacheKey", id);
        String cacheName = null;
        CacheConfig cacheConfig = getClass().getAnnotation(CacheConfig.class);
        if (cacheConfig == null || cacheConfig.cacheNames() == null || cacheConfig.cacheNames().length < 1) {
            cacheName = getClass().getName();
        } else {
            cacheName = cacheConfig.cacheNames()[0];
        }
        String k = new StringBuilder(IConstants.CACHE_NAMESPACE).append(cacheName).append(":").append(id).toString();
        return k;
    }
}
