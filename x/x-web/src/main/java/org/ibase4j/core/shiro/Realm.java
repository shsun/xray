package org.ibase4j.core.shiro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import base.core.IBaseProvider;
import base.core.Parameter;
import base.utils.WebUtil;
import org.ibase4j.model.SysSession;
import org.ibase4j.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

import com.baomidou.mybatisplus.plugins.Page;

public class Realm extends AuthorizingRealm {
    private final Logger logger = LogManager.getLogger();

    @Autowired
    @Qualifier("sysProvider")
    protected IBaseProvider provider;

    @Autowired
    private RedisOperationsSessionRepository sessionRepository;

    /**
     * 权限, this method will be invoked by the shiro framework
     * 
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Long userId = WebUtil.getCurrentUser();

        Parameter parameter = new Parameter("sysAuthorizeService", "queryPermissionByUserId").setId(userId);
        List<?> list = provider.execute(parameter).getList();
        for (Object permission : list) {
            if (StringUtils.isNotBlank((String) permission)) {
                // 添加基于Permission的权限信息
                info.addStringPermission((String) permission);
            }
        }
        // 添加用户权限
        info.addStringPermission("user");
        return info;
    }

    /**
     * 登录验证, this method will be invoked by the shiro framework
     *
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        AuthenticationInfo authcInfo = null;
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("countSql", 0);
        p.put("enable", 1);
        p.put("account", token.getUsername());
        Parameter parameter = new Parameter("sysUserService", "query").setMap(p);
        Page<?> pageInfo = provider.execute(parameter).getPage();
        if (pageInfo.getTotal() == 1) {
            SysUser user = (SysUser) pageInfo.getRecords().get(0);
            StringBuilder sb = new StringBuilder(100);
            for (int i = 0; i < token.getPassword().length; i++) {
                sb.append(token.getPassword()[i]);
            }
            if (user.getPassword().equals(sb.toString())) {
                WebUtil.saveCurrentUser(user.getId());
                saveSession(user.getAccount());

                authcInfo = new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), user.getUserName());
            } else {
                logger.warn("USER [{}] PASSWORD IS WRONG: {}", token.getUsername(), sb.toString());
            }
        } else {
            logger.warn("No user: {}", token.getUsername());
        }
        return authcInfo;
    }

    private void saveSession(String account) {
        SysSession newSession = new SysSession();
        Session oldSession = SecurityUtils.getSubject().getSession();

        // 踢出用户
        newSession.setAccount(account);
        Parameter parameter = new Parameter("sysSessionService", "querySessionIdByAccount").setModel(newSession);
        List<?> sessionIds = provider.execute(parameter).getList();
        if (sessionIds != null) {
            for (Object id : sessionIds) {
                // delete from DB
                newSession.setSessionId((String) id);
                parameter = new Parameter("sysSessionService", "deleteBySessionId").setModel(newSession);
                provider.execute(parameter);
                // delete from redis
                sessionRepository.delete((String) id);
                sessionRepository.cleanupExpiredSessions();
            }
        }

        // update session to DB
        newSession.setSessionId(oldSession.getId().toString());
        final String tmpHost = (String) oldSession.getAttribute("HOST");
        newSession.setIp(StringUtils.isBlank(tmpHost) ? oldSession.getHost() : tmpHost);
        newSession.setStartTime(oldSession.getStartTimestamp());
        parameter = new Parameter("sysSessionService", "update").setModel(newSession);
        provider.execute(parameter);
        // see common-ac-session.xml in common project
        // <bean class="org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration">
        // RedisHttpSessionConfiguration will save session to redis
    }
}
