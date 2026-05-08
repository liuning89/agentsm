package com.lifang.sms.model;

public class SmsBusinessResponse {
    private int status;
    private String message;

    public SmsBusinessResponse() {}
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
