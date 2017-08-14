package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.LfYfykAmoutSetReadMapper;
import com.lifang.agentsm.dao.read.MessagePushReadMapper;
import com.lifang.agentsm.dao.write.MessagePushWriteMapper;
import com.lifang.agentsm.entity.MessagePush;
import com.lifang.agentsm.model.LfMessagePush;
import com.lifang.agentsm.model.LfMessagePushLimit;
import com.lifang.agentsm.model.enums.MessageTypeEnum;
import com.lifang.agentsm.service.MessagePushService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.mipushsoa.facade.MiPushSOAServer;
import com.lifang.model.Response;


/**
 * 消息推送
 * Function: TODO ADD FUNCTION
 *
 * @author   luogq
 * @Date	 2015年8月20日	上午11:23:47
 *
 * @see
 */
@Service
public class MessagePushServiceImpl implements MessagePushService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessagePushReadMapper messagePushReadMapper;
	@Autowired
	private MessagePushWriteMapper messagePushWriteMapper;
	@Autowired
	private MiPushSOAServer pc;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Autowired
	private AgentSOAServer agentSOA;

	@Override
	public Map<String, Object> getMessagePushList(Map<String, Object> pars) {

		Map<String, Object> map = new HashMap<String, Object>();
		int count = messagePushReadMapper.getMessagePushCount(pars);
		
		List<MessagePush> list = new ArrayList<MessagePush>();
        if (count > 0) {
            list = messagePushReadMapper.getMessagePushList(pars);
        }
        map.put("data", list);
        map.put("total", count);
		
        return map;
	}

	@Override
	public void insertSendMessage(Map<String, Object> pars) {
	    pars.put("id", null);
	    //title,pushContent,createtime,updatetime,pushtime,istiming,pushstatus,isdelete,createuserid,pushuserid,iosversion,pushplatform
		messagePushWriteMapper.insertSendMessage(pars);
		if(StringUtils.isNotBlank(pars.get("companyIds").toString())){
		    messagePushWriteMapper.deleteMessageLimitByPushId(Integer.valueOf(pars.get("id").toString()));
		    String[] companyIds = pars.get("companyIds").toString().split(",");
	        String[] cityIds = pars.get("cityIds").toString().split(",");
	        for(int i=0;i<companyIds.length;i++){
	            LfMessagePushLimit limit = new LfMessagePushLimit();
	            limit.setCityId(Integer.valueOf(cityIds[i].trim()));
	            limit.setCompanyId(Integer.valueOf(companyIds[i].trim()));
	            limit.setPushId(Integer.valueOf(pars.get("id").toString()));
	            messagePushWriteMapper.insertSendMsgLimit(limit);
	        }

		}
	}

	@Override
	public List<String> findMobileList() {
		return messagePushReadMapper.findMobileList();
	}

	@Override
	public void timingPushMsg() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(new Date().getTime());

		calendar.set(Calendar.SECOND, 0);
		Date startDate = calendar.getTime() ;

		calendar.set(Calendar.SECOND, 59);
		Date endDate = calendar.getTime() ;

		//获取需要推送的消息记录
		Map<String, Object> param = new HashMap<String, Object>() ;
		param.put("startDate", startDate) ;
		param.put("endDate", endDate) ;
		List<MessagePush> data = messagePushReadMapper.searchTimeMessages(param) ;

		if(data != null && data.size() > 0) {
			//推送消息
			pushMsg(data);

			//修改推送信息
			List<Integer> id = new ArrayList<Integer>() ;
			for(MessagePush mp : data) {
				id.add(mp.getId());
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pushstatus", 2);
			params.put("updateTime", new Date());
			params.put("messageIds", id);
			messagePushWriteMapper.pushMessage(params);
		}
	}
	
	
	/**
	 * 调用推送消息接口
	 * @param msg
	 */
	private void pushMsg(final List<MessagePush> msg) {
		threadPoolTaskExecutor.submit(new Runnable() {
			public void run() {
				try {
					if(msg != null) {
						logger.info("推送消息开始------------");
						//消息推送
						//获取所有用户
//						List<FytUser> fytUser = fytUserReadMapper.selectAll();
						List<String> mobileList = messagePushReadMapper.findMobileList();

						List<String> list = new ArrayList<String>();
						List<String> listToken = new ArrayList<String>();
						for(MessagePush mp : msg){
//							pc.sendYFYKMsg(mobileList, mp.getTitle(), mp.getPushContent(), MessageTypeEnum.AUDIT.getCode(), 0, "", 0,null);//.sendAndroidNotification(mobileList, title,
							if(mp.getPushPlatform()==3){
							    pc.sendMessage(mobileList, mp.getTitle(), mp.getPushContent(), null, 2, 0, MessageTypeEnum.AUDIT.getCode());
							}else{
							    List<LfMessagePushLimit> limitList = messagePushReadMapper.getCompanyAndCityByMsgId(mp.getId());
							    List<Agent> listAll = new ArrayList<Agent>();
							    if(limitList.size()>0&&!limitList.isEmpty()){
							        for(int i=0;i<limitList.size();i++){
							            List<Agent>  agentList = agentSOA.getAgentList(limitList.get(i).getCityId(), null, null, null, null,limitList.get(i).getId());
							            listAll.addAll(agentList);
							        }
							    }
	                            List<String> allMobile = new ArrayList<String>();
	                            if(!listAll.isEmpty()&&listAll.size()>0){
	                                for(Agent a:listAll){
	                                    allMobile.add(a.getMobile());
	                                }
	                                pc.sendMessage(allMobile, mp.getTitle(), mp.getPushContent(), null, 2, 0, MessageTypeEnum.AUDIT.getCode());//.sendAndroidNotification(mobileList, title,
	                            }

							}
						}
						
						
						
						logger.info("推送消息结束------------");
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					logger.error("经纪人信息推送失败：", e);
				}
			}
		});
	}

	@Override
	public void updateSendMessage(Map<String, Object> pars) {
		messagePushWriteMapper.updateSendMessage(pars);
	}

	@Override
	public void deleteMessage(Map<String, Object> pars) {

		messagePushWriteMapper.deleteMessage(pars);
	}

    @Override
    public List<LfMessagePushLimit> getCompanyAndCity(LfMessagePushLimit req) {
        return messagePushReadMapper.getCompanyAndCityList(req);
        
    }

    @Override
    public LfMessagePush getMessageById(int id) {
        return messagePushReadMapper.getMessageById(id);
    }

    @Override
    public List<LfMessagePushLimit> getCompanyAndCityByMsgId(int pushId) {
        return messagePushReadMapper.getCompanyAndCityByMsgId(pushId);
    }

    @Override
    public Response saveData(Map<String, Object> pars) {
        if(StringUtils.isNotBlank(pars.get("companyIds").toString())){
            String[] companyIds = pars.get("companyIds").toString().split(",");
            String[] cityIds = pars.get("cityIds ").toString().split(",");
            for(int i=0;i<companyIds.length;i++){
                LfMessagePushLimit limit = new LfMessagePushLimit();
                limit.setCityId(Integer.valueOf(cityIds[i].trim()));
                limit.setCompanyId(Integer.valueOf(companyIds[i].trim()));
                limit.setPushId(Integer.valueOf(pars.get("id").toString()));
                messagePushWriteMapper.insertSendMsgLimit(limit);
            }
        }
        return null;
    }
}
