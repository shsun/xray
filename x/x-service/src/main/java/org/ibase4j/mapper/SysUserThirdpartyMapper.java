package org.ibase4j.mapper;

import org.apache.ibatis.annotations.Param;
import org.ibase4j.core.base.IBaseMapper;
import org.ibase4j.model.SysUserThirdparty;

public interface SysUserThirdpartyMapper extends IBaseMapper<SysUserThirdparty> {
	Long queryUserIdByThirdParty(@Param("provider") String provider, @Param("openId") String openId);
}