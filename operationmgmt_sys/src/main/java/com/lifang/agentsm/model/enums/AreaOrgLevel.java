package com.lifang.agentsm.model.enums;

/**
 * 
 * 组织架构
 *
 * @author   Yang'ushan
 * @Date	 2015年8月4日		上午11:39:34
 *
 * @see
 */
public enum AreaOrgLevel {

	/**
	 * 国家
	 */
	Country(100),
	
	/**
	 * 城市
	 */
	City(90),
	
	/**
	 * 区域
	 */
	Area(70),
	
	/**
	 * 门店
	 */
	Store(60),
	
	/**
	 * 个人
	 */
	Person(40);
	
	public final int value;
	
	private AreaOrgLevel(int value) {
		this.value = value;
	}
	
	public static AreaOrgLevel newInstance(int value) {
		for (AreaOrgLevel temp : AreaOrgLevel.values()) {
			if (temp.value == value) {
				return temp;
			}
		}
		return null;
	}
	
}
