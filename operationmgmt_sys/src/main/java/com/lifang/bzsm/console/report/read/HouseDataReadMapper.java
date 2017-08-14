package com.lifang.bzsm.console.report.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.model.HouseCountTown;

public interface HouseDataReadMapper
{

    int getHouseDataTownCount(Map<String, Object> pars);

    List<HouseCountTown> getHouseDataTown(Map<String, Object> pars);
}