package org.ibase4j.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.AbstractController;
import org.ibase4j.model.SysDic;
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
 * 字典管理
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:14:34
 */
@RestController
@Api(value = "字典管理", description = "字典管理")
@RequestMapping(value = "/dic")
public class SysDicController extends AbstractController<ISysProvider> {

    public String getService() {
        return "sysDicService";
    }

    @ApiOperation(value = "查询字典项")
    @RequiresPermissions("sys.base.dic.read")
    @PutMapping(value = "/read/list")
    public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody Map<String, Object> param) {
        param.put("orderBy", "sort_no");
        return super.query(request, response, modelMap, param);
    }

    @ApiOperation(value = "字典项详情")
    @RequiresPermissions("sys.base.dic.read")
    @PutMapping(value = "/read/detail")
    public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysDic param) {
        return super.get(request, response, modelMap, param);
    }

    @PostMapping
    @ApiOperation(value = "修改字典项")
    @RequiresPermissions("sys.base.dic.update")
    public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysDic param) {
        return super.update(request, response, modelMap, param);
    }

    @DeleteMapping
    @ApiOperation(value = "删除字典项")
    @RequiresPermissions("sys.base.dic.delete")
    public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysDic param) {
        return super.delete(request, response, modelMap, param);
    }
}
