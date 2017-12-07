package org.ibase4j.core.base;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.Constants;
import org.ibase4j.core.exception.BusinessException;
import org.ibase4j.core.util.ExceptionUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.reflectasm.MethodAccess;

/**
 *
 */
public abstract class BaseProviderImpl implements ApplicationContextAware, BaseProvider {

    protected static Logger logger = LogManager.getLogger();

    /**
     *
     */
    private ApplicationContext applicationContext;

    /**
     *
     * @param applicationContext
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     *
     * @param p
     * @return
     */
    public Parameter execute(Parameter p) {
        logger.info("请求：{}", JSON.toJSONString(p));
        Object service = applicationContext.getBean(p.getService());
        try {
            Long id = p.getId();
            BaseModel model = p.getModel();
            List<?> list = p.getList();
            Map<?, ?> map = p.getMap();

            Object result = null;

			MethodAccess ma = MethodAccess.get(service.getClass());
            if (id != null) {
                result = ma.invoke(service, p.getMethod(), p.getId());
            } else if (model != null) {
                result = ma.invoke(service, p.getMethod(), p.getModel());
            } else if (list != null) {
                result = ma.invoke(service, p.getMethod(), p.getList());
            } else if (map != null) {
                result = ma.invoke(service, p.getMethod(), p.getMap());
            } else {
                result = ma.invoke(service, p.getMethod());
            }
            if (result != null) {
                Parameter response = new Parameter(result);
                logger.info("响应：{}", JSON.toJSONString(response));
                return response;
            }
            logger.info("空响应");
            return null;
        } catch (Exception e) {
            throw new BusinessException(Constants.EXCEPTION_HEAD + ExceptionUtil.getStackTraceAsString(e), e);
        }
    }
}
