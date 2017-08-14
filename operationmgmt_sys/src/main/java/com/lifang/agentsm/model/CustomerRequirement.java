package com.lifang.agentsm.model;
import lombok.Data;
@Data
public class CustomerRequirement {
	private Double minPrice;//最小面积
	private Double maxPrice;//最大面积
	private int bedRoomSum;// 卧室,
	private Integer livingRoomSum;//客厅 
	private Integer wcSum;// 卫生间 
	private Double minFloorage;// 最小价格  
	private Double maxFloorage;//最大价格
	private String customerReq;//整合字段
}