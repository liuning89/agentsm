package com.lifang.bzsm.console.model;

import com.lifang.bzsm.console.entity.LfCustomerHouseSee;
import lombok.Data;

import java.util.Date;

@Data
public class LfCustomerHouseSeeInfo extends LfCustomerHouseSee {

    private String name;   //带看人

    private String customerName; //客户姓名
    private String houseAddress; //房屋地址
    private String storeName;    //门店名称

}