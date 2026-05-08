package com.lifang.hr.exception;

public class BusinessException extends RuntimeException {
    public BusinessException() { super(); }
    public BusinessException(String message) { super(message); }
    public BusinessException(String message, Throwable cause) { super(message, cause); }
}
