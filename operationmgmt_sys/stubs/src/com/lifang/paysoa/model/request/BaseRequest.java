package com.lifang.paysoa.model.request;

import com.lifang.paysoa.common.enums.PaySide;

public class BaseRequest {
    private Long guestId;
    private PaySide paySide;

    public BaseRequest() {}
    public Long getGuestId() { return guestId; }
    public void setGuestId(Long guestId) { this.guestId = guestId; }
    public PaySide getPaySide() { return paySide; }
    public void setPaySide(PaySide paySide) { this.paySide = paySide; }
}
