package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;

@Data
public class AccusAtion {

	private Integer id;
	/**房源ID*/
	private Integer houseId;
	/**举报人*/
	private Integer accusationAgentId;
	/**被举报人*/
	private Integer beAccusationAgentId;
	/**举报类型( 1.卖点举报  2.图片举报)*/
	private Integer accusationType;
	/**举报原因ID*/
	private Integer accusationReasonId;
	/**图片key*/
	private String imgKey;
	/**是否有效（0.否 1.是）*/
	private Integer status;
	/**审核人*/
	private Integer auditor;
	/**审核状态(1.未审核   2.审核通过)*/
	private Integer auditSatus;
	/**审核时间*/
	private Date auditTime;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	
	private String positionId;
	private String positiName;
	
	private String positionId1;
	private String positiName1;
	
	private String name;//上传人
	private String name1; //举报人
	
	/**房屋买点*/
	private String sellPoint;//卖点
	/**处理人姓名*/
	private String aname;//
		
}
