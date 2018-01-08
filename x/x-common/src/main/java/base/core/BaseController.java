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
    protected Long getCurrUser() {
        return WebUtil.getCurrentUser();
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap map) {
        return setSuccessModelMap(map, null);
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap map, Object data) {
        return setModelMap(map, HttpCode.OK, data);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(ModelMap map, HttpCode code) {
        return setModelMap(map, code, null);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(ModelMap map, HttpCode code, Object data) {
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
        return ResponseEntity.ok(map);
    }

    /**
     * 异常处理
     */
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws Exception {
        logger.error(IConstants.EXCEPTION_HEAD, exception);
        response.setContentType("application/json;charset=UTF-8");

        ModelMap tmp = new ModelMap();
        if (exception instanceof BaseException) {
            ((BaseException) exception).handler(tmp);
        } else if (exception instanceof IllegalArgumentException) {
            new IllegalParameterException(exception.getMessage()).handler(tmp);
        } else if (exception instanceof UnauthorizedException) {
            tmp.put("httpCode", HttpCode.FORBIDDEN.value());
            tmp.put("msg", StringUtils.defaultIfBlank(exception.getMessage(), HttpCode.FORBIDDEN.msg()));
        } else {
            String msg = StringUtils.defaultIfBlank(exception.getMessage(), HttpCode.INTERNAL_SERVER_ERROR.msg());
            tmp.put("httpCode", HttpCode.INTERNAL_SERVER_ERROR.value());
            tmp.put("msg", msg.length() > 100 ? "系统走神了,请稍候再试." : msg);
        }
        tmp.put("timestamp", System.currentTimeMillis());
        logger.info(JSON.toJSON(tmp));

        byte[] bytes = JSON.toJSONBytes(tmp, SerializerFeature.DisableCircularReferenceDetect);
        response.getOutputStream().write(bytes);
    }
}
