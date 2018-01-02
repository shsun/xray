package org.ibase4j.service;

import java.util.List;
import java.util.Map;

import base.core.BaseModel;
import base.core.BaseService;
import base.utils.InstanceUtil;
import org.ibase4j.mapper.SysMenuMapper;
import org.ibase4j.model.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "sysMenu")
public class SysMenuService extends BaseService<SysMenu> {

    @Autowired
    private SysDicService sysDicService;

    /**
     * 
     * @param params
     * @return
     */
    public List<SysMenu> queryList(Map<String, Object> params) {
        List<SysMenu> list = super.queryList(params);
        Map<String, String> menuTypeMap = sysDicService.queryDicByType("MENUTYPE");
        Map<Long, Integer> leafMap = InstanceUtil.newHashMap();
        for (SysMenu item : list) {
            if (item.getMenuType() != null) {
                item.setTypeName(menuTypeMap.get(item.getMenuType().toString()));
            }
            if (leafMap.get(item.getId()) == null) {
                leafMap.put(item.getId(), 0);
            }
            leafMap.put(item.getId(), leafMap.get(item.getId()) + 1);
        }
        for (SysMenu item : list) {
            if (leafMap.get(item.getId()) > 0) {
                item.setLeaf(0);
            }
        }
        return list;
    }

    public List<Map<String, String>> getPermissions(BaseModel model) {
        List<Map<String, String>> l = ((SysMenuMapper) mapper).getPermissions();
        return l;
    }

}
