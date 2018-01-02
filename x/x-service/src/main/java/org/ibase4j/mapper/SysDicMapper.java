package org.ibase4j.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import base.core.IBaseMapper;
import org.ibase4j.model.SysDic;

public interface SysDicMapper extends IBaseMapper<SysDic> {
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);
}