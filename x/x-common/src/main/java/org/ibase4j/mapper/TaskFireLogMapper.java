package org.ibase4j.mapper;

import java.util.List;
import java.util.Map;

import base.core.IBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.ibase4j.model.TaskFireLog;


public interface TaskFireLogMapper extends IBaseMapper<TaskFireLog> {
    List<Long> selectIdByMap(RowBounds rowBounds, @Param("cm") Map<String, Object> params);
}
