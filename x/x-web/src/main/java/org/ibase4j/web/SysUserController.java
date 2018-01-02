package org.ibase4j.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import base.core.AbstractMSAController;
import base.core.Parameter;
import org.ibase4j.core.support.Assert;
import org.ibase4j.core.support.HttpCode;
import org.ibase4j.core.util.SecurityUtil;
import org.ibase4j.core.util.UploadUtil;
import org.ibase4j.core.util.WebUtil;
import org.ibase4j.model.SysUser;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "用户管理", description = "用户管理")
@RequestMapping(value = "/user")
public class SysUserController extends AbstractMSAController<ISysProvider> {

    public String getService() {
        return "sysUserService";
    }

    @PostMapping
    @ApiOperation(value = "修改用户信息")
    //@RequiresPermissions("sys.base.user.update")
    public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUser param) {
        Assert.isNotBlank(param.getAccount(), "ACCOUNT");
        Assert.length(param.getAccount(), 3, 15, "ACCOUNT");
        if (param.getId() != null) {
            // update
            Parameter parameter = new Parameter(getService(), "queryById").setId(param.getId());
            SysUser user = (SysUser) provider.execute(parameter).getModel();
            Assert.notNull(user, "USER", param.getId());
            if (StringUtils.isNotBlank(param.getPassword()) && !param.getPassword().equals(user.getPassword())) {
                param.setPassword(SecurityUtil.encryptPassword(param.getPassword()));
            }
        } else {
            // create
            param.setPassword(SecurityUtil.encryptPassword(param.getPassword()));
        }
        return super.update(request, response, map, param);
    }

    @ApiOperation(value = "查询用户")
    //@RequiresPermissions("sys.base.user.read")
    @PutMapping(value = "/read/list")
    public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody Map<String, Object> param) {
        return super.query(request, response, map, param);
    }

    @ApiOperation(value = "用户详细信息")
    //@RequiresPermissions("sys.base.user.read")
    @PutMapping(value = "/read/detail")
    public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUser param) {
        return super.get(request, response, map, param);
    }

    @ApiOperation(value = "删除用户")
    //@RequiresPermissions("sys.base.user.delete")
    @DeleteMapping
    public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUser param) {
        Object o = super.delete(request, response, map, param);
        return o;
    }

    @ApiOperation(value = "当前用户信息")
    @GetMapping(value = "/read/promission")
    public Object promission(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        Long id = getCurrUser();
        Parameter parameter = new Parameter(getService(), "queryById").setId(id);
        SysUser sysUser = (SysUser) provider.execute(parameter).getModel();
        map.put("user", sysUser);
        parameter = new Parameter("sysAuthorizeService", "queryAuthorizeByUserId").setId(id);
        List<?> menus = provider.execute(parameter).getList();
        map.put("menus", menus);
        return setSuccessModelMap(map);
    }

    @ApiOperation(value = "当前用户信息")
    @GetMapping(value = "/read/current")
    public Object current(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        SysUser param = new SysUser();
        param.setId(getCurrUser());
        return super.get(request, response, map, param);
    }

    @ApiOperation(value = "修改个人信息")
    @PostMapping(value = "/update/person")
    public Object updatePerson(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUser param) {
        param.setId(WebUtil.getCurrentUser());
        param.setPassword(null);
        Assert.isNotBlank(param.getAccount(), "ACCOUNT");
        Assert.length(param.getAccount(), 3, 15, "ACCOUNT");
        return super.update(request, response, map, param);
    }

    @ApiOperation(value = "修改用户头像")
    @PostMapping(value = "/update/avatar")
    public Object updateAvatar(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        List<String> fileNames = UploadUtil.uploadImage(request);
        if (fileNames.size() > 0) {
            SysUser param = new SysUser();
            param.setId(WebUtil.getCurrentUser());
            String filePath = UploadUtil.getUploadDir(request) + fileNames.get(0);
            // String avatar = UploadUtil.remove2DFS("sysUser", "user" +
            // sysUser.getId(), filePath).getRemotePath();
            // String avatar = UploadUtil.remove2Sftp(filePath, "user" +
            // sysUser.getId());
            param.setAvatar(filePath);
            return super.update(request, response, map, param);
        } else {
            setModelMap(map, HttpCode.BAD_REQUEST);
            map.put("msg", "请选择要上传的文件！");
            return map;
        }
    }

    @ApiOperation(value = "修改密码")
    @PostMapping(value = "/update/password")
    public Object updatePassword(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUser param) {
        Assert.notNull(param.getId(), "USER_ID");
        Assert.isNotBlank(param.getOldPassword(), "OLDPASSWORD");
        Assert.isNotBlank(param.getPassword(), "PASSWORD");
        String encryptPassword = SecurityUtil.encryptPassword(param.getOldPassword());
        Parameter parameter = new Parameter(getService(), "queryById").setId(param.getId());
        SysUser sysUser = (SysUser) provider.execute(parameter).getModel();
        Assert.notNull(sysUser, "USER", param.getId());
        Long userId = WebUtil.getCurrentUser();
        if (!param.getId().equals(userId)) {
            SysUser current = new SysUser();
            current.setId(userId);
            parameter = new Parameter(getService(), "queryById").setId(current.getId());
            SysUser user = (SysUser) provider.execute(parameter).getModel();
            if (user.getUserType() == 1) {
                throw new UnauthorizedException("您没有权限修改用户密码.");
            }
        } else {
            if (!sysUser.getPassword().equals(encryptPassword)) {
                throw new UnauthorizedException("原密码错误.");
            }
        }
        param.setPassword(encryptPassword);
        param.setUpdateBy(WebUtil.getCurrentUser());
        Object o = super.update(request, response, map, param);
        return o;
    }
}
