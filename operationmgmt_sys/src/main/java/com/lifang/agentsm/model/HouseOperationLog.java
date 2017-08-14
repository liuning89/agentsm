package com.lifang.agentsm.model;

import lombok.Data;

/**
 * @author lohocc
 * @Date 2015年6月24日
 */
@Data
public class HouseOperationLog {
	private Integer id;
	private Integer empId;
	private Integer houseId;
	private Integer opType;
	private Integer status;
	private String memo;
}
