package com.lifang.agentsm.model;

import lombok.Data;

@Data
public class TravelAllowanceSetDetail {

    private  int id;
    private int travelId;
    private int type; //'1.发布房源 2.实景（非现拍） 3.实景（现拍）4.视频（非现拍） 5.视频（现拍）6.好评 7.带盘 8 房源无效成功'
    private int status;
    private int wkCoin;
    private String typeName;
}
