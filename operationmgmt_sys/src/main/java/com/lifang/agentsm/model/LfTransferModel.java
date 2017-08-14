package com.lifang.agentsm.model;

import lombok.Data;
@Data
public class LfTransferModel {
    private Long id;

    private String name;//板块名称
    
    private String code;//板块code

    private int houseCount;//房源数
    
    private int easteCount;//小区数
    
    private int customerCount;//客源数
    
    private int cusSeeCount;//带看数

}