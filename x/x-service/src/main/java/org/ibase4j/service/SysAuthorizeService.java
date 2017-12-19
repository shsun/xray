package org.ibase4j.service;

import java.util.List;
import java.util.Map;

import org.ibase4j.core.util.DataUtil;
import org.ibase4j.core.util.InstanceUtil;
import org.ibase4j.mapper.SysAuthorizeMapper;
import org.ibase4j.mapper.SysRoleMenuMapper;
import org.ibase4j.mapper.SysUserMenuMapper;
import org.ibase4j.mapper.SysUserRoleMapper;
import org.ibase4j.model.SysMenu;
import org.ibase4j.model.SysRoleMenu;
import org.ibase4j.model.SysUserMenu;
import org.ibase4j.model.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

@Service
@CacheConfig(cacheNames = "sysAuthorize")
public class SysAuthorizeService {

    @Autowired
    private SysUserMenuMapper sysUserMenuMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysAuthorizeMapper sysAuthorizeMapper;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * @param userId
     * @return
     */
    public List<Long> queryMenuIdsByUserId(Long userId) {
        return sysUserMenuMapper.queryMenuIdsByUserId(userId);
    }

    @Transactional
    @CacheEvict(value = {"getAuthorize", "sysPermission", "userPermission"}, allEntries = true)
    public void updateUserMenu(List<SysUserMenu> sysUserMenus) {
        Long userId = null;
        for (SysUserMenu sysUserMenu : sysUserMenus) {
            if (sysUserMenu.getUserId() != null && "read".equals(sysUserMenu.getPermission())) {
                userId = sysUserMenu.getUserId();
            }
        }
        if (userId != null) {
            sysAuthorizeMapper.deleteUserMenu(userId, "read");
        }
        for (SysUserMenu sysUserMenu : sysUserMenus) {
            if (sysUserMenu.getUserId() != null && sysUserMenu.getMenuId() != null && "read".equals(sysUserMenu.getPermission())) {
                sysUserMenuMapper.insert(sysUserMenu);
            }
        }
    }

    @Transactional
    @CacheEvict(value = {"getAuthorize", "sysPermission", "userPermission"}, allEntries = true)
    public void updateUserPermission(List<SysUserMenu> list) {
        Long userId = null;
        String permission = null;
        for (SysUserMenu m : list) {
            if (m.getUserId() != null && !"read".equals(m.getPermission())) {
                userId = m.getUserId();
                permission = m.getPermission();
            }
        }
        if (userId != null && DataUtil.isNotEmpty(permission)) {
            sysAuthorizeMapper.deleteUserMenu(userId, permission);
        }
        for (SysUserMenu m : list) {
            if (m.getUserId() != null && m.getMenuId() != null && !"read".equals(m.getPermission())) {
                sysUserMenuMapper.insert(m);
            }
        }
    }

    public List<SysUserRole> getRolesByUserId(Long id) {
        SysUserRole role = new SysUserRole(id, null);
        Wrapper<SysUserRole> wrapper = new EntityWrapper<SysUserRole>(role);
        List<SysUserRole> l = sysUserRoleMapper.selectList(wrapper);
        return l;
    }

