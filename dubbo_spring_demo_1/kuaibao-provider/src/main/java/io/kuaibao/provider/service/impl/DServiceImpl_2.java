package io.kuaibao.provider.service.impl;

import io.kuaibao.provider.service.DService;
import org.springframework.stereotype.Service;

/**
 * Created by tianwei on 2017/1/21.
 */
//@Service("d2")
public class DServiceImpl_2 implements DService {
    public String getUserName() {
        return "UserName(用户名)-本机, DServiceImpl_2 i am provider ??";
    }
}
