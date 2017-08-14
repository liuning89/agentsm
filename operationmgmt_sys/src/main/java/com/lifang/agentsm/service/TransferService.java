package com.lifang.agentsm.service;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.FranchiseeDic;
import com.lifang.agentsm.model.LfFranchiseeInfo;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.model.TransferStore;
import com.lifang.model.Response;


/**
 * 
* @ClassName: TransferService 
* @Description: TODO(资源转移service) 
* @author luogq
* @date 2015年10月20日 
*
 */
public interface TransferService {

	Map<String, Object> findTransferList(Map<String, Object> pars);


	Map<String,Object> findFranchiseeList(Map<String, Object> pars);


	String saveFranchiseeInfo(Map<String, Object> pars);


	List<LfFranchiseeInfo> findFranchiseeById(Map<String, Object> pars);


	String updateFranchiseeInfo(Map<String, Object> pars);


	Map<String, Object> findFranchiseeStoreList(Map<String, Object> pars);


	String findAreaOrg(Map<String, Object> pars);


	void saveFranchiseeStore(Map<String, Object> pars);


	String transferResouce(Map<String, Object> pars);

	List<TransferStore> findNameById(Map<String, Object> pars);


	Map<String, Object> findCustomerList(Map<String, Object> pars);


	List<FranchiseeDic> getAreaByCityId(Map<String, Object> pars);


	List<FranchiseeDic> getData2AreaByAreaId(List<String> list);
	
	/**
	 * 
	 * 根据城市编号获取加盟商列表，志远是特殊的
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年12月23日      新建
	 * </pre>
	 *
	 * @param cityId
	 * @return
	 */
	public List<MiniuiEntity> getSimpleFranchiseeListByCityId(int cityId);


    void updatePush(Map<String, Object> pars);


    Map<String, Object> getPubReportList(Map<String, Object> pars);

	boolean transferResouce(String fransferId);

	String findIdByCityId(Map<String, Object> pars);


}
