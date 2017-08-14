package com.lifang.agentsm.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MessageCenter {

	private Integer id;
	private Integer userId; //用户Id
	private Integer type; //1系统  2个人3android 4 ios
	private String message ; //消息内容
	private Integer status; //1有效,2无效, 默认为0
	private Date createTime ;
	private Date updateTime ;

}