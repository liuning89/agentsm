package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.LfAppFeedback;
import com.lifang.agentsm.model.FeedBackRequest;
import com.lifang.agentsm.model.LfAppFeedbackInfo;

public interface LfAppFeedbackReadMapper {
    
    //通过主键获取反馈信息
    LfAppFeedback selectByPrimaryKey(Long id);
    
    //分页查询反馈信息
    List<LfAppFeedbackInfo> selectLfAppFeedbackListByPage(FeedBackRequest req);
    
    //分页查询总数量
    int selectLfAppFeedbackListCount(FeedBackRequest req);
}