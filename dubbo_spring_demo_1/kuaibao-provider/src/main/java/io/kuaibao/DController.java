package io.kuaibao;

import io.kuaibao.service.CService;
import io.kuaibao.service.DService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tianwei on 2017/1/21.
 */
@Controller
public class DController {

    @Autowired
    private CService service;

    @Autowired
    private DService myDService;

    @RequestMapping(value = "/test", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String test() {
        String userName = service.getUserName();
        return "test " + userName + "---" + myDService.getUserName();
    }
}
