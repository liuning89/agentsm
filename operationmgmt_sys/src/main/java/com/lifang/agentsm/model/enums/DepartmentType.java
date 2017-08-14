package com.lifang.agentsm.model.enums;

/**
 * 
 * 部门类型
 *
 * @author   Yang'ushan
 * @Date	 2015年8月4日		上午11:47:44
 *
 * @see
 */
public enum DepartmentType {

	/**
	 * 业务
	 */
	YeWu((byte)1),
	
	/**
	 * 运营
	 */
	YunYing((byte)2),
	
	/**
	 * 人事
	 */
	RenShi((byte)3),
	
	/**
	 * 财务
	 */
	CaiWu((byte)4),
	
	/**
	 * 法务
	 */
	FaWu((byte)5);
	
	public final byte value;
	
	private DepartmentType(byte value) {
		this.value = value;
	}
	
	public static DepartmentType newInstance(byte value) {
		for (DepartmentType temp : DepartmentType.values()) {
			if (temp.value == value) {
				return temp;
			}
		}
		return null;
	}
	
}
