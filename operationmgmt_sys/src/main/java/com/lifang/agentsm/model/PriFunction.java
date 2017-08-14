package com.lifang.agentsm.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * 功能
 *
 * @author   Yang'ushan
 * @Date	 2015年7月22日		下午4:28:35
 *
 * @see
 */
@Data
public class PriFunction {

	private Integer id;
	private String name;
	private String appName;
	private String memo;
	/** 1菜单 2url*/
	private Byte type;
	private Date createTime;
	
	private List<PriUrl> urls;
	
}
