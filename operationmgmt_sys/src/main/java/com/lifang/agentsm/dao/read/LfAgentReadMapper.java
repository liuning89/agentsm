package com.lifang.agentsm.dao.read;

import java.util.List;
import java.util.Map;

import com.lifang.agentsm.entity.LfAgent;
import com.lifang.agentsm.model.Employee;
import com.lifang.agentsm.model.LfAgentAllInfo;
import com.lifang.agentsm.model.LfAgentEvalueteInfo;
import com.lifang.agentsm.model.LfAgentLogListInfo;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.bzsm.console.model.AgentRequest;

public interface LfAgentReadMapper {
    LfAgent selectByPrimaryKey(Long id);
    
    //查询总条数
    int getAgentCount(Map<String, Object> pars);
    
    /**
     * 功能描述:条件查询获得经纪人列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     bianjie:   2015年5月7日      新建
     * </pre>
     *
     * @param pars
     * @return
     */
    List<LfAgentAllInfo> getAgentList(Map<String, Object> pars);
    /**
     * @author fangyouhui
     * @param pars storeId   companyId
     * @return
     */
    List<LfAgent> selectAgentListByPars(Map<String, Object> pars);

    Integer getAgentRecentlySeeCount(Map<String,Object> params);

    /**
     * 最近带看记录
     * */
    List<Map<String,Object>> getAgentRecentlySeeList(Map<String, Object> params);
    
    /**
     * 功能描述:获取门店经纪人简单信息列表
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
     * 功能描述:根据条件查询经纪人评价明细
     *
     * <pre>
     *     luogq:   2015年6月5日      新建
     * </pre>
     *
     * @param pars
     * @return
     */
    List<LfAgentEvalueteInfo> getAgentEvaluete(Map<String, Object> pars);
    
    //查询总条数
    int getAgentEvalueteCount(Map<String, Object> pars);
	
	
	
	
	    /**
     * 
    * @Title: getAgentById 
    * @Description: 通过ID获取agent
    * @param @param id
    * @param @return    设定文件 
    * @return LfAgentAllInfo    返回类型 
    * @throws
     */
    LfAgentAllInfo getAgentById(Integer id);

	int getAgentLogListCount(Map<String, Object> pars);

	List<LfAgentLogListInfo> getAgentLogList(Map<String, Object> pars);

	List<Employee> getAgentByStoreOrg(Map pars);

    /***
     *
     * @Title: getAgentListByOrg
     * @Description: 根据组织获取经纪人列表
     * @param @param par
     * @param @return    设定文件
     * @return List<LfAgentAllInfo>    返回类型
     * @throws
     */
    List<LfAgentAllInfo> getAgentListByOrg(AgentRequest par);
    /***
     *
     * @Title: getAgentListByOrg
     * @Description: 根据组织获取经纪人列表
     * @param @param par
     * @param @return    设定文件
     * @return List<LfAgentAllInfo>    返回类型
     * @throws
     */
    int getAgentListByOrgCount(AgentRequest par);
    
}