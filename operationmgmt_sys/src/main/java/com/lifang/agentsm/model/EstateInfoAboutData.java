package com.lifang.agentsm.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class EstateInfoAboutData implements Cloneable{
    private Long subEstateId;
    private String cityName;
    private String cityId;
    private String districtId;
    private String dicName;
    private String dicId;
    private String townName;
    private String townId;
    private String estateName;
    private int yxkcNum;//有效库存数
    private String llNum;//浏览数
    private String sjNum;//实景数
    private String sxNum;//速销数
    private String ysNum;//钥匙数
    private String msNum;//描述数
    private String dtNum;//店推数
    private String openHouse;//看房数
    private String dkNum;//带看数
    private String gjNum;//跟进数
    private String scNum;//收藏数
    private Integer pageSize;
    private Integer pageIndex;
    private Double totalScore;
    private String createTime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    
    public Object clone() throws CloneNotSupportedException {
        //直接调用父类的clone()方法,返回克隆副本
        return super.clone();
    }
    
}
