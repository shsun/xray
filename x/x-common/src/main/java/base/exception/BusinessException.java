package base.exception;

import base.HttpCode;

@SuppressWarnings("serial")
public class BusinessException extends BaseException {
    public BusinessException() {
    }

    public BusinessException(Throwable ex) {
        super(ex);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable ex) {
        super(message, ex);
    }

    protected HttpCode getHttpCode() {
        return HttpCode.CONFLICT;
    }
}