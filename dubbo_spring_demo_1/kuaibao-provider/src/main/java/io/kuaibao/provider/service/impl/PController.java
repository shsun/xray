package io.kuaibao.provider.service.impl;

import io.kuaibao.provider.service.CService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.kuaibao.provider.service.DService;

/**
 * Created by tianwei on 2017/1/21.
 */
@Controller
public class PController {

    @Autowired
//    @Qualifier("c")
    private CService service;

    @RequestMapping(value = "/test_p", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String test(){
        String userName = service.getUserName();
//        String userName = "p";
        return "test "+userName;
    }
}
