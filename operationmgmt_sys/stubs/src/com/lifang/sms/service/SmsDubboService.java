package com.lifang.sms.service;

import com.lifang.sms.model.SmsSendRequest;
import com.lifang.sms.model.SmsBusinessResponse;

public interface SmsDubboService {
    void sendSms(SmsSendRequest request);
    SmsBusinessResponse sendBusiness(SmsSendRequest request);
    SmsBusinessResponse sendBusiness(String mobile, int msgSource, String modelId, String param);
}
