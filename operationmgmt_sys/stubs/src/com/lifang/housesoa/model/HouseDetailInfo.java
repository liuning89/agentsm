package com.lifang.housesoa.model;

import java.util.List;

public class HouseDetailInfo {
    private Integer houseId;
    private HouseSupporting houseSupporting;
    private List aImageList;
    private Integer orientation;
    private Integer renovation;
    private Integer floor;
    private String sellPoint;

    public HouseDetailInfo() {}
    public Integer getHouseId() { return houseId; }
    public void setHouseId(Integer houseId) { this.houseId = houseId; }
    public HouseSupporting getHouseSupporting() { return houseSupporting; }
    public void setHouseSupporting(HouseSupporting houseSupporting) { this.houseSupporting = houseSupporting; }
    public List getAImageList() { return aImageList; }
    public void setAImageList(List aImageList) { this.aImageList = aImageList; }
    public Integer getOrientation() { return orientation; }
    public void setOrientation(Integer orientation) { this.orientation = orientation; }
    public Integer getRenovation() { return renovation; }
    public void setRenovation(Integer renovation) { this.renovation = renovation; }
    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
    public String getSellPoint() { return sellPoint; }
    public void setSellPoint(String sellPoint) { this.sellPoint = sellPoint; }
}
