package com.lifang.agentsm.service;

import java.util.List;
import java.util.Map;

import com.lifang.bzsm.console.model.AgentRequest;
import com.lifang.agentsm.entity.LfAgent;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.Employee;
import com.lifang.agentsm.model.LfAgentAllInfo;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.model.PageResponse;

public interface LfAgentService {
	public PageResponse getAgentList(AgentRequest req, LfEmployee employee);
	
	
	/**
	 * 功能描述:新增一个经纪人
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月7日      新建
	 * </pre>
	 *
	 * @param agent
	 * @return
	 */
	public int insertAgent(LfAgent agent,byte[] img);
	
	
	public int updateAgent(LfAgent agent);
	
	/**
	 * 功能描述:修改经纪人
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月7日      新建
	 * </pre>
	 *
	 * @param agent
	 * @return
	 */
	public int updateAgent(LfAgent agent,byte[] img);
	
	/**
	 * 功能描述:根据id删除经纪人
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月7日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public int deleteAgentById(int id);
	
	/**
	 * 功能描述:根据手机号查询经纪人数量
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月11日      新建
	 * </pre>
	 *
	 * @param mobile
	 * @return
	 */
	public int getAgentCount(String mobile);
	
	/**
	 * 功能描述:TODO(描述这个方法的作用)
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月11日      新建
	 * </pre>
	 *
	 * @param pars
	 * @return
	 */
	
	//获取门店列表
	List<LfAgent> selectAgentListByPars(Map<String,Object> pars);
	
	/**
	 * 功能描述:查询转入经纪人，状态为1
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月29日      新建
	 * </pre>
	 *
	 * @param storeId
	 * @return
	 */
	public List<LfAgent> getAgentListByParms(int storeId);

    /**
     * 功能描述:最近带看列表TODO(描述这个方法的作用)
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     bianjie:   2015年5月11日      新建
     * </pre>
     *
     * @param pars
     * @return
     */
    public Map<String, Object> getAgentRecentlySeeList(Map<String, Object> pars,  LfEmployee employee);
    
    /**
     * 功能描述:获取门店的经纪人简单信息列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     CK:   2015年5月30日      新建
     * </pre>
     *
     * @param storeId
     * @return
     */
    List<MiniuiEntity> getAgentSimpleListByStoreId(Integer storeId);
    
    /**
     * 功能描述:获取经纪人评价明细信息
     * @param pars
     * @return
     */
    public Map<String, Object> getAgentEvalueteList(Map<String, Object> pars);
    
	
	    /**
     * 
    * @Title: getAgentById 
    * @Description: 通过ID获取agent信息
    * @param @param id
    * @param @return    设定文件 
    * @return LfAgentAllInfo    返回类型 
    * @throws
     */
    public LfAgentAllInfo getAgentById(Integer id);


	public Map<String, Object> getAgentLogList(Map<String, Object> pars);


	public Object getAgentByStoreOrg(Map pars);


    
}
