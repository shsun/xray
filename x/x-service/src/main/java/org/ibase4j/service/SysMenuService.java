package org.ibase4j.service;

import java.util.List;
import java.util.Map;

import org.ibase4j.core.base.BaseModel;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.util.InstanceUtil;
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
        List<SysMenu> pageInfo = super.queryList(params);
        Map<String, String> menuTypeMap = sysDicService.queryDicByType("MENUTYPE");
        Map<Long, Integer> leafMap = InstanceUtil.newHashMap();
        for (SysMenu sysMenu : pageInfo) {
            if (sysMenu.getMenuType() != null) {
                sysMenu.setTypeName(menuTypeMap.get(sysMenu.getMenuType().toString()));
            }
            if (leafMap.get(sysMenu.getId()) == null) {
                leafMap.put(sysMenu.getId(), 0);
            }
            leafMap.put(sysMenu.getId(), leafMap.get(sysMenu.getId()) + 1);
        }
        for (SysMenu sysMenu : pageInfo) {
            if (leafMap.get(sysMenu.getId()) > 0) {
                sysMenu.setLeaf(0);
            }
        }
        return pageInfo;
    }

    public List<Map<String, String>> getPermissions(BaseModel model) {
        List<Map<String, String>> l = ((SysMenuMapper) mapper).getPermissions();
        return l;
    }

}
