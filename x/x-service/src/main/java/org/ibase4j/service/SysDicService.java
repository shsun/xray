package org.ibase4j.service;

import java.util.List;
import java.util.Map;

import base.core.BaseService;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.model.SysDic;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;


@Service
@CacheConfig(cacheNames = "SysDic")
public class SysDicService extends BaseService<SysDic> {

    // @Cacheable(value = "sysDics")
    public Map<String, Map<String, String>> getAllDic() {
        Map<String, Object> p = InstanceUtil.newHashMap();
        p.put("orderBy", "sort_no");
        List<SysDic> list = queryList(p);

        Map<String, Map<String, String>> map = InstanceUtil.newHashMap();
        for (SysDic item : list) {
            String key = item.getType();
            if (map.get(key) == null) {
                Map<String, String> tmp = InstanceUtil.newHashMap();
                map.put(key, tmp);
            }
            map.get(key).put(item.getCode(), item.getCodeText());
        }

        return map;
    }

    // @Cacheable(value = { "sysDicMap" }, key = "'queryDicByType'.concat(#key)")
    // @Cacheable(value = "sysDicMap")
    public Map<String, String> queryDicByType(String key) {
        return applicationContext.getBean(SysDicService.class).getAllDic().get(key);
    }
}
