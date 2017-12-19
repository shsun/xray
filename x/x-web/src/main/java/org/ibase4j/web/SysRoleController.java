package org.ibase4j.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.AbstractController;
import org.ibase4j.model.SysRole;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 角色管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:15:43
 */
@RestController
@Api(value = "角色管理", description = "角色管理")
@RequestMapping(value = "role")
public class SysRoleController extends AbstractController<ISysProvider> {
    public String getService() {
        return "sysRoleService";
    }

    @ApiOperation(value = "查询角色")
    @RequiresPermissions("sys.base.role.read")
    @PutMapping(value = "/read/list")
    public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody Map<String, Object> param) {
        return super.query(request, response, modelMap, param);
    }

    @ApiOperation(value = "角色详情")
    @RequiresPermissions("sys.base.role.read")
    @PutMapping(value = "/read/detail")
    public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysRole param) {
        return super.get(request, response, modelMap, param);
    }

    @PostMapping
    @ApiOperation(value = "修改角色")
    @RequiresPermissions("sys.base.role.update")
    public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysRole param) {
        return super.update(request, response, modelMap, param);
    }

    @DeleteMapping
    @ApiOperation(value = "删除角色")
    @RequiresPermissions("sys.base.role.delete")
    public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysRole param) {
        return super.delete(request, response, modelMap, param);
    }
}
