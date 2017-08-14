package com.lifang.agentsm.model;

import java.util.Date;

import lombok.Data;

@Data
public class Menu {

	private Integer id;
	private String name;
	private Integer parentId;
	private String parentName;
	private Integer functionId;
	private String functionName;
	private Date createTime;
	private String memo;
	private Byte isLeaf;
	private String url;
	private Integer sort;
	private String appName;
	
}
