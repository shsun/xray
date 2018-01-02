package org.ibase4j.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import base.core.AbstractMSAController;
import base.core.Parameter;
import org.ibase4j.core.config.Resources;
import base.exception.LoginException;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.support.HttpCode;
import base.login.LoginHelper;
import org.ibase4j.core.util.SecurityUtil;
import org.ibase4j.core.util.WebUtil;
import org.ibase4j.model.SysUser;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.config.annotation.Reference;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "登录接口", description = "登录接口")
public class LoginController extends AbstractMSAController<ISysProvider> {

    public String getService() {
        return "sysUserService";
    }

    @Reference
    ISysProvider sysProvider;

    // 登录
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Object login(HttpServletRequest request, HttpServletResponse response, ModelMap map,
            @ApiParam(required = true, value = "登录帐号和密码") @RequestBody SysUser user) {

        user.setAccount("admin");
        user.setPassword("111111");

        Assert.notNull(user.getAccount(), "ACCOUNT");
        Assert.notNull(user.getPassword(), "PASSWORD");

        if (LoginHelper.login(user.getAccount(), SecurityUtil.encryptPassword(user.getPassword()))) {
            request.setAttribute("msg", "[" + user.getAccount() + "]登录成功.");
            return setSuccessModelMap(map);
        }
        request.setAttribute("msg", "[" + user.getAccount() + "]登录失败.");
        throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
    }

    // 登出
    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    public Object logout(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        Long id = WebUtil.getCurrentUser();
        if (id != null) {
            provider.execute(new Parameter("sysSessionService", "delete").setId(id));
        }
        SecurityUtils.getSubject().logout();
        return setSuccessModelMap(map);
    }

    // 注册
    @ApiOperation(value = "用户注册")
    @PostMapping("/regin")
    public Object regin(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUser user) {
        Assert.notNull(user.getAccount(), "ACCOUNT");
        Assert.notNull(user.getPassword(), "PASSWORD");
        //
        user.setPassword(SecurityUtil.encryptPassword(user.getPassword()));
        provider.execute(new Parameter("sysUserService", "update").setModel(user));
        if (LoginHelper.login(user.getAccount(), user.getPassword())) {
            return setSuccessModelMap(map);
        }
        throw new IllegalArgumentException(Resources.getMessage("LOGIN_FAIL"));
    }

    // 没有登录
    @ApiOperation(value = "没有登录")
    @RequestMapping(value = "/unauthorized", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
    public Object unauthorized(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
        return setModelMap(map, HttpCode.UNAUTHORIZED);
    }

    // 没有权限
    @ApiOperation(value = "没有权限")
    @RequestMapping(value = "/forbidden", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
    public Object forbidden(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        return setModelMap(map, HttpCode.FORBIDDEN);
    }
}
