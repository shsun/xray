package org.ibase4j.core;

import javax.servlet.ServletContextEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import base.listener.XServerListener;
import org.ibase4j.service.SysDicService;
import org.ibase4j.service.SysUserService;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SysServerListener extends XServerListener {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * @param contextEvent
     */
    public void contextInitialized(ServletContextEvent contextEvent) {

        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

        SysUserService sysUserService = context.getBean(SysUserService.class);
        sysUserService.init();

        SysDicService sysDicService = context.getBean(SysDicService.class);
        sysDicService.getAllDic();
        //
        super.contextInitialized(contextEvent);
    }
}