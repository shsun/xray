package io.kuaibao.provider.service.impl;

import org.springframework.stereotype.Service;

import io.kuaibao.provider.service.DService;

/**
 * Created by tianwei on 2017/1/21.
 */
@Service("d3")
public class DServiceImpl_3 implements DService {
    public String getUserName() {
        return "UserName(用户名)-本机, DServiceImpl_3 i am provider ??";
    }
}
