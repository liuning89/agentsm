package com.lifang.mipushsoa.facade;

import java.util.List;

public interface MiPushSOAServer {
    void sendMessage(List<String> mobileList, String title, String message, String extra, int platform, int type, int code);
}
