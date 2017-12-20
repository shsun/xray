package org.ibase4j.core.exception;

import org.ibase4j.core.support.HttpCode;

@SuppressWarnings("serial")
public class IllegalParameterException extends BaseException {
    public IllegalParameterException() {
    }

    public IllegalParameterException(Throwable ex) {
        super(ex);
    }

    public IllegalParameterException(String message) {
        super(message);
    }

    public IllegalParameterException(String message, Throwable ex) {
        super(message, ex);
    }

    protected HttpCode getHttpCode() {
        return HttpCode.BAD_REQUEST;
    }
}
