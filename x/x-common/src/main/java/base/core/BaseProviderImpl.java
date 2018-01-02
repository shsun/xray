package base.core;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ibase4j.core.IConstants;
import base.exception.BusinessException;
import org.ibase4j.core.util.ExceptionUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.reflectasm.MethodAccess;

public abstract class BaseProviderImpl implements ApplicationContextAware, IBaseProvider {
    protected static Logger logger = LogManager.getLogger();
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Parameter execute(Parameter p) {
        logger.info("请求：{}", JSON.toJSONString(p));
        Object service = applicationContext.getBean(p.getService());
        try {
            final long start = System.currentTimeMillis();
            Object arg = null;
            Object result = null;

            final Long id = p.getId();
            final BaseModel model = p.getModel();
            final List<?> list = p.getList();
            final Map<?, ?> map = p.getMap();

            MethodAccess access = MethodAccess.get(service.getClass());

            if (id != null) {
                arg = p.getId();
                result = access.invoke(service, p.getMethod(), arg);
            } else if (model != null) {
                arg = p.getModel();
                result = access.invoke(service, p.getMethod(), arg);
            } else if (list != null) {
                arg = p.getList();
                result = access.invoke(service, p.getMethod(), arg);
            } else if (map != null) {
                arg = p.getMap();
                result = access.invoke(service, p.getMethod(), arg);
            } else {
                arg = null;
                result = access.invoke(service, p.getMethod());
            }
            //
            final long end = System.currentTimeMillis();
			System.err.println("Dubbo Time: " + (end - start) + " ms, " + p.getService() + "." + p.getMethod() + " argument=" + arg);

            if (result != null) {
                Parameter response = new Parameter(result);
                logger.info("响应：{}", JSON.toJSONString(response));
                return response;
            }
            logger.info("空响应");
            return null;
        } catch (Exception e) {
            throw new BusinessException(IConstants.EXCEPTION_HEAD + ExceptionUtil.getStackTraceAsString(e), e);
        }

    }
}