    @Transactional
    @CacheEvict(value = {"getAuthorize", "sysPermission", "userPermission", "rolePermission"}, allEntries = true)
    public void updateUserRole(List<SysUserRole> sysUserRoles) {
        Long userId = null;
        for (SysUserRole sysUserRole : sysUserRoles) {
            if (sysUserRole.getUserId() != null) {
                userId = sysUserRole.getUserId();
                break;
            }
        }
        if (userId != null) {
            sysAuthorizeMapper.deleteUserRole(userId);
        }
        for (SysUserRole sysUserRole : sysUserRoles) {
            if (sysUserRole.getUserId() != null && sysUserRole.getRoleId() != null) {
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }

    public List<Long> queryMenuIdsByRoleId(Long roleId) {
        List<Long> l = sysRoleMenuMapper.queryMenuIdsByRoleId(roleId);
        return l;
    }

    @Transactional
    @CacheEvict(value = {"getAuthorize", "sysPermission", "userPermission", "rolePermission"}, allEntries = true)
    public void updateRoleMenu(List<SysRoleMenu> list) {
        Long roleId = null;
        for (SysRoleMenu item : list) {
            if (item.getRoleId() != null && "read".equals(item.getPermission())) {
                roleId = item.getRoleId();
                break;
            }
        }
        if (roleId != null) {
            sysAuthorizeMapper.deleteRoleMenu(roleId, "read");
        }
        for (SysRoleMenu item : list) {
            if (item.getRoleId() != null && item.getMenuId() != null && "read".equals(item.getPermission())) {
                sysRoleMenuMapper.insert(item);
            }
        }
    }

    @Transactional
    @CacheEvict(value = {"getAuthorize", "sysPermission", "userPermission", "rolePermission"}, allEntries = true)
    public void updateRolePermission(List<SysRoleMenu> list) {
        Long roleId = null;
        String permission = null;
        for (SysRoleMenu item : list) {
            if (item.getRoleId() != null && !"read".equals(item.getPermission())) {
                roleId = item.getRoleId();
                permission = item.getPermission();
                break;
            }
        }
        if (roleId != null && DataUtil.isNotEmpty(permission)) {
            sysAuthorizeMapper.deleteRoleMenu(roleId, permission);
        }
        for (SysRoleMenu item : list) {
            if (item.getRoleId() != null && item.getMenuId() != null && !"read".equals(item.getPermission())) {
                sysRoleMenuMapper.insert(item);
            }
        }
    }

    @Cacheable(value = "getAuthorize")
    public List<SysMenu> queryAuthorizeByUserId(Long userId) {
        List<Long> menuIds = sysAuthorizeMapper.getAuthorize(userId);
        List<SysMenu> menus = sysMenuService.getList(menuIds);
        Map<Long, List<SysMenu>> map = InstanceUtil.newHashMap();
        for (SysMenu sysMenu : menus) {
            if (map.get(sysMenu.getParentId()) == null) {
                List<SysMenu> menuBeans = InstanceUtil.newArrayList();
                map.put(sysMenu.getParentId(), menuBeans);
            }
            map.get(sysMenu.getParentId()).add(sysMenu);
        }
        List<SysMenu> result = InstanceUtil.newArrayList();
        for (SysMenu sysMenu : menus) {
            if (sysMenu.getParentId() == 0) {
                sysMenu.setLeaf(0);
                sysMenu.setMenuBeans(getChildMenu(map, sysMenu.getId()));
                result.add(sysMenu);
            }
        }
        return result;
    }

    // 递归获取子菜单
    private List<SysMenu> getChildMenu(Map<Long, List<SysMenu>> map, Long id) {
        List<SysMenu> menus = map.get(id);
        if (menus != null) {
            for (SysMenu sysMenu : menus) {
                sysMenu.setMenuBeans(getChildMenu(map, sysMenu.getId()));
            }
        }
        return menus;
    }

    @Cacheable("sysPermission")
    public List<String> queryPermissionByUserId(Long userId) {
        List<String> l = sysAuthorizeMapper.queryPermissionByUserId(userId);
        return l;
    }

    @Cacheable("userPermission")
    public List<String> queryUserPermission(Long userId) {
        List<String> l = sysUserMenuMapper.queryPermission(userId);
        return l;
    }

    @Cacheable("rolePermission")
    public List<String> queryRolePermission(Long roleId) {
        List<String> l = sysRoleMenuMapper.queryPermission(roleId);
        return l;
    }

    public List<SysMenu> queryMenusPermission() {
        List<SysMenu> l = sysAuthorizeMapper.queryMenusPermission();
        return l;
    }

    public List<Long> queryUserPermissions(SysUserMenu sysUserMenu) {
        List<Long> l = sysUserMenuMapper.queryPermissions(sysUserMenu.getUserId(), sysUserMenu.getPermission());
        return l;
    }

    public List<Long> queryRolePermissions(SysRoleMenu sysRoleMenu) {
        List<Long> l = sysRoleMenuMapper.queryPermissions(sysRoleMenu.getRoleId(), sysRoleMenu.getPermission());
        return l;
    }
}
