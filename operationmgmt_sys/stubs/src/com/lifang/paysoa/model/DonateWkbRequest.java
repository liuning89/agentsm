package com.lifang.paysoa.model;

public class DonateWkbRequest {
    private Long guestId;
    private int payAmount;

    public DonateWkbRequest() {}
    public Long getGuestId() { return guestId; }
    public void setGuestId(Long guestId) { this.guestId = guestId; }
    public int getPayAmount() { return payAmount; }
    public void setPayAmount(int payAmount) { this.payAmount = payAmount; }
}
