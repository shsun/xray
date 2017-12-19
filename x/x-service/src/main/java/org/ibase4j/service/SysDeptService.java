package org.ibase4j.service;

import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.SysDept;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "sysDept")
public class SysDeptService extends BaseService<SysDept> {

}
