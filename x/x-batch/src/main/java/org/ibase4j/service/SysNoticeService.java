package org.ibase4j.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.SysNotice;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * @author ShenHuaJie
 *
 */
@Service
@CacheConfig(cacheNames = "sysNoticeTemplate")
public class SysNoticeService extends BaseService<SysNotice> {


}
