package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class ReportModel {
	private long id;
	private long agentId;  //'经纪人ID',
	private String realName;
	private String storeName;
	private long storeId;//门店id
	private long publishNum; //'发布房源数',
	private long publishManNum; //发布房源人数
	private long uploadPicNum; //'上传图片套数',
	private long uploadPicManNum; //上传图片人数
	private long keyNum; //'钥匙数',
	private long entrustNum; //'独家委托数',
	private long addMasterNum; //'新增客户数',
	private long contactLandladyCount; //'联系房东次数',
	private long shareHouseNum; //'分享房源套数',
	private long forwardHouseCount; //'转发房源次数',
	private long leadMasterNum; //'带看客户数',
	private long shareTotalCount; //分享总次数
	
	
	public void sumReport(ReportModel report){
		publishNum+=report.getPublishNum(); //'发布房源数',
		uploadPicNum+=report.getUploadPicNum(); //'上传图片套数',
		keyNum+=report.getKeyNum(); //'钥匙数',
		entrustNum+=report.getEntrustNum(); //'独家委托数',
		addMasterNum+=report.getAddMasterNum(); //'新增客户数',
		contactLandladyCount+=report.getContactLandladyCount(); //'联系房东次数',
		shareHouseNum+=report.getShareHouseNum(); //'分享房源套数',
		forwardHouseCount+=report.getForwardHouseCount(); //'转发房源次数',
		leadMasterNum+=report.getLeadMasterNum(); //'带看客户数',
	}

}
