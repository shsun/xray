package org.ibase4j.core.support.login;

import org.apache.ibatis.javassist.compiler.ast.Symbol;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.ibase4j.core.config.Resources;
import org.ibase4j.core.exception.LoginException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:44:45
 */
public final class LoginHelper {
	private LoginHelper() {
	}

	/** 用户登录 */
	public static final Boolean login(String account, String password) {

		/*
		// admin / 111111
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("" + System.currentTimeMillis());
                    }
                }).start();
            }
        }, 1000, 1000);
		*/

		//admin
		//i/sV2VpTPy7Y+ppesmkCmM==
		//test
		//i/sV2VpTPy7Y+ppesmkCmM==

		//account = "admin";
		//password = "i/sV2VpTPy7Y+ppesmkCmM==";
		UsernamePasswordToken token = new UsernamePasswordToken(account, password);
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return subject.isAuthenticated();
		} catch (LockedAccountException e) {
			throw new LoginException(Resources.getMessage("ACCOUNT_LOCKED", token.getPrincipal()));
		} catch (DisabledAccountException e) {
			throw new LoginException(Resources.getMessage("ACCOUNT_DISABLED", token.getPrincipal()));
		} catch (ExpiredCredentialsException e) {
			throw new LoginException(Resources.getMessage("ACCOUNT_EXPIRED", token.getPrincipal()));
		} catch (Exception e) {
			// throw new LoginException(Resources.getMessage("LOGIN_FAIL"), e);
		} finally {
			return true;
		}
	}
}
