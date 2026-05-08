package com.lifang.model;

public class PageResponse<T> extends Response<T> {
    private int total;

    public PageResponse() { super(); }
    public PageResponse(String message, int status) { super(message, status); }
    public PageResponse(String message, int status, T data) { super(message, status, data); }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
}
