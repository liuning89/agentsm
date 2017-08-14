package com.lifang.agentsm.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.lifang.agentsm.common.Global;
import com.lifang.agentsm.entity.Config;
import com.lifang.agentsm.service.ConfigService;

@Controller
public class ConfigReloadController {
	private Logger logger = LoggerFactory.getLogger(ConfigReloadController.class);
	
	/**local cache*/
	private static Set<String> ipSet = new HashSet<String>();
	
	@Autowired
	private ConfigService configService;
	
	/*@PostConstruct
	public void init() {
		//		reload ();// 放在这里 需要hims先启动,部署在 同一个环境中时, 不能保证
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				//1 分钟 调用一次
				reload ();
			}
		}, 30 , 60, TimeUnit.SECONDS);
	}*/
	
	public boolean isValid (String ip) {
	    if (Global.DISABLE_VISIT_IP == 0) {
	        logger.info("不对ip进行验证...");
            return true;
        }
	    
		//logger.info("hims 客户端 ip : {}" ,ip);
		//第一次调用的时候. 先加载一次
		if(ipSet.size() == 0){
			reload ();
		}
		if (StringUtils.isBlank(ip)) {
			logger.error("ip is null");
			return false;
		}
		if(ipSet.contains(ip)){
			return true;
		}
		if (ip.lastIndexOf(".") != -1) {
			ip = ip.substring(0,ip.lastIndexOf("."));
			logger.debug("short ip........" + ip);
		}
		if(ipSet.contains(ip)){
			return true;
		}else {
			logger.info("hims not valid ip:" + ip);
			return false;
		}
	}
	
	public void reload () {
		logger.info("ConfigReloadController.reload");
		List<Config> configs = configService.selectByConfigList();
				
		if (configs != null && configs.size() > 0) {
			Set<String> ipSetTemp = new HashSet<String>(configs.size());
			for (Config one : configs) {
				ipSetTemp.add(one.getIpValue());
			}
			logger.info(" valid ip table:" + ipSetTemp.toString());
			ipSet = ipSetTemp;//内存引用替换
		}
		
	}

	

}
