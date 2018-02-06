package org.ibase4j.core;

import org.ibase4j.provider.ISysProvider;

import com.alibaba.dubbo.config.annotation.Service;

import base.core.BaseProviderImpl;

//@Component
//@Service(protocol = { "dubbo" }, interfaceClass = ISysProvider.class)
@Service(interfaceClass = ISysProvider.class)
public class SysProviderImpl extends BaseProviderImpl implements ISysProvider {

    public SysProviderImpl() {
        super();
    }

}
