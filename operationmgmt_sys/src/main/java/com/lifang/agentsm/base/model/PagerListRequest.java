package com.lifang.agentsm.base.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagerListRequest {

	// 页数
	private Integer page = 1;
	// 起始下标
	private Integer start = 0;
	// 每页显示数
	private Integer rows = 20;
	// 排序列
	private String sort;
	// 排序方式
	private String order;
	
	public Integer getStart() {
		return (page - 1) * rows;
	}
	
	

}
