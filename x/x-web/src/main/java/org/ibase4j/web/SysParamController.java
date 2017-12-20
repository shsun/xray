package org.ibase4j.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.AbstractController;
import org.ibase4j.model.SysParam;
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
 * 参数管理
 */
@RestController
@Api(value = "系统参数管理", description = "系统参数管理")
@RequestMapping(value = "param")
public class SysParamController extends AbstractController<ISysProvider> {
	public String getService() {
		return "sysParamService";
	}

	@PutMapping(value = "/read/list")
	@ApiOperation(value = "查询系统参数")
	@RequiresPermissions("sys.base.param.read")
	public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody Map<String, Object> param) {
		return super.query(request, response, map, param);
	}

	@PutMapping(value = "/read/detail")
	@ApiOperation(value = "系统参数详情")
	@RequiresPermissions("sys.base.param.read")
	public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysParam param) {
		return super.get(request, response, map, param);
	}

	@PostMapping
	@ApiOperation(value = "修改系统参数")
	@RequiresPermissions("sys.base.param.update")
	public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysParam param) {
		return super.update(request, response, map, param);
	}

	@DeleteMapping
	@ApiOperation(value = "删除系统参数")
	@RequiresPermissions("sys.base.param.delete")
	public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysParam param) {
		return super.delete(request, response, map, param);
	}
}
