package org.ibase4j.core;

import base.core.BaseProviderImpl;

import com.alibaba.dubbo.config.annotation.Service;
import org.ibase4j.provider.ISysProvider;

//@Component
//@Service(protocol = { "dubbo" }, interfaceClass = ISysProvider.class)
@Service(interfaceClass = ISysProvider.class)
public class SysProviderImpl extends BaseProviderImpl implements ISysProvider {

    public SysProviderImpl() {
        super();
    }

}
