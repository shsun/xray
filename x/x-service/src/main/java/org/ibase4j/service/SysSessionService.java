package org.ibase4j.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import base.core.BaseService;
import org.ibase4j.CacheUtil;
import base.utils.InstanceUtil;
import base.utils.PropertiesUtil;
import org.ibase4j.mapper.SysSessionMapper;
import org.ibase4j.model.SysSession;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "sysSession")
public class SysSessionService extends BaseService<SysSession> {

    public SysSessionService() {
        super();
    }

    /**
     * 
     * @param record
     * @return
     */
    @CachePut
    @Transactional
    public SysSession update(SysSession record) {
        if (record.getId() == null) {
            record.setUpdateTime(new Date());
            Long id = ((SysSessionMapper) mapper).queryBySessionId(record.getSessionId());
            if (id != null) {
                mapper.updateById(record);
            } else {
                record.setCreateTime(new Date());
                mapper.insert(record);
            }
        } else {
            mapper.updateById(record);
        }
        return record;
    }

    /**
     * 系统触发,由系统自动管理缓存
     * 
     * @param sysSession
     */
    public void deleteBySessionId(final SysSession sysSession) {
        ((SysSessionMapper) mapper).deleteBySessionId(sysSession.getSessionId());
    }

    /**
     *
     * @param sysSession
     * @return
     */
    public List<String> querySessionIdByAccount(SysSession sysSession) {
        return ((SysSessionMapper) mapper).querySessionIdByAccount(sysSession.getAccount());
    }

    /**
     *
     */
    public void cleanExpiredSessions() {
        String key = "spring:session:" + PropertiesUtil.getString("session.redis.namespace") + ":sessions:expires:";
        Map<String, Object> columnMap = InstanceUtil.newHashMap();
        List<SysSession> sessions = queryList(columnMap);
        for (SysSession sysSession : sessions) {
            logger.info("检查SESSION : {}", sysSession.getSessionId());
            if (!CacheUtil.getCache().exists(key + sysSession.getSessionId())) {
                logger.info("移除SESSION : {}", sysSession.getSessionId());
                delete(sysSession.getId());
            }
        }
    }
}