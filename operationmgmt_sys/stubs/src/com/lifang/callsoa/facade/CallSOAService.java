package com.lifang.callsoa.facade;

import com.lifang.callsoa.model.BindPhoneResponse;

public interface CallSOAService {
    BindPhoneResponse addBindPhoneRet(String mobile, int type);
    void delBindPhone(String mobile);
}
