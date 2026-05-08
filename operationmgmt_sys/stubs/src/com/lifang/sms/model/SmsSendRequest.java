package com.lifang.sms.model;

public class SmsSendRequest {
    private String mobile;
    private int msgSource;
    private String modelId;
    private String param;

    public SmsSendRequest() {}
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public int getMsgSource() { return msgSource; }
    public void setMsgSource(int msgSource) { this.msgSource = msgSource; }
    public String getModelId() { return modelId; }
    public void setModelId(String modelId) { this.modelId = modelId; }
    public String getParam() { return param; }
    public void setParam(String param) { this.param = param; }
}
