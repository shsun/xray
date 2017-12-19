package org.ibase4j.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.ibase4j.core.base.AbstractController;
import org.ibase4j.core.base.Parameter;
import org.ibase4j.core.config.Resources;
import org.ibase4j.core.exception.LoginException;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.support.HttpCode;
import org.ibase4j.core.support.login.LoginHelper;
import org.ibase4j.core.util.SecurityUtil;
import org.ibase4j.core.util.WebUtil;
import org.ibase4j.model.SysUser;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户登录
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:11:21
 */
@RestController
@Api(value = "登录接口", description = "登录接口")
public class LoginController extends AbstractController<ISysProvider> {

    public String getService() {
        return "sysUserService";
    }



    @Reference
    ISysProvider sysProvider;

    // 登录
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Object login(HttpServletRequest request, HttpServletResponse response, ModelMap map, @ApiParam(required = true, value = "登录帐号和密码") @RequestBody SysUser sysUser) {

        sysUser.setAccount("admin");
        sysUser.setPassword("111111");

        Assert.notNull(sysUser.getAccount(), "ACCOUNT");
        Assert.notNull(sysUser.getPassword(), "PASSWORD");
        
        if (LoginHelper.login(sysUser.getAccount(), SecurityUtil.encryptPassword(sysUser.getPassword()))) {
            request.setAttribute("msg", "[" + sysUser.getAccount() + "]登录成功.");
            return setSuccessModelMap(map);
        }
        request.setAttribute("msg", "[" + sysUser.getAccount() + "]登录失败.");
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
    public Object regin(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUser sysUser) {
        Assert.notNull(sysUser.getAccount(), "ACCOUNT");
        Assert.notNull(sysUser.getPassword(), "PASSWORD");
        sysUser.setPassword(SecurityUtil.encryptPassword(sysUser.getPassword()));
        provider.execute(new Parameter("sysUserService", "update").setModel(sysUser));
        if (LoginHelper.login(sysUser.getAccount(), sysUser.getPassword())) {
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