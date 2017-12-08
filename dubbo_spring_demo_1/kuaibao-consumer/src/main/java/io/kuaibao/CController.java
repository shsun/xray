package io.kuaibao;

import io.kuaibao.service.DService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CController {

    @Autowired
    @Qualifier("dService")
    private DService service;

    @Autowired
    @Qualifier("dService_random")
    private DService dService_random;

    @RequestMapping(value = "/test", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String test() {
        String userName = service.getUserName();

        return "test " + userName + "----" + dService_random.getUserName();
    }
}
