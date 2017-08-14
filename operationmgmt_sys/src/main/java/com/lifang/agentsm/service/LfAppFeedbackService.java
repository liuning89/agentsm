package com.lifang.agentsm.service;

import java.util.Map;

import com.lifang.agentsm.model.FeedBackRequest;


public interface LfAppFeedbackService {
     
    /**
     * 查询反馈信息分页
     */
    Map<String,Object> selectLfAppFeedbackListByPage(FeedBackRequest req);
}