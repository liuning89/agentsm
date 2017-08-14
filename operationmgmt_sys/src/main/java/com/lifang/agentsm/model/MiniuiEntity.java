package com.lifang.agentsm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author:CK
 * 创建时间：2015年5月28日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiniuiEntity {
	private String text;
	private Integer id;
	private Integer parentId;
}

