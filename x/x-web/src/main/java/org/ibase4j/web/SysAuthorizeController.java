package org.ibase4j.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import base.core.AbstractMSAController;
import base.core.Parameter;
import base.exception.IllegalParameterException;
import org.ibase4j.core.util.WebUtil;
import org.ibase4j.model.SysRoleMenu;
import org.ibase4j.model.SysUserMenu;
import org.ibase4j.model.SysUserRole;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(value = "权限管理", description = "权限管理")
public class SysAuthorizeController extends AbstractMSAController<ISysProvider> {

    public String getService() {
        return "sysAuthorizeService";
    }

    @ApiOperation(value = "获取用户菜单编号")
    @PutMapping(value = "user/read/menu")
    @RequiresPermissions("sys.permisson.userMenu.read")
    public Object getUserMenu(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUserMenu param) {
        Parameter parameter = new Parameter(getService(), "queryMenuIdsByUserId").setId(param.getUserId());
        List<?> menus = provider.execute(parameter).getList();
        return setSuccessModelMap(map, menus);
    }

    @ApiOperation(value = "修改用户菜单")
    @PostMapping(value = "/user/update/menu")
    @RequiresPermissions("sys.permisson.userMenu.update")
    public Object userMenu(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody List<SysUserMenu> list) {
        Long userId = null;
        Long currentUserId = WebUtil.getCurrentUser();
        for (SysUserMenu sysUserMenu : list) {
            if (sysUserMenu.getUserId() != null) {
                if (userId != null && userId != sysUserMenu.getUserId()) {
                    throw new IllegalParameterException("参数错误.");
                }
                userId = sysUserMenu.getUserId();
            }
            sysUserMenu.setCreateBy(currentUserId);
            sysUserMenu.setUpdateBy(currentUserId);
        }
        Parameter parameter = new Parameter(getService(), "updateUserMenu").setList(list);
        provider.execute(parameter);
        return setSuccessModelMap(map);
    }

    @ApiOperation(value = "获取用户角色")
    @PutMapping(value = "user/read/role")
    @RequiresPermissions("sys.permisson.userRole.read")
    public Object getUserRole(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUserRole param) {
        Parameter parameter = new Parameter(getService(), "getRolesByUserId").setId(param.getUserId());
        List<?> menus = provider.execute(parameter).getList();
        return setSuccessModelMap(map, menus);
    }

    @ApiOperation(value = "修改用户角色")
    @PostMapping(value = "/user/update/role")
    @RequiresPermissions("sys.permisson.userRole.update")
    public Object userRole(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody List<SysUserRole> list) {
        Long userId = null;
        Long currentUserId = WebUtil.getCurrentUser();
        for (SysUserRole sysUserRole : list) {
            if (sysUserRole.getUserId() != null) {
                if (userId != null && userId != sysUserRole.getUserId()) {
                    throw new IllegalParameterException("参数错误.");
                }
                userId = sysUserRole.getUserId();
            }
            sysUserRole.setCreateBy(currentUserId);
            sysUserRole.setUpdateBy(currentUserId);
        }
        Parameter parameter = new Parameter(getService(), "updateUserRole").setList(list);
        provider.execute(parameter);
        return setSuccessModelMap(map);
    }

    @ApiOperation(value = "获取角色菜单编号")
    @PutMapping(value = "role/read/menu")
    @RequiresPermissions("sys.permisson.roleMenu.read")
    public Object getRoleMenu(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysRoleMenu param) {
        Parameter parameter = new Parameter(getService(), "queryMenuIdsByRoleId").setId(param.getRoleId());
        List<?> menus = provider.execute(parameter).getList();
        return setSuccessModelMap(map, menus);
    }

    @ApiOperation(value = "修改角色菜单")
    @PostMapping(value = "/role/update/menu")
    @RequiresPermissions("sys.permisson.roleMenu.update")
    public Object roleMenu(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody List<SysRoleMenu> list) {
        Long roleId = null;
        Long userId = WebUtil.getCurrentUser();
        for (SysRoleMenu sysRoleMenu : list) {
            if (sysRoleMenu.getRoleId() != null) {
                if (roleId != null && roleId != sysRoleMenu.getRoleId()) {
                    throw new IllegalParameterException("参数错误.");
                }
                roleId = sysRoleMenu.getRoleId();
            }
            sysRoleMenu.setCreateBy(userId);
            sysRoleMenu.setUpdateBy(userId);
        }
        Parameter parameter = new Parameter(getService(), "updateRoleMenu");
        parameter.setList(list);
        provider.execute(parameter);
        return setSuccessModelMap(map);
    }

    @ApiOperation(value = "获取人员操作权限")
    @PutMapping(value = "user/read/permission")
    @RequiresPermissions("sys.permisson.user.read")
    public Object queryUserPermissions(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUserMenu record) {
        Parameter parameter = new Parameter(getService(), "queryUserPermissions").setModel(record);
        List<?> menuIds = provider.execute(parameter).getList();
        return setSuccessModelMap(map, menuIds);
    }

    @ApiOperation(value = "修改用户操作权限")
    @PostMapping(value = "/user/update/permission")
    @RequiresPermissions("sys.permisson.user.update")
    public Object updateUserPermission(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody List<SysUserMenu> list) {
        Parameter parameter = new Parameter(getService(), "updateUserPermission").setList(list);
        provider.execute(parameter);
        return setSuccessModelMap(map);
    }

    @ApiOperation(value = "获取角色操作权限")
    @PutMapping(value = "role/read/permission")
    @RequiresPermissions("sys.permisson.role.read")
    public Object queryRolePermissions(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysRoleMenu record) {
        Parameter parameter = new Parameter(getService(), "queryRolePermissions").setModel(record);
        List<?> menuIds = provider.execute(parameter).getList();
        return setSuccessModelMap(map, menuIds);
    }

    @ApiOperation(value = "修改角色操作权限")
    @PostMapping(value = "/role/update/permission")
    @RequiresPermissions("sys.permisson.role.update")
    public Object updateRolePermission(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody List<SysRoleMenu> list) {
        Parameter parameter = new Parameter(getService(), "updateRolePermission").setList(list);
        provider.execute(parameter);
        return setSuccessModelMap(map);
    }

    @ApiOperation(value = "清理缓存")
    @RequiresPermissions("sys.cache.update")
    @RequestMapping(value = "/cache/update", method = RequestMethod.POST)
    public Object flush(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        Parameter parameter = new Parameter(getService(), "flushCache");
        provider.execute(parameter);
        return setSuccessModelMap(map);
    }
}
