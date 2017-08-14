package com.lifang.agentsm.service;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.LfAgentActivity;


/**
 * 
* @ClassName:  
* @Description: TODO(活动管理service) 
* @author luogq
* @date 2015年9月6日 
*
 */
public interface MarketActivityService {

	/**
	 * 查找所有活动
	 * @return
	 */
	List<LfAgentActivity> selectByList();

	int addAndEditUserAppTopUrl(Map<String, Object> pars);

	int addAndUpdateUserAppTopImg(byte[] bytes, Long id);

	int updateOut(Map<String, Object> pars);
    
  
    
}
