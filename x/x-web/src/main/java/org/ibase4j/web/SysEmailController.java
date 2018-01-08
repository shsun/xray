package org.ibase4j.web;

import java.util.Map;

import base.core.AbstractMSAController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.model.SysEmail;
import org.ibase4j.model.SysUser;
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

@RestController
@Api(value = "邮件管理", description = "邮件管理")
@RequestMapping(value = "email")
public class SysEmailController extends AbstractMSAController<ISysProvider> {

    public String getService() {
        return "sysEmailService";
    }

    @ApiOperation(value = "查询邮件")
    @RequiresPermissions("sys.email.list.read")
    @PutMapping(value = "/read/list")
    public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody Map<String, Object> param) {
        return super.query(request, response, map, user, param);
    }

    @ApiOperation(value = "邮件详情")
    @RequiresPermissions("sys.email.list.read")
    @PutMapping(value = "/read/detail")
    public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody SysEmail param) {
        return super.get(request, response, map, user, param);
    }

    @PostMapping
    @ApiOperation(value = "修改邮件")
    @RequiresPermissions("sys.email.list.update")
    public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody SysEmail param) {
        return super.update(request, response, map, user, param);
    }

    @DeleteMapping
    @ApiOperation(value = "删除邮件")
    @RequiresPermissions("sys.email.list.delete")
    public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody SysEmail param) {
        return super.delete(request, response, map, user, param);
    }
}
