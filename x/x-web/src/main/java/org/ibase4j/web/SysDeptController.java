package org.ibase4j.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.AbstractController;
import org.ibase4j.model.SysDept;
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
 * 部门管理控制类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:13:31
 */
@RestController
@Api(value = "部门管理", description = "部门管理")
@RequestMapping(value = "dept")
public class SysDeptController extends AbstractController<ISysProvider> {
    public String getService() {
        return "sysDeptService";
    }

    @ApiOperation(value = "查询部门")
    @RequiresPermissions("sys.base.dept.read")
    @PutMapping(value = "/read/list")
    public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody Map<String, Object> param) {
        return super.query(request, response, modelMap, param);
    }

    @ApiOperation(value = "部门详情")
    @RequiresPermissions("sys.base.dept.read")
    @PutMapping(value = "/read/detail")
    public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysDept param) {
        return super.get(request, response, modelMap, param);
    }

    @PostMapping
    @ApiOperation(value = "修改部门")
    @RequiresPermissions("sys.base.dept.update")
    public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysDept param) {
        param.setUnitId(1);
        return super.update(request, response, modelMap, param);
    }

    @DeleteMapping
    @ApiOperation(value = "删除部门")
    @RequiresPermissions("sys.base.dept.delete")
    public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysDept param) {
        return super.delete(request, response, modelMap, param);
    }
}
