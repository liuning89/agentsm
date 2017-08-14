package com.lifang.agentsm.dao.write;

import java.util.Map;

import com.lifang.agentsm.model.LfMessagePushLimit;

/**
 * 消息推送()
 * Function: TODO ADD FUNCTION
 *
 * @author   luogq
 * @Date	 2015年5月18日	下午3:58:43
 *
 * @see
 */
public interface MessagePushWriteMapper {

	void insertSendMessage(Map<String, Object> pars);

	void pushMessage(Map<String, Object> params);

	void updateSendMessage(Map<String, Object> pars);

	void deleteMessage(Map<String, Object> pars);

    void insertSendMsgLimit(LfMessagePushLimit limit);

    void deleteMessageLimitByPushId(int pushId);

}