package com.lifang.bzsm.console.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class BizReportRequest {
    private String dateStart;
    private String dateEnd;

    private Integer pageIndex;
    private Integer pageSize;
    private Integer cityId;
    private Integer areaId;
    private Integer storeId;
    private Integer agentId;
    private String agentMobile;
    private int queryType;
    private String orgCode;

    private String sortField;
    private String sortOrder;
    private List<Integer> agentIds;
    private String columns;
    private String header;
    private String field;
    private String title;


    List<Integer> storeIds;
    List<Integer> areaIds;

}
