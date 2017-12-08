package io.kuaibao;


import io.kuaibao.service.CService;

/**
 * Created by shsun on 12/8/17.
 */
//@Service("c")
public class CServiceImpl implements CService {
    public String getUserName() {
        return "UserName(用户名)-本机, i am consumer ??";
    }
}
