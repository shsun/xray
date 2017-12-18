package org.ibase4j.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.base.BaseService;
import org.ibase4j.core.support.login.ThirdPartyUser;
import org.ibase4j.core.util.CacheUtil;
import org.ibase4j.core.util.SecurityUtil;
import org.ibase4j.mapper.SysUserMapper;
import org.ibase4j.mapper.SysUserThirdpartyMapper;
import org.ibase4j.model.SysUser;
import org.ibase4j.model.SysUserThirdparty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;

@Service
@CacheConfig(cacheNames = "SysUser")
public class SysUserService extends BaseService<SysUser> {

    @Autowired
    private SysUserThirdpartyMapper thirdpartyMapper;

    @Autowired
    private SysDicService sysDicService;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysAuthorizeService sysAuthorizeService;

    public Page<SysUser> query(Map<String, Object> params) {
        logger.info("query");
        final long start = System.currentTimeMillis();

        Map<String, String> userTypeMap = sysDicService.queryDicByType("USERTYPE");
        Page<SysUser> pageInfo = super.query(params);
        for (SysUser userBean : pageInfo.getRecords()) {
            if (userBean.getUserType() != null) {
                userBean.setUserTypeText(userTypeMap.get(userBean.getUserType().toString()));
            }
            if (userBean.getDeptId() != null) {
                userBean.setDeptName(sysDeptService.queryById(userBean.getDeptId()).getDeptName());
            }
            List<String> permissions = sysAuthorizeService.queryUserPermission(userBean.getId());
            for (String permission : permissions) {
                if (StringUtils.isBlank(userBean.getPermission())) {
                    userBean.setPermission(permission);
                } else {
                    userBean.setPermission(userBean.getPermission() + ";" + permission);
                }
            }
        }

        final long end = System.currentTimeMillis();
        logger.info("SysUserService.query-->" + (end - start));

        return pageInfo;
    }

    /** 查询第三方帐号用户Id */
    @Cacheable
    public Long queryUserIdByThirdParty(ThirdPartyUser param) {
        final Long id = thirdpartyMapper.queryUserIdByThirdParty(param.getProvider(), param.getOpenid());
        return id;
    }

    /** 保存第三方帐号 */
    @Transactional
    public SysUser insertThirdPartyUser(ThirdPartyUser thirdPartyUser) {
        //
        SysUser sysUser = new SysUser();
        //'1','SEX','0','未知'
        //'2','SEX','1','男'
        //'3','SEX','2','女'
        sysUser.setSex(0);
        sysUser.setUserType(1);
        sysUser.setPassword(SecurityUtil.encryptPassword("123456"));
        sysUser.setUserName(thirdPartyUser.getUserName());
        sysUser.setAvatar(thirdPartyUser.getAvatarUrl());

        // 初始化第三方信息
        SysUserThirdparty thirdparty = new SysUserThirdparty();
        thirdparty.setProvider(thirdPartyUser.getProvider());
        thirdparty.setOpenId(thirdPartyUser.getOpenid());
        thirdparty.setCreateTime(new Date());
        //
        this.update(sysUser);
        sysUser.setAccount(thirdparty.getProvider() + sysUser.getId());
        this.update(sysUser);

        thirdparty.setUserId(sysUser.getId());
        thirdpartyMapper.insert(thirdparty);

        return sysUser;
    }

    public void init() {
        SysUserMapper tmp = (SysUserMapper) mapper;
        List<Long> list = tmp.selectIdPage(Collections.<String, Object> emptyMap());
        for (Long id : list) {
            CacheUtil.getCache().set(getCacheKey(id), tmp.selectById(id));
        }
    }
}
