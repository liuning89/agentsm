package com.lifang.sso.entity;

public class SsoUserPositionOrgRelate {
    private Integer id;
    private Integer cityId;
    private Integer areaId;
    private Integer storeId;

    public SsoUserPositionOrgRelate() {}
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getCityId() { return cityId; }
    public void setCityId(Integer cityId) { this.cityId = cityId; }
    public Integer getAreaId() { return areaId; }
    public void setAreaId(Integer areaId) { this.areaId = areaId; }
    public Integer getStoreId() { return storeId; }
    public void setStoreId(Integer storeId) { this.storeId = storeId; }
}
