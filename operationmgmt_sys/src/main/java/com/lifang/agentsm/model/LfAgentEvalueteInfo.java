package com.lifang.agentsm.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class LfAgentEvalueteInfo {
	
	private Long id;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	private Date updateTime; //评价时间
	
	private String type;//1为好评,2为差评

    private String label;//好评标签  差评标签

    private String  name;//客户姓名

    private String comment;//补充内容

    private String guestPhoneNum;// 客户手机号

  
}
