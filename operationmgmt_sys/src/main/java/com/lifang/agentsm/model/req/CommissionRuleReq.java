package com.lifang.agentsm.model.req;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by admin on 2015/10/20.
 */

@Data
public class CommissionRuleReq extends BaseReq implements Serializable{

    private String id;
    private String role;
    private Float distpercent;
    private Boolean islocked;
    private String _state;
    private Float commission;
    private String createby;
}
