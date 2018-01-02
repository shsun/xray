package base.exception;

import org.apache.commons.lang3.StringUtils;
import org.ibase4j.core.support.HttpCode;
import org.springframework.ui.ModelMap;

@SuppressWarnings("serial")
public abstract class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(Throwable ex) {
        super(ex);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable ex) {
        super(message, ex);
    }

    public void handler(ModelMap map) {
        String msg = StringUtils.isNotBlank(getMessage()) ? getMessage() : getHttpCode().msg();
        map.put("httpCode", getHttpCode().value());
        map.put("msg", msg);
        map.put("timestamp", System.currentTimeMillis());
    }

    protected abstract HttpCode getHttpCode();
}
