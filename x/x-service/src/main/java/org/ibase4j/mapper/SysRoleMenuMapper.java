package org.ibase4j.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import base.core.IBaseMapper;
import org.ibase4j.model.SysRoleMenu;

public interface SysRoleMenuMapper extends IBaseMapper<SysRoleMenu> {
	List<Long> queryMenuIdsByRoleId(@Param("roleId") Long roleId);

	List<Long> queryPermissions(@Param("roleId") Long roleId, @Param("permission") String permission);

	List<String> queryPermission(@Param("roleId") Long id);
}
