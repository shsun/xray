package org.ibase4j.core;

import org.ibase4j.core.base.BaseProviderImpl;

import com.alibaba.dubbo.config.annotation.Service;

//@Component
//@Service(protocol = { "dubbo" }, interfaceClass = ISysProvider.class)
@Service(interfaceClass = ISysProvider.class)
public class SysProviderImpl extends BaseProviderImpl implements org.ibase4j.core.base.BaseProvider {

    public SysProviderImpl() {
        super();
    }

}
