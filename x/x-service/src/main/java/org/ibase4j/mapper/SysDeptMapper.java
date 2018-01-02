package org.ibase4j.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import base.core.IBaseMapper;
import org.ibase4j.model.SysDept;

public interface SysDeptMapper extends IBaseMapper<SysDept> {

	List<Long> selectIdPage(@Param("cm") SysDept sysDept);
}