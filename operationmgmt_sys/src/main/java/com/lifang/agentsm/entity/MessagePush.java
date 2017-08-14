package com.lifang.agentsm.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 消息推送表 Function: TODO ADD FUNCTION
 * 
 * @author luogq
 * @Date 2015年8月20
 * 
 * @see
 */
@Data
public class MessagePush {
	private Integer id;
	private String title;
	private Integer pushPlatform; // 推送平台,1.Android, 2.IOS, 3.全部
	private Integer iosVersion; // 1线上, 2inhouse, 3全部
	private Integer pushUserId; // 发送人Id
	private Integer createUserId; // 创建人Id
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	private Date createTime; // 创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	private Date updateTime; // 修改时间
	private String pushContent;// '推送内容',
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	private Date pushTime;// '推送时间',
	private Integer isTiming;// 是否定时 '1：是，2:否',
	private Integer pushStatus;// 消息状态 '1：待发送，2:已发送,3:发送失败',
	private Integer isdelete;// 是否删除'1：正常，2:已删除',

}