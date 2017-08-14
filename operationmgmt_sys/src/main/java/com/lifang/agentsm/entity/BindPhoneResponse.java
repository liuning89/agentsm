package com.lifang.agentsm.entity;

import lombok.Data;

@Data
public class BindPhoneResponse {
    private int status ; //0成功, 1失败, 2该号码已经绑定过, 3绑定非法号码
    private String message; //返回信息
}
