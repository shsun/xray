package org.ibase4j.service;

import base.core.BaseService;
import org.ibase4j.model.SysNotice;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;


@Service
@CacheConfig(cacheNames = "sysNoticeTemplate")
public class SysNoticeService extends BaseService<SysNotice> {


}
