package com.lifang.housesoa.model;

import java.math.BigDecimal;

public class HouseInfoRequest {
    private Integer houseId;
    private HouseSupporting houseSupporting;
    private Integer carSpace;
    private String completed;
    private Integer floor;
    private Integer htype;
    private Integer isHaveKey;
    private Integer orientation;
    private Integer property;
    private Integer renovation;
    private String sellPoint;
    private Integer source;
    private BigDecimal spaceArea;
    private BigDecimal sellPrice;
    private BigDecimal gefuPrice;
    private Integer houseChildType;
    private Integer isFiveYears;
    private Integer isOnlyOne;

    public HouseInfoRequest() {}
    public Integer getHouseId() { return houseId; }
    public void setHouseId(Integer houseId) { this.houseId = houseId; }
    public HouseSupporting getHouseSupporting() { return houseSupporting; }
    public void setHouseSupporting(HouseSupporting houseSupporting) { this.houseSupporting = houseSupporting; }
    public Integer getCarSpace() { return carSpace; }
    public void setCarSpace(Integer carSpace) { this.carSpace = carSpace; }
    public String getCompleted() { return completed; }
    public void setCompleted(String completed) { this.completed = completed; }
    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
    public Integer getHtype() { return htype; }
    public void setHtype(Integer htype) { this.htype = htype; }
    public Integer getIsHaveKey() { return isHaveKey; }
    public void setIsHaveKey(Integer isHaveKey) { this.isHaveKey = isHaveKey; }
    public Integer getOrientation() { return orientation; }
    public void setOrientation(Integer orientation) { this.orientation = orientation; }
    public Integer getProperty() { return property; }
    public void setProperty(Integer property) { this.property = property; }
    public Integer getRenovation() { return renovation; }
    public void setRenovation(Integer renovation) { this.renovation = renovation; }
    public String getSellPoint() { return sellPoint; }
    public void setSellPoint(String sellPoint) { this.sellPoint = sellPoint; }
    public Integer getSource() { return source; }
    public void setSource(Integer source) { this.source = source; }
    public BigDecimal getSpaceArea() { return spaceArea; }
    public void setSpaceArea(BigDecimal spaceArea) { this.spaceArea = spaceArea; }
    public BigDecimal getSellPrice() { return sellPrice; }
    public void setSellPrice(BigDecimal sellPrice) { this.sellPrice = sellPrice; }
    public BigDecimal getGefuPrice() { return gefuPrice; }
    public void setGefuPrice(BigDecimal gefuPrice) { this.gefuPrice = gefuPrice; }
    public Integer getHouseChildType() { return houseChildType; }
    public void setHouseChildType(Integer houseChildType) { this.houseChildType = houseChildType; }
    public Integer getIsFiveYears() { return isFiveYears; }
    public void setIsFiveYears(Integer isFiveYears) { this.isFiveYears = isFiveYears; }
    public Integer getIsOnlyOne() { return isOnlyOne; }
    public void setIsOnlyOne(Integer isOnlyOne) { this.isOnlyOne = isOnlyOne; }
}
