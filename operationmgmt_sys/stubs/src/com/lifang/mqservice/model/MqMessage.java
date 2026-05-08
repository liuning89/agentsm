package com.lifang.mqservice.model;

public class MqMessage {
    private String msgTopic;
    private String msgType;
    private int ip;
    private String memo;

    public MqMessage() {}
    public String getMsgTopic() { return msgTopic; }
    public void setMsgTopic(String msgTopic) { this.msgTopic = msgTopic; }
    public String getMsgType() { return msgType; }
    public void setMsgType(String msgType) { this.msgType = msgType; }
    public int getIp() { return ip; }
    public void setIp(int ip) { this.ip = ip; }
    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }
}
