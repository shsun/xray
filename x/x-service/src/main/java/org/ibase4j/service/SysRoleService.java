package org.ibase4j.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.model.SysDept;
import org.ibase4j.model.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;

@Service
@CacheConfig(cacheNames = "sysRole")
public class SysRoleService extends BaseService<SysRole> {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysAuthorizeService sysAuthorizeService;

    // @Cacheable(value = { "sysRoleMap" }, key = "'query'.concat(#key)")
    @Cacheable(value = "sysRoleMap")
    public Page<SysRole> query(Map<String, Object> map) {
        Page<SysRole> pageInfo = super.query(map);
        // 权限信息
        for (SysRole bean : pageInfo.getRecords()) {
            if (bean.getDeptId() != null) {
                SysDept dept = sysDeptService.queryById(bean.getDeptId());
                bean.setDeptName(dept.getDeptName());
            }

            List<String> list = sysAuthorizeService.queryRolePermission(bean.getId());
            for (String permission : list) {
                if (StringUtils.isBlank(bean.getPermission())) {
                    bean.setPermission(permission);
                } else {
                    bean.setPermission(bean.getPermission() + ";" + permission);
                }
            }
        }
        return pageInfo;
    }
}
