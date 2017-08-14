package com.lifang.agentsm.exception;

/**
 * 业务异常
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -342132296305339699L;

    private String            code;

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public BizException(String msg) {
        super(msg);
        this.code = "BizException";
    }

    public String getCode() {
        return code;
    }

}
