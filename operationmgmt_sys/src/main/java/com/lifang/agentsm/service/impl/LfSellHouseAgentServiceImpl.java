package com.lifang.agentsm.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lifang.agentsm.dao.read.LfSellHouseAgentReadMapper;
import com.lifang.agentsm.dao.write.LfSellHouseAgentWriteMapper;
import com.lifang.agentsm.entity.ResourceTransferRequest;
import com.lifang.agentsm.service.LfSellHouseAgentService;

@Service
public class LfSellHouseAgentServiceImpl implements LfSellHouseAgentService {
	@Autowired
	private LfSellHouseAgentWriteMapper lfSellHouseAgentWriteMapper;
	@Autowired
	private LfSellHouseAgentReadMapper lfSellHouseAgentReadMapper;
	@Transactional
	@Override
	public void transferResource(ResourceTransferRequest resourceTransferRequest) {
		Integer[] types = resourceTransferRequest.getTypes();
		List<Integer> typeList = Arrays.asList(types);
		
//				value="1"   />发布人 
//				value="2"  />钥匙人  
//				value="3" />委托人   
//				value="4"  />实景人  
//				value="5"  />客源   
		if(typeList.contains(1)){
			lfSellHouseAgentWriteMapper.updatePublishAgentToNew(resourceTransferRequest);
		}
		if(typeList.contains(2)){
			lfSellHouseAgentWriteMapper.updateKeyAgentToNew(resourceTransferRequest);
		}
		if(typeList.contains(3)){
			lfSellHouseAgentWriteMapper.updateCommAgenttToNew(resourceTransferRequest);
		}
		if(typeList.contains(4)){
			lfSellHouseAgentWriteMapper.updatePictureAgentToNew(resourceTransferRequest);
		}
		if(typeList.contains(5)){
			lfSellHouseAgentWriteMapper.updateBuyAgentIdToNew(resourceTransferRequest);
			lfSellHouseAgentWriteMapper.updateCustRequirement(resourceTransferRequest);
		}
		if(typeList.contains(6)){
            lfSellHouseAgentWriteMapper.updateHouseSeeAgentToNew(resourceTransferRequest);
        }
	}

	@Override
	public Integer[] isResourceTransfer(Integer userId) {
		Integer[] results = new Integer[]{0,0,0,0,0,0};
//		value="1"   />发布人 
//		value="2"  />钥匙人  
//		value="3" />委托人   
//		value="4"  />实景人  
//		value="5"  />客源   
		if(lfSellHouseAgentReadMapper.isPublishAgent(userId)>0){
			results[0] = 1;//1代表有这项资源
		}
		if(lfSellHouseAgentReadMapper.isKeyAgent(userId)>0){
			results[1] = 1;
		}
		if(lfSellHouseAgentReadMapper.isCommAgent(userId)>0){
			results[2] = 1;
		}
		if(lfSellHouseAgentReadMapper.isPictureAgent(userId)>0){
			results[3] = 1;
		}
		if(lfSellHouseAgentReadMapper.isBuyAgentId(userId)>0){
			results[4] = 1;
		}
		if(lfSellHouseAgentReadMapper.isHouseSeeAgentId(userId) > 0)
		{
		    results[5] = 1;
		}
		return results;
	}

}
