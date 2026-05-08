package com.lifang.mqservice.client;

import com.lifang.mqservice.model.MqMessage;

public class MsgQueueSenderClient {
    private String app;
    private String servAddr;
    private String timeout;

    public void setApp(String app) { this.app = app; }
    public void setServAddr(String servAddr) { this.servAddr = servAddr; }
    public void setTimeout(String timeout) { this.timeout = timeout; }

    public void sendMessage(MqMessage msg) {}
}
