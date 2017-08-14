package com.lifang.agentsm.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class LfHouse {
	private String houseSupporting;
	private Integer orientation;
	private Integer floor;
	private Integer htype;
	private Integer houseChildType;
	private Integer renovation;
	private Integer completed;
	private Integer property;
	private Integer carSpace;
	private String sellPoint;
	private Integer source;
	private Integer isHaveKey;
	private Integer houseId;
	private String isOnlyOne;
	private String isFiveYears;
	private BigDecimal spaceArea;
	private BigDecimal sellPrice;
	private BigDecimal gefuPrice;
}
