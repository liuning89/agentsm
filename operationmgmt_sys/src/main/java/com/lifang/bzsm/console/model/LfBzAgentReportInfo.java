package com.lifang.bzsm.console.model;

import com.lifang.bzsm.console.entity.LfBzAgentReport;
import lombok.Data;

import java.util.Date;


@Data
public class LfBzAgentReportInfo extends LfBzAgentReport
{
    private double houseSeeAvg;
    private double newCustomerAvg;
    private double housePublishAvg;
    private double houseKeyAvg;
    private double houseImageAvg;
    private double houseEntrustAvg;
}