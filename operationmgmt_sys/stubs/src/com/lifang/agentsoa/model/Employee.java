package com.lifang.agentsoa.model;

import java.util.List;

public class Employee {
    private Integer id;
    private String name;
    private String mobile;
    private Integer cityId;
    private Integer areaId;
    private Integer storeId;
    private Integer status;
    private String workNumber;
    private String empCode;
    private Integer franchiseeId;
    private List<String> codes;

    public Employee() {}
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public Integer getCityId() { return cityId; }
    public void setCityId(Integer cityId) { this.cityId = cityId; }
    public Integer getAreaId() { return areaId; }
    public void setAreaId(Integer areaId) { this.areaId = areaId; }
    public Integer getStoreId() { return storeId; }
    public void setStoreId(Integer storeId) { this.storeId = storeId; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getWorkNumber() { return workNumber; }
    public void setWorkNumber(String workNumber) { this.workNumber = workNumber; }
    public String getEmpCode() { return empCode; }
    public void setEmpCode(String empCode) { this.empCode = empCode; }
    public Integer getFranchiseeId() { return franchiseeId; }
    public void setFranchiseeId(Integer franchiseeId) { this.franchiseeId = franchiseeId; }
    public List<String> getCodes() { return codes; }
    public void setCodes(List<String> codes) { this.codes = codes; }
}
