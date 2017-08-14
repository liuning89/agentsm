package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;

@Data
public class CusRefuseGuest {
	private Integer id;
	private String phoneNum;  //
	private Integer status;
	private Integer reasonType;
	private Date createTime;//
	private Integer createBy; //
	private Integer version; //

}
