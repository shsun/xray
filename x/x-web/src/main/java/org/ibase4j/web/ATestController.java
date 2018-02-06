package org.ibase4j.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ibase4j.model.TaskFireLog;
import org.ibase4j.service.StoredCardInventoryServiceImpl;
import org.ibase4j.service.TaskFireLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import base.HttpCode;
import base.core.BaseController;

@Controller
@RequestMapping("/test")
public class ATestController extends BaseController {

    @Autowired
    TaskFireLogService taskFireLogService;

    @Autowired
    StoredCardInventoryServiceImpl storedCardInventoryService;

    @RequestMapping("/test_1")
    @ResponseBody
    public Object test_1(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        request.getRequestURI();
        request.getQueryString();

        TaskFireLog log = taskFireLogService.queryById(1L);
        System.out.println("done");
        String s = log != null ? log.toString() : "no-data";

        storedCardInventoryService.sayhi();

        return setModelMap(modelMap, HttpCode.OK, log);

    }
}
