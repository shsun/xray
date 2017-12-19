package org.ibase4j.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ibase4j.core.base.AbstractController;
import org.ibase4j.model.SysNews;
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
 * 新闻管理控制类
 * 
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:13:31
 */
@RestController
@Api(value = "新闻管理", description = "新闻管理")
@RequestMapping(value = "news")
public class SysNewsController extends AbstractController<ISysProvider> {
	public String getService() {
		return "sysNewsService";
	}

	@ApiOperation(value = "查询新闻")
	@RequiresPermissions("public.news.read")
	@PutMapping(value = "/read/list")
	public Object query(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody Map<String, Object> param) {
		return super.query(request, response, map, param);
	}

	@ApiOperation(value = "新闻详情")
	@RequiresPermissions("public.news.read")
	@PutMapping(value = "/read/detail")
	public Object get(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysNews param) {
		Object o = super.get(request, response, map, param);
		return o;
	}

	@PostMapping
	@ApiOperation(value = "修改新闻")
	@RequiresPermissions("public.news.update")
	public Object update(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysNews param) {
		Object o = super.update(request, response, map, param);
		return o;
	}

	@DeleteMapping
	@ApiOperation(value = "删除新闻")
	@RequiresPermissions("public.news.delete")
	public Object delete(HttpServletRequest request, HttpServletResponse response, ModelMap map, @RequestBody SysNews param) {
		Object o = super.delete(request, response, map, param);
		return o;
	}
}
