package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;

@Data
public class HouseSellResource {
    private Integer id;

    private Integer houseId;

    private Integer estateId;

    private Integer subEstateId;

    private Integer buildingId;

    private Byte houseState;

    private Integer actionType;

    private Byte status;
    
    private Byte checkState;

    private Byte checkNum;

    private Double sellPrice;

    private Double unitPrice;

    private Integer source;

    private Integer renovation;

    private Byte isFiveYears;
    
    private Byte isOnlyOne;

    private Byte isLoan;

    private Byte propertyNum;

    private String des;

    private Integer seeHouseNum;
    
    private Date publishDate;

    private Date resultDate;
    
    private Date loopTime;
    
    private Date sellTime;

    private Date createtime;
    
    private Date updatetime;
    
    private String isSubWay;
    
    private String houseResourceStatus;
    private String failReason;
    private String addOrUpdate;
    private Integer constructionDate;
    private String isShow;
    
    private Integer sourceId;
    
    private Integer publicId;                                                           
    private Integer currentStatus	;                                                    
                                                           
                                                            
                                                               
    private Integer carSpace	;                                                        
    private Integer isHaveKey	;                                                       
    private Integer lookAnytime	;                                                        
    private Integer property	;                                                     
    private Integer setsList	;                                                        
                                                                   
    private Integer  note	;                                                           
                                                      
    private Integer  sellPoint	;                                                        
    					
    
    
}