package com.lifang.agentsm.model;

import lombok.Data;
@Data
public class LfTownInfo {
	//houseId,a.publishAgent,a.pictureAgent,a.keyAgent,a.commAgent,a.maintainAgent
    private String townname	;//板块名称

    private String townid;//

    private String houseCount;//房源数量
    private String estateCount;//小区数量
   
	private String customerCount;//客源数量
	private String cusSeeCount;//带看
	
	private String storeNmae;//门店
    
}