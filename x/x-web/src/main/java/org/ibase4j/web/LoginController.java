package org.ibase4j.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.ibase4j.model.SysUser;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.config.annotation.Reference;

import base.Assert;
import base.HttpCode;
import base.config.Resources;
import base.core.AbstractMSAController;
import base.core.Parameter;
import base.exception.LoginException;
import base.login.LoginHelper;
import base.utils.SecurityUtil;
import base.utils.WebUtil;
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
    public Object login(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user,
            @ApiParam(required = true, value = "登录帐号和密码") @RequestBody SysUser param) {

        param.setAccount("admin");
        param.setPassword("111111");

        Assert.notNull(param.getAccount(), "ACCOUNT");
        Assert.notNull(param.getPassword(), "PASSWORD");

        if (LoginHelper.login(param.getAccount(), SecurityUtil.encryptPassword(param.getPassword()))) {
            request.setAttribute("msg", "[" + param.getAccount() + "]登录成功.");
            return setSuccessModelMap(map);
        }
        request.setAttribute("msg", "[" + param.getAccount() + "]登录失败.");
        throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
    }

    // 登出
    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    public Object logout(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user) {
        Long id = WebUtil.getCurrentUser().getId();
        if (id != null) {
            provider.execute(new Parameter("sysSessionService", "delete").setId(id));
        }
        SecurityUtils.getSubject().logout();
        return setSuccessModelMap(map);
    }

    // 注册
    @ApiOperation(value = "用户注册")
    @PostMapping("/regin")
    public Object regin(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user, @RequestBody SysUser param) {
        Assert.notNull(param.getAccount(), "ACCOUNT");
        Assert.notNull(param.getPassword(), "PASSWORD");
        //
        param.setPassword(SecurityUtil.encryptPassword(param.getPassword()));
        provider.execute(new Parameter("sysUserService", "update").setModel(param));
        if (LoginHelper.login(param.getAccount(), param.getPassword())) {
            return setSuccessModelMap(map);
        }
        throw new IllegalArgumentException(Resources.getMessage("LOGIN_FAIL"));
    }

    // 没有登录
    @ApiOperation(value = "没有登录")
    @RequestMapping(value = "/unauthorized", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
    public Object unauthorized(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user) throws Exception {
        return setModelMap(map, HttpCode.UNAUTHORIZED);
    }

    // 没有权限
    @ApiOperation(value = "没有权限")
    @RequestMapping(value = "/forbidden", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
    public Object forbidden(HttpServletRequest request, HttpServletResponse response, ModelMap map, SysUser user) {
        return setModelMap(map, HttpCode.FORBIDDEN);
    }
}
