package org.ibase4j.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.AbstractController;
import org.ibase4j.model.SysNotice;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通知管理控制类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:13:31
 */
@RestController
@Api(value = "通知管理", description = "通知管理")
@RequestMapping(value = "notice")
public class SysNoticeController extends AbstractController<ISysProvider> {
    public String getService() {
        return "sysNoticeService";
    }

    @ApiOperation(value = "查询通知")
    @RequiresPermissions("public.notice.read")
    @RequestMapping(value = "/read/list", method = RequestMethod.PUT)
    public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody Map<String, Object> param) {
        return super.query(request, response, modelMap, param);
    }

    @ApiOperation(value = "通知详情")
    @RequiresPermissions("public.notice.read")
    @RequestMapping(value = "/read/detail", method = RequestMethod.PUT)
    public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysNotice param) {
        return super.get(request, response, modelMap, param);
    }

    @ApiOperation(value = "修改通知")
    @RequiresPermissions("public.notice.update")
    @RequestMapping(method = RequestMethod.POST)
    public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysNotice param) {
        return super.update(request, response, modelMap, param);
    }

    @ApiOperation(value = "删除通知")
    @RequiresPermissions("public.notice.delete")
    @RequestMapping(method = RequestMethod.DELETE)
    public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestBody SysNotice param) {
        return super.delete(request, response, modelMap, param);
    }
}
