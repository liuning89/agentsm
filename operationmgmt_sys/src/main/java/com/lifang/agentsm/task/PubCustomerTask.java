package com.lifang.agentsm.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lifang.agentsm.service.MessagePushService;
import com.lifang.agentsm.service.QuartzTaskBase;
import com.lifang.agentsm.service.RulePublicService;

/**
 * 定时推送消息
 */
@Component
public class PubCustomerTask {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
		
	@Autowired
	private MessagePushService messagePushService;
	
	@Autowired
    RulePublicService rulePublicService;
	/**
	 * 推送消息
	 */
//	@Scheduled(cron="0 10 0 * * ?")
//  @Scheduled(cron="0 0/1 * * * ?")
//	@Scheduled(cron="0 0/10 * * * ?")
	@Scheduled(cron="${pubCustomerTime}")
	public void pubCustomerTask(){
		logger.info("开始判断本机是否跑定时任务");
		if (isRunServer()) {
			//@Scheduled(cron="0 10 0 ? * *")
			logger.info("公客定时任务, 开始!");
			try {
				rulePublicService.runMain();
	        }
	        catch (Exception e) {
	            logger.error("数据异常",e);
	        } 
			logger.info("公客定时任务, 结束!");
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
