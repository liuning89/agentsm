package com.lifang.agentsm.entity;

import java.util.Date;

import lombok.Data;

@Data
public class LfUserappHomeImg {
    private Long id;
    private Integer type;
    private String url;
    private String imgKey;
    private Integer stauts;
    private Integer createBy;
    private Date createTime;
    private Integer updateBy;
    private Date updateTime;
    private String imgPath;
     
}