package com.lifang.agentsoa.model;

public class PositionInfo {
    private Integer id;
    private String name;
    private Integer positionId;
    private Integer cityAreaOrgId;
    private Integer areaOrgId;
    private Integer doorAreaOrgId;
    private Integer storePosition;

    public PositionInfo() {}
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getPositionId() { return positionId; }
    public void setPositionId(Integer positionId) { this.positionId = positionId; }
    public Integer getCityAreaOrgId() { return cityAreaOrgId; }
    public void setCityAreaOrgId(Integer cityAreaOrgId) { this.cityAreaOrgId = cityAreaOrgId; }
    public Integer getAreaOrgId() { return areaOrgId; }
    public void setAreaOrgId(Integer areaOrgId) { this.areaOrgId = areaOrgId; }
    public Integer getDoorAreaOrgId() { return doorAreaOrgId; }
    public void setDoorAreaOrgId(Integer doorAreaOrgId) { this.doorAreaOrgId = doorAreaOrgId; }
    public Integer getStorePosition() { return storePosition; }
    public void setStorePosition(Integer storePosition) { this.storePosition = storePosition; }
}
