package com.lifang.paysoa.model.vo;

public class GuestBalanceVo {
    private Long guestId;
    private long totalBalance;

    public GuestBalanceVo() {}
    public Long getGuestId() { return guestId; }
    public void setGuestId(Long guestId) { this.guestId = guestId; }
    public long getTotalBalance() { return totalBalance; }
    public void setTotalBalance(long totalBalance) { this.totalBalance = totalBalance; }
}
