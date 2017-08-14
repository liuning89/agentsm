package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;

@Data
public class LfAgentOperation {
	
	//@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  

    private Long id;
    private Integer operationType;//操作类型
    private Long operator;//操作人
    private Integer houseId;//
    private Integer status;//状态是否可用
    private Date createTime;
    private Date updateTime;
    private Integer operatorId;
}
