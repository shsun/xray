package org.ibase4j.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import base.core.AbstractMSAController;
import org.ibase4j.model.SysUnit;
import org.ibase4j.provider.ISysProvider;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@RestController
@Api(value = "单位管理", description = "单位管理")
@RequestMapping(value = "unit")
public class SysUnitController extends AbstractMSAController<ISysProvider> {
	public String getService() {
		return "sysUnitService";
	}

	@ApiOperation(value = "查询单位")
	@RequiresPermissions("sys.base.unit.read")
	@PutMapping(value = "/read/list")
	public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody Map<String, Object> param) {
		return super.query(request, response, map, param);
	}

	@ApiOperation(value = "单位详情")
	@RequiresPermissions("sys.base.unit.read")
	@PutMapping(value = "/read/detail")
	public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUnit param) {
		Object o = super.get(request, response, map, param);
		return o;
	}

	@PostMapping
	@ApiOperation(value = "修改单位")
	@RequiresPermissions("sys.base.unit.update")
	public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUnit param) {
		Object o = super.update(request, response, map, param);
		return o;
	}

	@DeleteMapping
	@ApiOperation(value = "删除单位")
	@RequiresPermissions("sys.base.unit.delete")
	public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysUnit param) {
		Object o = super.delete(request, response, map, param);
		return o;
	}
}
