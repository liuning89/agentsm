package com.lifang.agentsm.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lifang.agentsm.dao.read.LfAreaOrgReadMapper;
import com.lifang.agentsm.dao.read.OperatingSystemMapper;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.AgentAccountDetailDTO;
import com.lifang.agentsm.model.AgentWkCoinResultModel;
import com.lifang.agentsm.model.AgentWkCoinSearchModel;
import com.lifang.agentsm.service.OperatingSystemService;
import com.lifang.agentsm.utils.OrgCodeUtil;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.model.PageResponse;
import com.lifang.model.Response;
import com.lifang.paysoa.common.enums.PaySide;
import com.lifang.paysoa.facade.WalletFacadeService;
import com.lifang.paysoa.model.request.BaseRequest;
import com.lifang.paysoa.model.vo.GuestBalanceVo;
@Service
public class OperatingSystemServiceImpl implements OperatingSystemService {

	@Resource
	private OperatingSystemMapper operatingSystemMapper;
	
	@Resource
	private AgentSOAServer agentSOAServer;

    @Autowired
    private LfAreaOrgReadMapper lfAreaOrgReadMapper;
    
    @Autowired
    private WalletFacadeService walletFacadeService;

	@Override
	public Map<String, Object> queryAgentWKCoinRewardList(AgentWkCoinSearchModel searchModel,LfEmployee employee){
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		List<AgentWkCoinResultModel> resultModels = new ArrayList<>();
		int startIndex = searchModel.getPageIndex() * searchModel.getPageSize();
		int pageSize = searchModel.getPageSize();
		List<Integer> agentIdsTotal = OrgCodeUtil.getEmployeeIdsByOrgIds(null, null, null, agentSOAServer, lfAreaOrgReadMapper, employee, searchModel.getCompanyId());
		if(null != searchModel.getAgentPhoneNumber() && !"".equals(searchModel.getAgentPhoneNumber())){
			List<String> mobiles = new ArrayList<String>();
			mobiles.add(searchModel.getAgentPhoneNumber());
			List<Agent> mobileAgent = agentSOAServer.getAgentsByMobiles(mobiles);
			if(null == mobileAgent.get(0))
				return null;
			List<Integer> agentIdsPhone = new ArrayList<>();
			agentIdsPhone.add(mobileAgent.get(0).getId());
			agentIdsTotal.retainAll(agentIdsPhone);
		}
		if(CollectionUtils.isEmpty(agentIdsTotal))
			return null;
		String agentIds = convertAgentListToString(agentIdsTotal);
		List<AgentAccountDetailDTO> accountDetailDTOs = new ArrayList<>();
		total = operatingSystemMapper.countAgentAccountDetatilDTOs(searchModel.getStartTime(), searchModel.getEndTime()+" 23:59:59",agentIds,searchModel.getRewardReson());
		accountDetailDTOs = operatingSystemMapper.queryAgentAccountDetatilDTOs(searchModel.getStartTime(), searchModel.getEndTime()+" 23:59:59", agentIds,searchModel.getRewardReson(),startIndex, pageSize);
		if(CollectionUtils.isEmpty(accountDetailDTOs))
			return null;
		for(AgentAccountDetailDTO accountDetailDTO : accountDetailDTOs){
			AgentWkCoinResultModel resultModel = new AgentWkCoinResultModel();
			Agent agent = agentSOAServer.getAgent(accountDetailDTO.getAgentId());
			if(null != agent){
				resultModel.setCompanyName(agent.getFranchiseeCompanyName());
				resultModel.setCityName(agent.getCityName());
				resultModel.setAreaOrgName(agent.getAreaOrgName());
				resultModel.setStoreName(agent.getStoreName());
				resultModel.setAgentName(agent.getName());
			}
			resultModel.setWuKongCoin(accountDetailDTO.getWuKongCoin()+"");
			resultModel.setRewardTime(accountDetailDTO.getCreateTime());
			resultModel.setRewardReson(accountDetailDTO.getOrderName());
			resultModels.add(resultModel);
		}
		map.put("total", total);
		map.put("data", resultModels);
		return map;
	}

