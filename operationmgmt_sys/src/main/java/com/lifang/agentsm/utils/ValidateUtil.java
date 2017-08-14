package com.lifang.agentsm.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ValidateUtil {
	/**
	 * 功能描述:校验身份证是否合法,18位与15位均可校验
	 * 18位初步校验生日是否合法，15位只要求全为数字
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年4月27日      新建
	 * </pre>
	 *
	 * @param code
	 * @return 验证通过返回true，不通过返回false
	 */
	public static boolean checkIdentityCode(String code){
		if(StringUtils.isEmpty(code))
			return false;
		String codeReg = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
		return Pattern.matches(codeReg, code);
	}
	
	/**
	 * 功能描述:根据身份证号返回性别
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月12日      新建
	 * </pre>
	 *
	 * @param code
	 * @return 0-保密 1-男 2-女
	 */
	public static int getGenderByCardId(String code){
		if(code.length()==18){
			int i = Integer.parseInt(code.substring(16,17))%2;//0-女 1-男
			if(i==0){
				return 2;
			}else if(i==1){
				return 1;
			}
		}
		return 0;
	}
	
	public static void main(String[] args) {
		System.out.println(getGenderByCardId("420583199201083706"));
	}
	
	public static boolean isNull(String str)
	{
	    if(str == null || str.trim().equals(""))
	    {
	        return true;
	    }
	    else{
	        return false;
	    }
	}
}
