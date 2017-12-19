package org.ibase4j.web;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.AbstractController;
import org.ibase4j.core.base.Parameter;
import org.ibase4j.model.SysMenu;
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
 * 菜单管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:14:54
 */
@RestController
@Api(value = "菜单管理", description = "菜单管理")
@RequestMapping(value = "menu")
public class SysMenuController extends AbstractController<ISysProvider> {
    public String getService() {
        return "sysMenuService";
    }

    @ApiOperation(value = "查询菜单")
    @PutMapping(value = "/read/page")
    @RequiresPermissions("sys.base.menu.read")
    public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody Map<String, Object> param) {
        return super.query(request, response, modelMap, param);
    }

    @ApiOperation(value = "查询菜单")
    @PutMapping(value = "/read/list")
    @RequiresPermissions("sys.base.menu.read")
    public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody Map<String, Object> param) {
        return super.queryList(request, response, modelMap, param);
    }

    @ApiOperation(value = "菜单详情")
    @PutMapping(value = "/read/detail")
    @RequiresPermissions("sys.base.menu.read")
    public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysMenu param) {
        return super.get(request, response, modelMap, param);
    }

    @PostMapping
    @ApiOperation(value = "修改菜单")
    @RequiresPermissions("sys.base.menu.update")
    public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysMenu param) {
        return super.update(request, response, modelMap, param);
    }

    @DeleteMapping
    @ApiOperation(value = "删除菜单")
    @RequiresPermissions("sys.base.menu.delete")
    public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysMenu param) {
        return super.delete(request, response, modelMap, param);
    }

    @ApiOperation(value = "获取所有权限")
    @RequiresPermissions("sys.base.menu.read")
    @RequestMapping(value = "/read/permission")
    public Object getPermissions(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Parameter parameter = new Parameter(getService(), "getPermissions").setModel(new SysMenu());
        List<?> list = provider.execute(parameter).getList();
        return setSuccessModelMap(modelMap, list);
    }
}