	private String convertAgentListToString(List<Integer> agentList){
		String agentIds = "";
		for(Integer agentId:agentList){
			agentIds += agentId +",";
		}
		return agentIds.substring(0,agentIds.length()-1);
	}
	@Override
	public PageResponse<List<AgentWkCoinResultModel>> queryAgentWKCoinSurplusList(AgentWkCoinSearchModel searchModel,
			LfEmployee employee) {
		List<AgentWkCoinResultModel> listResult = new ArrayList<AgentWkCoinResultModel>();
		List<BaseRequest> queryList = new ArrayList<>();
		PageResponse<List<AgentWkCoinResultModel>> pageResponse = new PageResponse<List<AgentWkCoinResultModel>>("success",1,listResult);
		int startIndex = searchModel.getPageIndex() * searchModel.getPageSize();
		int pageSize = searchModel.getPageSize();
		if(null != searchModel.getAgentId()){
			Agent agent = agentSOAServer.getAgent(searchModel.getAgentId());
			if(null == agent)
				return pageResponse;
			if(!isSearchResultRight(agent, searchModel))
				return pageResponse;
			else {
				AgentWkCoinResultModel resultModel = new AgentWkCoinResultModel();
				resultModel.setAgentName(agent.getName());
				resultModel.setCompanyName(agent.getFranchiseeCompanyName());
				resultModel.setCityName(agent.getCityName());
				resultModel.setAreaOrgName(agent.getAreaOrgName());
				resultModel.setStoreName(agent.getStoreName());
				BaseRequest baseRequest = new BaseRequest();
				baseRequest.setGuestId(Long.valueOf(agent.getId()));
				baseRequest.setPaySide(PaySide.AGENT);
				queryList.add(baseRequest);
				Response<List<GuestBalanceVo>> balanceResults = walletFacadeService.batchWalletBalance(queryList);
//				int blance = operatingSystemMapper.getAgentBlanceByAgentId(agent.getId());
				resultModel.setWuKongCoin(balanceResults.getData().get(0).getTotalBalance()/100 + "");
				listResult.add(resultModel);
				pageResponse.setData(listResult);
				pageResponse.setTotal(1);
			}
		}else {
			List<Integer> agentIdsTotal = OrgCodeUtil.getEmployeeIdsByOrgIds(searchModel.getCityId(), searchModel.getAreaId(), searchModel.getStoreId(), agentSOAServer, lfAreaOrgReadMapper, employee, searchModel.getCompanyId());
			if(null != searchModel.getAgentPhoneNumber()){
				List<String> mobiles = new ArrayList<String>();
				mobiles.add(searchModel.getAgentPhoneNumber());
//				Agent agent = agentSOAServer.getAgent(searchModel.getAgentPhoneNumber());
//				mobileAgent.add(agent);
				List<Agent> mobileAgent = agentSOAServer.getAgentsByMobiles(mobiles);
				mobileAgent.get(0);
				if(CollectionUtils.isEmpty(mobileAgent) || null == mobileAgent.get(0))
					return pageResponse;
				List<Integer> agentIdsPhone = new ArrayList<>();
				agentIdsPhone.add(mobileAgent.get(0).getId());
				agentIdsTotal.retainAll(agentIdsPhone);
			}
			if(CollectionUtils.isEmpty(agentIdsTotal))
				return pageResponse;
			List<Agent> agentsTotal = agentSOAServer.getAgentInfoByIds(agentIdsTotal);
//			List<Agent> agentsTotal = agentSOAServer.getAllAgentList(searchModel.getCityId(), searchModel.getAreaId(), searchModel.getStoreId(), null, null, searchModel.getCompanyId());
			if(CollectionUtils.isEmpty(agentsTotal))
				return pageResponse;
			//伪分页
			if(agentIdsTotal.size() <= startIndex)
				return pageResponse;
			List<Agent> fakeAgents = new ArrayList<>();
			fakePage(agentsTotal, fakeAgents, startIndex, pageSize);
			convertAgentsToBaseRequests(fakeAgents, queryList);
			Response<List<GuestBalanceVo>> balanceResults = walletFacadeService.batchWalletBalance(queryList);
			convertGuestBalanceVoListToAgentWkCoinResultModelList(balanceResults.getData(), listResult);
	        pageResponse.setData(listResult);
	        pageResponse.setTotal(agentsTotal.size());
		}
		return pageResponse;
	}
	
