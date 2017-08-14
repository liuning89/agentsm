package com.lifang.agentsm.model;

import lombok.Data;
@Data
public class LfFranchiseeStore {
    private Long id;

    private String name;//门店

    private String chartername;//营业执照名称

    private String storeaddress;//门店地址
    
    private String townid;//区域
    
    private String code;//门店code
    
    private Integer franchiseeId;//合作伙伴ID
    
    /**
     * areaId:区域ID
     */
    private Integer areaId;
    
    /**
     * storeId:门店表的ID
     */
    private Integer storeId;
  
    /**
     * storeCode:来自组织架构里面的门店id
     */
    private Integer storeCode;
}