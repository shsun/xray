package org.ibase4j.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.model.SysDept;
import org.ibase4j.model.SysUser;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import base.core.AbstractMSAController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(value = "部门管理", description = "部门管理")
@RequestMapping(value = "dept")
public class SysDeptController extends AbstractMSAController<ISysProvider> {
    public String getService() {
        return "sysDeptService";
    }

    @ApiOperation(value = "查询部门")
    @RequiresPermissions("sys.base.dept.read")
    @PutMapping(value = "/read/list")
    public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody Map<String, Object> param) {
        Long id = super.getCurrUser().getId();
        return super.query(request, response, map, param);
    }

    @ApiOperation(value = "部门详情")
    @RequiresPermissions("sys.base.dept.read")
    @PutMapping(value = "/read/detail")
    public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysDept param) {
        return super.get(request, response, map, param);
    }

    @PostMapping
    @ApiOperation(value = "修改部门")
    @RequiresPermissions("sys.base.dept.update")
    public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysDept param) {
        return super.update(request, response, map, param);
    }

    @DeleteMapping
    @ApiOperation(value = "删除部门")
    @RequiresPermissions("sys.base.dept.delete")
    public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysDept param) {
        return super.delete(request, response, map, param);
    }
}