	private boolean isSearchResultRight(Agent agent,AgentWkCoinSearchModel searchModel){
		if(null != searchModel.getCompanyId() && searchModel.getCompanyId() != agent.getFranchiseeId())
			return false;
		return true;
	}
	
	private List<AgentWkCoinResultModel> getAgentWkCoinSurplusByAgentId(Integer agentId){
		List<AgentWkCoinResultModel> resultModels = new ArrayList<>();
		List<BaseRequest> queryList = new ArrayList<>();
		Agent agent = agentSOAServer.getAgent(agentId);
		if(null == agent)
			return null;
		AgentWkCoinResultModel resultModel = new AgentWkCoinResultModel();
		resultModel.setAgentName(agent.getName());
		resultModel.setCompanyName(agent.getFranchiseeCompanyName());
		resultModel.setCityName(agent.getCityName());
		resultModel.setAreaOrgName(agent.getAreaOrgName());
		resultModel.setStoreName(agent.getStoreName());
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setGuestId(Long.valueOf(agent.getId()));
		baseRequest.setPaySide(PaySide.AGENT);
		queryList.add(baseRequest);
		Response<List<GuestBalanceVo>> balanceResults = walletFacadeService.batchWalletBalance(queryList);
		resultModel.setWuKongCoin(balanceResults.getData().get(0).getTotalBalance()/100 + "");
		resultModels.add(resultModel);
		return resultModels;
	}
	
	private void fakePage(List<Agent> totalAgents,List<Agent> fakeAgents,int startIndex,int pageSize){
		for(int i = startIndex;i < totalAgents.size();i++){
			if(startIndex+pageSize == i)
				break;
			fakeAgents.add(totalAgents.get(i));
		}
	}
	
	private void convertGuestBalanceVoListToAgentWkCoinResultModelList(List<GuestBalanceVo> balanceList,List<AgentWkCoinResultModel> listResult){
		for(GuestBalanceVo balanceVo : balanceList){
			AgentWkCoinResultModel resultModel = new AgentWkCoinResultModel();
			Agent agent = agentSOAServer.getAgent(balanceVo.getGuestId().intValue());
			if(null == agent)
				continue;
			resultModel.setAgentName(agent.getName());
			resultModel.setCompanyName(agent.getFranchiseeCompanyName());
			resultModel.setCityName(agent.getCityName());
			resultModel.setAreaOrgName(agent.getAreaOrgName());
			resultModel.setStoreName(agent.getStoreName());
			resultModel.setWuKongCoin(balanceVo.getTotalBalance()/100 + "");
			listResult.add(resultModel);
		}
	}
	
	
	/**
	 * 
	 * @param agentIds
	 * @param queryList
	 */
	private void convertAgentsToBaseRequests(List<Agent> agents,List<BaseRequest> queryList){
		for(Agent agent : agents){
			BaseRequest baseRequest = new BaseRequest();
			baseRequest.setGuestId(Long.valueOf(agent.getId()));
			baseRequest.setPaySide(PaySide.AGENT);
			queryList.add(baseRequest);
		}
	}

