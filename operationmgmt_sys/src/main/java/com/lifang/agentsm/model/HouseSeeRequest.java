package com.lifang.agentsm.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;


/**
 * Function: 房源带看请求实体
 *
 * @author   admin
 * @Date	 2015年4月1日		上午11:22:04
 *
 * @see 	  
 */
@Data
public class HouseSeeRequest {
	private Long id;
    private Long agentId;
    private Long houseId;
    private String mobile;
    private Byte gender;
    /**
     * customerName:客户姓名
     */
    private String customerName;
    /**
     * seeTime:带看时间
     */
    private Date seeTime;
    /**
     * agreement:带看协议附件
     */
    @JSONField(serialize =false)
    private byte[] agreement;
    /**
     * agreementNo:协议编号
     */
    private String agreementNo;
    /**
     * agreementKey:带看协议；oss服务器key
     */
    private String agreementKey;
    /**
     * customerId:客户ID
     */
    private Long customerId;
    /**
     * status:状态
     */
    private Byte status;
    
    private Integer addType; //新增类型 0.手动添加  1.自动添加
    
    private Long orderId;	//约看ID
    
    private String token;//来自客户APP
    
    
}
