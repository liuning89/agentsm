package com.lifang.agentsm.service;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.model.LfMessagePush;
import com.lifang.agentsm.model.LfMessagePushLimit;
import com.lifang.model.Response;

/**
 * 推送消息
 * Function: TODO ADD FUNCTION
 *
 * @author   luogq
 * @Date	 2015年8月20日	下午4:02:58
 *
 * @see
 */
public interface MessagePushService {
	

	public Object getMessagePushList(Map<String, Object> pars);
/**
 *  将消息保存至数据库
 * @param pars
 */
	public void insertSendMessage(Map<String, Object> pars);
	public List<String> findMobileList();
	public void timingPushMsg();
	public void updateSendMessage(Map<String, Object> pars);
	public void deleteMessage(Map<String, Object> pars);
    public List<LfMessagePushLimit> getCompanyAndCity(LfMessagePushLimit limit);
    public LfMessagePush getMessageById(int id);
    public List<LfMessagePushLimit> getCompanyAndCityByMsgId(int pushId);
    public Response saveData(Map<String, Object> pars);
}
