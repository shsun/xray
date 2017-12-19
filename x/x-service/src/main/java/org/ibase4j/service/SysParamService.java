package org.ibase4j.service;

import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.SysParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "sysParam")
public class SysParamService extends BaseService<SysParam> {

}
