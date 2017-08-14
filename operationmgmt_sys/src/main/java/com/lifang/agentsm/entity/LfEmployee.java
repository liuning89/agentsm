package com.lifang.agentsm.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


@Data
public class LfEmployee implements Serializable{
    private Integer id;
    private String name;
    private String workNumber;

    private String passWord;

    private Integer status;


    private Date createTime;

    private Date updateTime;
    
    private String mobile; //员工电话
    
    private Integer gender; //员工性别
    
    private String department;//运营系统为2
    
    private Integer cityId;

	private Integer positionId;

}