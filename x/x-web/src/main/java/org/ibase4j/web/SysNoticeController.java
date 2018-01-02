package org.ibase4j.web;

import java.util.Map;

import base.core.AbstractMSAController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 *
 */
@RestController
@Api(value = "通知管理", description = "通知管理")
@RequestMapping(value = "notice")
public class SysNoticeController extends AbstractMSAController<ISysProvider> {
    public String getService() {
        return "sysNoticeService";
    }

    @ApiOperation(value = "查询通知")
    @RequiresPermissions("public.notice.read")
    @RequestMapping(value = "/read/list", method = RequestMethod.PUT)
    public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody Map<String, Object> param) {
        return super.query(request, response, map, param);
    }

    @ApiOperation(value = "通知详情")
    @RequiresPermissions("public.notice.read")
    @RequestMapping(value = "/read/detail", method = RequestMethod.PUT)
    public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysNotice param) {
        return super.get(request, response, map, param);
    }

    @ApiOperation(value = "修改通知")
    @RequiresPermissions("public.notice.update")
    @RequestMapping(method = RequestMethod.POST)
    public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysNotice param) {
        return super.update(request, response, map, param);
    }

    @ApiOperation(value = "删除通知")
    @RequiresPermissions("public.notice.delete")
    @RequestMapping(method = RequestMethod.DELETE)
    public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysNotice param) {
        return super.delete(request, response, map, param);
    }
}
