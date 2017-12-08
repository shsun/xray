package io.kuaibao;

import io.kuaibao.service.DService;

/**
 * Created by tianwei on 2017/1/21.
 */
//@Service("d1")
public class DServiceImpl_1 implements DService {
    public String getUserName() {
        return "UserName(用户名)-本机, DServiceImpl_1 i am provider ??";
    }
}
