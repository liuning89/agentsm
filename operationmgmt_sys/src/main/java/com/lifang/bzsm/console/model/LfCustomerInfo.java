package com.lifang.bzsm.console.model;

import com.lifang.bzsm.console.entity.LfCustomer;
import lombok.Data;

import java.util.Date;

@Data
public class LfCustomerInfo extends LfCustomer {
    private Integer acus;//客户标A数量
    private Date acusTime; //标A时间
    private String agentName; //经纪人姓名
}
