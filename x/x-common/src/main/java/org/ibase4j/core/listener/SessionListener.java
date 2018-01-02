package org.ibase4j.core.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import base.IConstants;
import org.ibase4j.core.util.CacheUtil;

public class SessionListener implements HttpSessionListener {
    private Logger logger = LogManager.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        session.setAttribute(IConstants.WEBTHEME, "default");
        logger.info("创建了一个Session连接:[" + session.getId() + "]");
        setAllUserNumber(1);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        if (getAllUserNumber() > 0) {
            logger.info("销毁了一个Session连接:[" + session.getId() + "]");
        }
        session.removeAttribute(IConstants.CURRENT_USER);
        setAllUserNumber(-1);
    }

    private void setAllUserNumber(int n) {
        Integer number = getAllUserNumber() + n;
        if (number >= 0) {
            logger.info("用户数：" + number);
            CacheUtil.getCache().set(IConstants.ALLUSER_NUMBER, number, 60 * 60 * 24);
        }
    }

    /**
     * 获取在线用户数量
     */
    public static Integer getAllUserNumber() {
        Integer v = (Integer) CacheUtil.getCache().get(IConstants.ALLUSER_NUMBER);
        if (v != null) {
            return v;
        }
        return 0;
    }
}
