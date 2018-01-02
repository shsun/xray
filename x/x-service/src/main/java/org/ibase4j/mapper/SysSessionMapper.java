package org.ibase4j.mapper;

import java.util.List;

import base.core.IBaseMapper;
import org.ibase4j.model.SysSession;

public interface SysSessionMapper extends IBaseMapper<SysSession> {

    void deleteBySessionId(String sessionId);

    Long queryBySessionId(String sessionId);

    List<String> querySessionIdByAccount(String account);
}