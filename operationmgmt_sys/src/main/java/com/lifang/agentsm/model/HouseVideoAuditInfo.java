package com.lifang.agentsm.model;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

@Data
public class HouseVideoAuditInfo {
//	a.id,a.estateId,c.initname,a.videokey,a.videosmallkey,e.`name`,a.createTime,
//	a.videostatus,f.`name`,d.updatetime,d.rejectreason,a.videoBigImageKey,a.videosmallimagekey	
	private Long id;
	private String houseId;
	private String initname;//房屋地址
	private String videoBigImageKey;
	private String videosmallimagekey;
	private String videokey;
	private String videosmallkey;
	
	private String employeeId;//视频上传人ID
	
	
	private String agentName;//视频上传人

    private String deptName;//部门
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    private String createTime;// 上传时间

	private String videostatus;//上传状态
	private String auditorid;//审核人id
    private String auditor;//审核人

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
    private String updatetime;//审核时间
    private String rejectreason;//驳回理由
    private String mobile;
    private String positionid;
    private String estateName;
    
    private String room;
    private String buildingName;
    private String ownerid;
    private String phoneNum;//房东号码
    
}
