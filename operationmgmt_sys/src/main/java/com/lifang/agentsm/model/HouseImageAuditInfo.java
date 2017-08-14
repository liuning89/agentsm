package com.lifang.agentsm.model;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

@Data
public class HouseImageAuditInfo {
	
	private Long id;
	private String sellPoint;//房屋买点.
	private String mobile;
	private String token;
	private String houseId;
	private String agentId;
	private String estatename;//小区名
	private String subEstateName;//子划分
    private String buildingName;//座栋号

    private String  room;//房间号

    private String deptName;//部门

    private String realName;// 上传人
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    private String createTime;// 上传时间
    private String auditStatus;// 审核状态
    private String loginName;// 审核人
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    private String auditTime;// 审核时间
    private String rejectreason;//驳回理由
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    private String batchTime;//批次时间
    
    private String agentpId;//上传人pid
    private String emppId;//
    private Integer guestId;//房东id
    private String phoneNum;//房东号码
}
