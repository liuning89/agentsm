package com.lifang.bzsm.console.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.HouseResourceReadMapper;
import com.lifang.agentsm.dao.read.LfAreaOrgReadMapper;
import com.lifang.agentsm.dao.read.LfCustomerReadMapper;
import com.lifang.agentsm.dao.write.LfCustomerWriteMapper;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.bzsm.console.entity.LfCustomer;
import com.lifang.bzsm.console.model.LfCustomerInfo;
import com.lifang.bzsm.console.model.LfCustomerRequest;
import com.lifang.bzsm.console.model.LfCustomerRequirementInfo;
import com.lifang.model.PageResponse;

@Service
public class LfCustomerService {

	@Autowired
	private LfCustomerReadMapper lfCustomerReadMapper;
    @Autowired
    private LfCustomerWriteMapper lfCustomerWriteMapper;

    @Autowired
    private AgentSOAServer agentSOAServer;

    @Autowired
    private LfAreaOrgReadMapper lfAreaOrgReadMapper;
    @Autowired
    private HouseResourceReadMapper houseRead;

    /**
     * 获取客户列表
     * @param req
     * @return
     */
    public PageResponse getCustomerList(LfCustomerRequest req, LfEmployee employee)
    {
        req.setPageIndex(req.getPageIndex() * req.getPageSize());
        List<Integer> agents = new ArrayList<Integer>();
        if (req.getAgentId() != null && !"".equals(req.getAgentId())) {

            agents.add(req.getAgentId());
            req.setAgentIds(agents);
        }else if (req.getStoreId() != null && !"".equals(req.getStoreId())) {
//            List<Integer> agents = new ArrayList<Integer>();
            List<Agent> agentCityList = agentSOAServer.getAgentList(null, null, req.getStoreId(), null, null);
            if (agentCityList == null || agentCityList.size()<= 0){
                agents.add(req.getAgentId());
                req.setAgentIds(agents);
            }else{
                for (Agent agentId : agentCityList){
                    agents.add(agentId.getId());

                }
                req.setAgentIds(agents);
            }
        }else if (req.getAreaId() != null && !"".equals(req.getAreaId())) {
            List<Agent> agentCityList = agentSOAServer.getAgentList(null, req.getAreaId(), null, null, null);
            if (agentCityList == null || agentCityList.size() <= 0){
                agents.add(req.getAgentId());
                req.setAgentIds(agents);
            }else{
                for (Agent agentId : agentCityList){
                    agents.add(agentId.getId());

                }
                req.setAgentIds(agents);
            }

        }else if(req.getCityId() != null && !"".equals(req.getCityId())) {
            int cityId = houseRead.findCityIdById(req.getCityId());
            req.setCityId(cityId);
            List<Agent> agentCityList = agentSOAServer.getAgentList(req.getCityId(), null, null, null, null);
            if (agentCityList == null || agentCityList.size() <= 0){
                agents.add(req.getAgentId());
                req.setAgentIds(agents);
            }else{
                for (Agent agentId : agentCityList){
                    agents.add(agentId.getId());
                }
                req.setAgentIds(agents);
            }
        }else if (employee.getCityId() == 1){

            List<Agent> agentCityList = agentSOAServer.getAgentList(null, null, null, null, null);
            for (Agent agentId : agentCityList){
                agents.add(agentId.getId());
            }
            req.setAgentIds(agents);
        }else{
            List<Agent> agentCityList = agentSOAServer.getAgentList(employee.getCityId(), null, null, null, null);
            for (Agent agentId : agentCityList){
                agents.add(agentId.getId());
            }
            req.setAgentIds(agents);
        }






//        List<Integer> agents = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSOAServer, lfAreaOrgReadMapper, employee);
//        req.setAgentIds(agents);

        List<LfCustomerInfo> customerInfos = lfCustomerReadMapper.selectCustomerList(req);

        for (LfCustomerInfo customerInfo : customerInfos) {
            customerInfo.setMobile("");
        }

        PageResponse pageResponse = new PageResponse("success", 1, customerInfos);
        pageResponse.setTotal(lfCustomerReadMapper.selectCustomerListCount(req));
        return pageResponse;
    }


    /**
     * 获取客户信息
     * @param customerId
     * @return
     */
    public LfCustomer selectByPK(Long customerId)
    {
        return lfCustomerReadMapper.selectByPrimaryKey(customerId);
    }

    /**
     * 获取客户列表
     * @param customerId
     * @return
     */
    public LfCustomerRequirementInfo selectCustomerReq(Long customerId)
    {
        return lfCustomerReadMapper.selectCustomerReq(customerId);
    }

    /**
     * 获取客户3A列表
     * @param req
     * @return
     */
    public PageResponse getCustomerAMarkList(LfCustomerRequest req)
    {
        req.setPageIndex(req.getPageIndex() * req.getPageSize());

        PageResponse pageResponse = new PageResponse("success", 1, lfCustomerReadMapper.selectCustomerAMarkList(req));
        pageResponse.setTotal(lfCustomerReadMapper.selectCustomerAMarkListCount(req));
        return pageResponse;
    }

    /**
     * 获取客户带看列表
     * @param req
     * @return
     */
    public PageResponse getCustomerHouseSeeList(LfCustomerRequest req)
    {
        req.setPageIndex(req.getPageIndex() * req.getPageSize());

        PageResponse pageResponse = new PageResponse("success", 1, lfCustomerReadMapper.selectCustomerHouseSee(req));
        pageResponse.setTotal(lfCustomerReadMapper.selectCustomerHouseSeeCount(req));
        return pageResponse;
    }

    /**
     * 获取客户跟进列表
     * @param req
     * @return
     */
    public PageResponse getCustomerFollowList(LfCustomerRequest req)
    {
        req.setPageIndex(req.getPageIndex() * req.getPageSize());

        PageResponse pageResponse = new PageResponse("success", 1, lfCustomerReadMapper.selectCustomerFollow(req));
        pageResponse.setTotal(lfCustomerReadMapper.selectCustomerFollowCount(req));
        return pageResponse;
    }

    /**
     * 获取当前经纪人是否有当前电话号码的经纪人
     * @param
     * @return
     */
    public int selectAgentCustomer(Map param)
    {
        return lfCustomerReadMapper.selectAgentCustomer(param);
    }

}
