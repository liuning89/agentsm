package com.lifang.sso.entity;

public class SsoUser {
    private Integer id;
    private String name;
    private String mobile;
    private Integer status;
    private String workNumber;

    public SsoUser() {}
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getWorkNumber() { return workNumber; }
    public void setWorkNumber(String workNumber) { this.workNumber = workNumber; }
}
