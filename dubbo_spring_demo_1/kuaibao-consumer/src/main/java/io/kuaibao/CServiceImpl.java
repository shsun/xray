package io.kuaibao;


import io.kuaibao.service.*;

/**
 * Created by shsun on 12/8/17.
 */
public class CServiceImpl implements CService {
    public String getUserName() {
        return "module=consumer, c=CServiceImpl i=CService";
    }
}
