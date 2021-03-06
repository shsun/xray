package org.ibase4j.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import base.core.IBaseMapper;
import org.ibase4j.model.SysUserMenu;

public interface SysUserMenuMapper extends IBaseMapper<SysUserMenu> {
	List<Long> queryMenuIdsByUserId(@Param("userId") Long userId);

	List<Long> queryPermissions(@Param("userId") Long userId, @Param("permission") String permission);

	List<String> queryPermission(@Param("userId") Long id);
}