package org.ibase4j.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.model.SysNews;
import org.ibase4j.model.SysUser;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import base.core.AbstractMSAController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *
 */
@RestController
@Api(value = "新闻管理", description = "新闻管理")
@RequestMapping(value = "news")
public class SysNewsController extends AbstractMSAController<ISysProvider> {
    public String getService() {
        return "sysNewsService";
    }

    @ApiOperation(value = "查询新闻")
    @RequiresPermissions("public.news.read")
    @PutMapping(value = "/read/list")
    public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody Map<String, Object> param) {
        return super.query(request, response, map, user, param);
    }

    @ApiOperation(value = "新闻详情")
    @RequiresPermissions("public.news.read")
    @PutMapping(value = "/read/detail")
    public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody SysNews param) {
        Object o = super.get(request, response, map, user, param);
        return o;
    }

    @PostMapping
    @ApiOperation(value = "修改新闻")
    @RequiresPermissions("public.news.update")
    public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody SysNews param) {
        Object o = super.update(request, response, map, user, param);
        return o;
    }

    @DeleteMapping
    @ApiOperation(value = "删除新闻")
    @RequiresPermissions("public.news.delete")
    public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody SysNews param) {
        Object o = super.delete(request, response, map, user, param);
        return o;
    }
}
