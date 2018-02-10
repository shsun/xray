package base.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.ibase4j.model.SysUser;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.plugins.Page;

import base.HttpCode;
import base.IConstants;
import base.exception.BaseException;
import base.exception.IllegalParameterException;
import base.utils.HttpServletRequestUtils;
import base.utils.InstanceUtil;
import base.utils.WebUtil;

public abstract class BaseController {
    protected final Logger logger = LogManager.getLogger(this.getClass());

    protected ModelAndView createModelAndView(String viewName, Map<String, ?> viewData) {
        viewData = viewData == null ? new HashMap<String, Object>() : viewData;
        // 第一个参数：指定页面要跳转的view视图路径;第二个参数：指定了要项前台传递的参数，在前台可以这样取值 ${sp_ids }
        ModelAndView mav = null;
        try {
            mav = new ModelAndView(viewName);
            if (viewData != null) {
                mav.addAllObjects(viewData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mav = null;
        }
        return mav;
    }

    /**
     * 获取当前用户Id
     */
    protected SysUser getCurrUser() {
        return WebUtil.getCurrentUser();
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        return setSuccessModelMap(request, response, map, null);
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(HttpServletRequest request, HttpServletResponse response, ModelMap map, Object data) {
        return setModelMap(request, response, map, HttpCode.OK, data);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(HttpServletRequest request, HttpServletResponse response, ModelMap map, HttpCode code) {
        return setModelMap(request, response, map, code, null);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(HttpServletRequest request, HttpServletResponse response, ModelMap map, HttpCode code, Object data) {
        Map<String, Object> tmp = InstanceUtil.newLinkedHashMap();
        tmp.putAll(map);
        map.clear();
        for (Iterator<String> iterator = tmp.keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();
            if (!key.startsWith("org.springframework.validation.BindingResult") && !key.equals("void")) {
                map.put(key, tmp.get(key));
            }
        }
        if (data != null) {
            if (data instanceof Page) {
                Page<?> page = (Page<?>) data;
                map.put("data", page.getRecords());
                map.put("current", page.getCurrent());
                map.put("size", page.getSize());
                map.put("pages", page.getPages());
                map.put("total", page.getTotal());
                map.put("iTotalRecords", page.getTotal());
                map.put("iTotalDisplayRecords", page.getTotal());
            } else if (data instanceof List<?>) {
                map.put("data", data);
                map.put("iTotalRecords", ((List<?>) data).size());
                map.put("iTotalDisplayRecords", ((List<?>) data).size());
            } else {
                map.put("data", data);
            }
        }
        map.put("httpCode", code.value());
        map.put("msg", code.msg());
        map.put("timestamp", System.currentTimeMillis());

        this.updateDebugInfo(request, map);

        return ResponseEntity.ok(map);
    }

    /**
     * 异常处理
     */
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws Exception {
        logger.error(IConstants.EXCEPTION_HEAD, exception);
        response.setContentType("application/json;charset=UTF-8");

        ModelMap map = new ModelMap();
        if (exception instanceof BaseException) {
            ((BaseException) exception).handler(map);
        } else if (exception instanceof IllegalArgumentException) {
            new IllegalParameterException(exception.getMessage()).handler(map);
        } else if (exception instanceof UnauthorizedException) {
            map.put("httpCode", HttpCode.FORBIDDEN.value());
            map.put("msg", StringUtils.defaultIfBlank(exception.getMessage(), HttpCode.FORBIDDEN.msg()));
        } else {
            String msg = StringUtils.defaultIfBlank(exception.getMessage(), HttpCode.INTERNAL_SERVER_ERROR.msg());
            map.put("httpCode", HttpCode.INTERNAL_SERVER_ERROR.value());
            map.put("msg", msg.length() > 100 ? "系统走神了,请稍候再试." : msg);
        }
        map.put("timestamp", System.currentTimeMillis());
        logger.info(JSON.toJSON(map));

        this.updateDebugInfo(request, map);

        byte[] bytes = JSON.toJSONBytes(map, SerializerFeature.DisableCircularReferenceDetect);
        response.getOutputStream().write(bytes);
    }

    private void updateDebugInfo(HttpServletRequest request, ModelMap map) {
        // if ("1".equals(request.getParameter("debug"))) {
        Map info = HttpServletRequestUtils.getRequestMap(request);
        map.put("__a__parameter", info);
        map.put("__a__requestURI", request.getRequestURI());
        map.put("__a__queryString", request.getQueryString());

        final long dateHeader = request.getDateHeader("");
        if (dateHeader > 0) {
            map.put("__a__cost", "" + (System.currentTimeMillis() - dateHeader));
        }

        // }
    }

}
