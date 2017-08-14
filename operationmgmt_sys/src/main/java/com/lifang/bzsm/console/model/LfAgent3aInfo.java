package com.lifang.bzsm.console.model;

import com.lifang.bzsm.console.entity.LfAgent3a;
import lombok.Data;

import java.util.Date;

@Data
public class LfAgent3aInfo extends LfAgent3a{
    private Date logUpTime;
    private String name3A;
}