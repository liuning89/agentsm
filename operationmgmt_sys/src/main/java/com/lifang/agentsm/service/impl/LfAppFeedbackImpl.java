package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.LfAppFeedbackReadMapper;
import com.lifang.agentsm.model.FeedBackRequest;
import com.lifang.agentsm.model.LfAppFeedbackInfo;
import com.lifang.agentsm.service.LfAppFeedbackService;

@Service(value = "lfAppFeedbackService")
public class LfAppFeedbackImpl implements LfAppFeedbackService {

    @Autowired
    private LfAppFeedbackReadMapper lfAppFeedbackReadMapper;
    

    @Override
    public Map<String, Object> selectLfAppFeedbackListByPage(FeedBackRequest req) {
        req.setPageIndex(req.getPageIndex() * req.getPageSize());
        
        int count = lfAppFeedbackReadMapper.selectLfAppFeedbackListCount(req);
        List<LfAppFeedbackInfo> array = new ArrayList<LfAppFeedbackInfo>();
        if(count > 0)
        {
            array = lfAppFeedbackReadMapper.selectLfAppFeedbackListByPage(req);
        }
        
        
        if(array != null && array.size() > 0)
        {
            List<Integer> pids = new ArrayList<Integer>();
            LfAppFeedbackInfo tempInfo = null;
            for (int i = 0; i < array.size(); i++)
            {
                tempInfo = array.get(i);
                if (tempInfo.getPositionId() != null && tempInfo.getPositionId() != 0)
                {
                    pids.add(tempInfo.getPositionId());
                }
            }

//            if (pids.size() > 0)
//            {
//                List<PositionCache> cacheList = areaOrgPriSOAClient.getShowNameByPositionIds(pids);
//                if (cacheList != null)
//                {
//                    LfAppFeedbackInfo tempResearch;
//                    PositionCache tempCache;
//                    for (int i = 0; i < array.size(); i++)
//                    {
//                        tempResearch = array.get(i);
//                        for (int j = 0; j < cacheList.size(); j++)
//                        {
//                            tempCache = cacheList.get(j);
//                            if (tempCache != null)
//                            {
//                                if (tempCache.getPersonPositionId() != null && tempResearch.getPositionId() != null)
//                                {
//                                    if (tempResearch.getPositionId().intValue() == tempCache.getPersonPositionId().intValue())
//                                    {
//                                        tempResearch.setStoreName(tempCache.getDoorAreaOrg());
//                                        break;
//                                    }
//                                }
//                                
//                                if (tempCache.getDoorPositionId() != null && tempResearch.getPositionId() != null)
//                                {
//                                    if (tempResearch.getPositionId().intValue() == tempCache.getDoorPositionId().intValue())
//                                    {
//                                        tempResearch.setStoreName(tempCache.getDoorAreaOrg());
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        }
        
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", array);
        map.put("total", count);
        return map;
    }

}
