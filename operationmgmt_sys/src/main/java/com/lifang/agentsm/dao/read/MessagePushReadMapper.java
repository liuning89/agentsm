package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.MessagePush;
import com.lifang.agentsm.model.LfMessagePush;
import com.lifang.agentsm.model.LfMessagePushLimit;

/**
 * 消息推送
 * Function: TODO ADD FUNCTION
 *
 * @author   周智明
 * @Date	 2015年5月18日	下午3:58:43
 *
 * @see
 */
public interface MessagePushReadMapper {
	
	

	public int getMessagePushCount(Map<String, Object> pars);

	public List<MessagePush> getMessagePushList(Map<String, Object> pars);

	public List<String> findMobileList();

	public List<MessagePush> searchTimeMessages(Map<String, Object> param);

    public List<LfMessagePushLimit> getCompanyAndCityList(LfMessagePushLimit req);

    public LfMessagePush getMessageById(int id);

    public List<LfMessagePushLimit> getCompanyAndCityByMsgId(int pushId);

	
}