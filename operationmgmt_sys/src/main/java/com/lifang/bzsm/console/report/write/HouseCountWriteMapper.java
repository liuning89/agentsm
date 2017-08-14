package com.lifang.bzsm.console.report.write;

import java.util.List;

import com.lifang.agentsm.model.HouseCountTown;

/**
 * 
 *
 * @author   luogq
 * @Date	 2016年2月26日
 *
 * @see
 */
public interface HouseCountWriteMapper {


	int insertHouseSellCount(List<HouseCountTown> hctList);
}
