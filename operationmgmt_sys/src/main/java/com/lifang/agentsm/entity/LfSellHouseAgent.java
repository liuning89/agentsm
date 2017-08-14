package com.lifang.agentsm.entity;

import java.util.Date;

import lombok.Data;

@Data
public class LfSellHouseAgent {
    private Long id;

    private Long houseId;

    private Long keyAgent;

    private Long publishAgent;

    private Long commAgent;

    private Long buyAgentId;

    private Byte allowContact;

    private Date updateTime;

    private Date createTime;

    private Long contactAgentId;

    private Byte status;

    private Long proCommAgent;

    private Byte isExclusive;

    private Long updateAgent;

    private Long pictureAgent;

    private Long recordAgent;

    private Long sourceId;

}