package com.leo.jaxrs.fault;

import java.util.Locale;

public class LeoFault extends RuntimeException {
    public static final int SYSTEM_ERROR = 500;

    private int errorCode;
    private String errorMessage;
    private Locale locale;

    public LeoFault() { super(); }
    public LeoFault(int errorCode) { super(); this.errorCode = errorCode; }
    public LeoFault(String message) { super(message); }
    public LeoFault(int errorCode, String message) { super(message); this.errorCode = errorCode; }

    public int getErrorCode() { return errorCode; }
    public void setErrorCode(int errorCode) { this.errorCode = errorCode; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public Locale getLocale() { return locale; }
    public void setLocale(Locale locale) { this.locale = locale; }
}
