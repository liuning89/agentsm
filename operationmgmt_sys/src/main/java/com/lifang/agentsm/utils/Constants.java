package com.lifang.agentsm.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
	
	public static final int 接口异常 = -1;

	public static final int 成功 = 1;

	private static final Logger logger = LoggerFactory.getLogger(Constants.class);

	public static String TABLE_NAME_INTERMEDIARYTEMP = "IntermediaryTemp";
	
	public static String TABLE_NAME_USER = "User";
	public static String TABLE_NAME_USERHISTORY = "UserHistory";
	public static String TABLE_NAME_PAY = "Pay";
	public static String TABLE_NAME_MERGERPAY = "MergerPay";
	public static String TABLE_NAME_HOURCERESOURCEVIEWCOUNT = "HouseResourceViewCount";
	public static String TABLE_NAME_EMPLOYEE = "Employee";
	
	public static String TABLE_NAME_AREA = "Area";
	public static String TABLE_NAME_HOUSERESOURCETEMP = "HouseResourceTemp";
	public static String TABLE_NAME_HOUSERESOURCEHISTORY = "HouseResourceHistory";
	public static String TABLE_NAME_HOUSEDOWN = "HouseDown";
	
	public static String LOGIN_SESSION;

	public static String LINK_FRONT_PART;
	
	
	public static String HIMS_SERVICE_PATH;
	
	public static String FYT_PUSH_IS_PRODUCTION;

	static {
		LOGIN_SESSION = Config.get("S_constants.login_session");
		LINK_FRONT_PART = Config.get("S_constants.link_front_part");
		HIMS_SERVICE_PATH = Config.get("S_constants.hims_service_path");
		FYT_PUSH_IS_PRODUCTION = Config.get("fytPushIsProduction");
		
	}

	private static class Config {

		static ConfigMap configMap = new ConfigMap();
		static {
			try {
				configMap = PropertiesHelper.loadProperties("/config.properties");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("The current application of information in the [/META-INF/config.properties] configuration:current.server");
			}
		}

		public static String get(String key) {
			return configMap.getKV(key, StringUtils.EMPTY);
		}
		
		public static int getInt(String key) {
			return configMap.getKV(key, 0);
		}
	}
}
