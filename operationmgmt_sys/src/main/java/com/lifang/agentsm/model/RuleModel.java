package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;



/**
 * Function: 跟进实体类
 *
 * @author   luogq
 * @Date	 2015年12月7日		下午8:02:40
 *
 * @see 	  
 */
@Data
public class RuleModel {
    
    private Integer storeId;
    private String mobile;
    private String houseSeeId;
    private String followUpId;
    private String cusGuestId;
    private String bidOrderId;
    private String customerId; 
    private String agentId;
    private String name;
    /**
     * gender:性别
     */
    private String gender;
    /**
     * source:来源
     */
    private String source;//
    private String isCallBackCus;
    /**
     * createTime:lf_customer表的创建时间
     */
    private Date createTime;
    /**
     * custime:cus_gust表的创建时间
     */
    private Date custime;
}
