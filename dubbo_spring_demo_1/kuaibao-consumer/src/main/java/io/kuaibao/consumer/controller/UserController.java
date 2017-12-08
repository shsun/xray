package io.kuaibao.consumer.controller;

import io.kuaibao.provider.service.DService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    //@Qualifier("d2")
    private DService service;

    @RequestMapping(value = "/test", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String test() {
        String userName = service.getUserName();
        return "test " + userName;
    }
}
