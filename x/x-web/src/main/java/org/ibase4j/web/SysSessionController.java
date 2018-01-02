package org.ibase4j.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import base.core.AbstractController;
import org.ibase4j.core.listener.SessionListener;
import org.ibase4j.model.SysSession;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "会话管理", description = "会话管理")
@RequestMapping(value = "/session")
public class SysSessionController extends AbstractController<ISysProvider> {

    public String getService() {
        return "sysSessionService";
    }

    // 查询会话
    @ApiOperation(value = "查询会话")
    @PutMapping(value = "/read/list")
    @RequiresPermissions("sys.base.session.read")
    public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody Map<String, Object> param) {
        Integer number = SessionListener.getAllUserNumber();
        map.put("userNumber", number); // 用户数大于会话数,有用户没有登录
        return super.query(request, response, map, param);
    }

    @DeleteMapping
    @ApiOperation(value = "删除会话")
    @RequiresPermissions("sys.base.session.delete")
    public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysSession param) {
        return super.delete(request, response, map, param);
    }
}
