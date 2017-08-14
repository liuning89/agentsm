package com.lifang.bzsm.console.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 15-7-20.
 */
@Data
public class LfAreaOrg {
    private Integer id;
    private String name;
    private Integer cityId;
    private Integer parentId;
    private Integer level;
    private String code;
    private Integer status;
    private Integer areaId;
    private Integer storeId;
    private String storeCode;
    private Integer townId;
    private Date createTime;
    private Date updateTime;
    private String memo;
}
