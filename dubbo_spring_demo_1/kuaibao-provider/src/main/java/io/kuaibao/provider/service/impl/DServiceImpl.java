package io.kuaibao.provider.service.impl;

import io.kuaibao.provider.service.DService;

/**
 * Created by tianwei on 2017/1/21.
 */
//@Service("d")
public class DServiceImpl implements DService {
    public String getUserName() {
        return "UserName(用户名)-本机, i am provider ??";
    }
}
