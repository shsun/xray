package io.kuaibao;


import io.kuaibao.service.*;

/**
 * Created by shsun on 12/8/17.
 */
public class MyDServiceImpl implements DService {
    public String getUserName() {
        return "module=consumer, c=MyDServiceImpl i=CService";
    }

}
