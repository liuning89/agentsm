package com.lifang.bzsm.console.entity;

import java.math.BigDecimal;
import java.util.Date;

public class LfCustomerRequirement {
    private Long id;

    private Long cusId;

    private Long agentId;

    private Byte hType;

    private Byte houseChildType;

    private Long subEstateId;

    private Integer townId;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private BigDecimal minFloorage;

    private BigDecimal maxFloorage;

    private Integer bedRoomSum;

    private Integer livingRoomSum;

    private Integer wcSum;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private String tempHouseType;

    private String tempHouseChildType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCusId() {
        return cusId;
    }

    public void setCusId(Long cusId) {
        this.cusId = cusId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Byte gethType() {
        return hType;
    }

    public void sethType(Byte hType) {
        this.hType = hType;
    }

    public Byte getHouseChildType() {
        return houseChildType;
    }

    public void setHouseChildType(Byte houseChildType) {
        this.houseChildType = houseChildType;
    }

    public Long getSubEstateId() {
        return subEstateId;
    }

    public void setSubEstateId(Long subEstateId) {
        this.subEstateId = subEstateId;
    }

    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinFloorage() {
        return minFloorage;
    }

    public void setMinFloorage(BigDecimal minFloorage) {
        this.minFloorage = minFloorage;
    }

    public BigDecimal getMaxFloorage() {
        return maxFloorage;
    }

    public void setMaxFloorage(BigDecimal maxFloorage) {
        this.maxFloorage = maxFloorage;
    }

    public Integer getBedRoomSum() {
        return bedRoomSum;
    }

    public void setBedRoomSum(Integer bedRoomSum) {
        this.bedRoomSum = bedRoomSum;
    }

    public Integer getLivingRoomSum() {
        return livingRoomSum;
    }

    public void setLivingRoomSum(Integer livingRoomSum) {
        this.livingRoomSum = livingRoomSum;
    }

    public Integer getWcSum() {
        return wcSum;
    }

    public void setWcSum(Integer wcSum) {
        this.wcSum = wcSum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getTempHouseType() {
        return tempHouseType;
    }

    public void setTempHouseType(String tempHouseType) {
        this.tempHouseType = tempHouseType;
    }

    public String getTempHouseChildType() {
        return tempHouseChildType;
    }

    public void setTempHouseChildType(String tempHouseChildType) {
        this.tempHouseChildType = tempHouseChildType;
    }
}