	private void convertAgentIdsToBaseRequests(List<Integer> agentIds,List<BaseRequest> queryList){
		for(Integer agentId : agentIds){
			BaseRequest baseRequest = new BaseRequest();
			baseRequest.setGuestId(agentId.longValue());
			baseRequest.setPaySide(PaySide.AGENT);
			queryList.add(baseRequest);
		}
	}
	@Override
	public PageResponse<String> getAgentWKCoinSurplusTotal(AgentWkCoinSearchModel searchModel,LfEmployee employee) {
		List<BaseRequest> queryList = new ArrayList<>();
		PageResponse<String> pageResponse = new PageResponse<String>("success",1,"0");
		if(null != searchModel.getAgentId()){
			Agent agent = agentSOAServer.getAgent(searchModel.getAgentId());
			if(null == agent)
				return pageResponse;
			if(!isSearchResultRight(agent, searchModel))
				return pageResponse;
			BaseRequest baseRequest = new BaseRequest();
			baseRequest.setGuestId(agent.getId().longValue());
			baseRequest.setPaySide(PaySide.AGENT);
			queryList.add(baseRequest);
		}else {
			List<Integer> agentIdsTotal = OrgCodeUtil.getEmployeeIdsByOrgIds(searchModel.getCityId(), searchModel.getAreaId(), searchModel.getStoreId(), agentSOAServer, lfAreaOrgReadMapper, employee, searchModel.getCompanyId());
			if(null != searchModel.getAgentPhoneNumber()){
				List<String> mobiles = new ArrayList<String>();
				mobiles.add(searchModel.getAgentPhoneNumber());
				List<Agent> mobileAgent = agentSOAServer.getAgentsByMobiles(mobiles);
				if(CollectionUtils.isEmpty(mobileAgent) || null == mobileAgent.get(0))
					return pageResponse;
				List<Integer> agentIdsPhone = new ArrayList<>();
				agentIdsPhone.add(mobileAgent.get(0).getId());
				agentIdsTotal.retainAll(agentIdsPhone);
			}
			convertAgentIdsToBaseRequests(agentIdsTotal, queryList);
		}
		Response<Integer> sumBalance = walletFacadeService.sumUserBalance(queryList);
		if(null == sumBalance.getData())
			return pageResponse;
		pageResponse.setData(""+sumBalance.getData()/100);
		return pageResponse;
	}

	@Override
	public List<AgentWkCoinResultModel> agentWkCoinSurplusExport(AgentWkCoinSearchModel searchModel,
			LfEmployee employee) {
		if(null != searchModel.getAgentId())
			return getAgentWkCoinSurplusByAgentId(searchModel.getAgentId());
		List<AgentWkCoinResultModel> resultModels = new ArrayList<>();
		List<BaseRequest> queryList = new ArrayList<>();
		List<Integer> agentIdsTotal = OrgCodeUtil.getEmployeeIdsByOrgIds(searchModel.getCityId(), searchModel.getAreaId(), searchModel.getStoreId(), agentSOAServer, lfAreaOrgReadMapper, employee, searchModel.getCompanyId());
		if(null != searchModel.getAgentPhoneNumber() && !"".equals(searchModel.getAgentPhoneNumber())){
			List<String> mobiles = new ArrayList<String>();
			mobiles.add(searchModel.getAgentPhoneNumber());
			List<Agent> mobileAgent = agentSOAServer.getAgentsByMobiles(mobiles);
			if(CollectionUtils.isEmpty(mobileAgent) || null == mobileAgent.get(0))
				return null;
			List<Integer> agentIdsPhone = new ArrayList<>();
			agentIdsPhone.add(mobileAgent.get(0).getId());
			agentIdsTotal.retainAll(agentIdsPhone);
		}
		if(CollectionUtils.isEmpty(agentIdsTotal))
			return null;
		convertAgentIdsToBaseRequests(agentIdsTotal, queryList);
		Response<List<GuestBalanceVo>> balanceResults = walletFacadeService.batchWalletBalance(queryList);
		convertGuestBalanceVoListToAgentWkCoinResultModelList(balanceResults.getData(), resultModels);
		return resultModels;
	}
}
