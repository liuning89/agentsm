package com.lifang.agentsm.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lifang.agentsm.service.MessagePushService;
import com.lifang.agentsm.service.QuartzTaskBase;

/**
 * 定时推送消息
 */
@Component
public class MessagePushTask {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@Autowired
	private MessagePushService messagePushService;

	/**
	 * 推送消息
	 */
	//1分钟跑一次
	@Scheduled(cron="0 0/1 * * * ?")
	public void toMorrowBirthSendSMS(){
		logger.info("开始判断本机是否跑定时任务");
		if (isRunServer()) {
			logger.info("定时推送消息, 开始!");
			try {
				messagePushService.timingPushMsg() ;
	        }
	        catch (Exception e) {
	            logger.error("数据异常",e);
	        } 
			logger.info("定时推送消息, 结束!");
		}
	}
	
	private boolean isRunServer() {
		if (QuartzTaskBase.isRun()) {
			return true;
		} else {
			logger.info("这台机器不跑定时任务！");
		}
		return false;
	}
}
