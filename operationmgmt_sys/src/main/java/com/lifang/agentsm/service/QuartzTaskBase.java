package com.lifang.agentsm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.lifang.agentsm.common.Global;
import com.lifang.utils.WebTool;

public class QuartzTaskBase {

	private static Logger logger = LoggerFactory.getLogger(QuartzTaskBase.class);

	private static boolean runFlag = false;

	public static boolean isRun() {
		return runFlag;
	}

	private static void setRunFlag(boolean runFlag) {
		QuartzTaskBase.runFlag = runFlag;
	}

	static {
		String ip = WebTool.getHostName();
		logger.info("获取本机 ip : {}", ip);
		logger.info("定时任务ip为："+Global.QUARTZTASK_RUN_IP);
		if (Global.QUARTZTASK_RUN_IP.equals(ip)) {
			setRunFlag(true);
		}

	}

}
