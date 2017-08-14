/**
 *
 */
package com.lifang.agentsm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lifang.agentsm.dao.read.LfAreaOrgReadMapper;
import com.lifang.agentsm.entity.LfAreaOrg;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.agentsoa.model.AreaOrg;
import com.lifang.agentsoa.model.AreaOrgModel;
import com.lifang.agentsoa.model.PositionInfo;
import com.lifang.agentsoa.model.enums.SystemInfo;

/**
 * @author cq
 */
public class OrgCodeUtil {

    /**
     * 根据CODE获取当前经纪人权限下的所有经纪人
     * @param code
     * @param server
     * @param lfAreaOrgReadMapper
     * @param employee
     * @return
     */
    public static List<Integer> getEmployeeIdsByOrgCode(String code, AgentSOAServer server, LfAreaOrgReadMapper lfAreaOrgReadMapper, LfEmployee employee) {

        List<Integer> resultList = null;

        if(code != null && code.length() > 3)
        {
            List<Agent> agents = null;
            Agent tempagent;
            if(code.length() > 12 )
            {
                code = code.substring(0,12);
            }

            if(code.length() == 12)
            {
                Agent agent = server.getAgent(employee.getId());
                if(agent == null)
                {
                    agents =  server.getAgentList(null, null, null, code, null);
                }
                else
                {
                    if(agent.getStorePosition() != null && agent.getStorePosition().intValue() == 1)
                    {
                        agents =  server.getAgentList(null, null, null, code, null);
                    }
                }
            }
            else
            {
                LfAreaOrg valudOrg = lfAreaOrgReadMapper.selectByCode(code);
                if(valudOrg != null)
                {
                    if (code.length() == 6)
                    {
                        agents =  server.getAgentList(valudOrg.getCityId(), null, null, null, null);
                    }
                    else if(code.length() == 9)
                    {
                        agents =  server.getAgentList(null, valudOrg.getId(), null, null, null);
                    }
                }
            }

            if(agents != null && agents.size() > 0)
            {
                resultList = new ArrayList<Integer>();
                for (int i = 0; i < agents.size(); i++) {
                    tempagent = agents.get(i);
                    resultList.add(tempagent.getId());
                }
            }
            else
            {
                resultList = new ArrayList<Integer>();
                resultList.add(employee.getId());
            }

            return resultList;

        }
        else
        {
            return null;
        }
    }

    /***
     * 根据Ids获取当前经纪人权限下的所有经纪人
     * @param cityOrgId
     * @param areaOrgId
     * @param storeOrgId
     * @param server
     * @param lfAreaOrgReadMapper
     * @param employee
     * @return
     */
    public static List<Integer> getEmployeeIdsByOrgIds(Integer cityOrgId, Integer areaOrgId, Integer storeOrgId, AgentSOAServer server, LfAreaOrgReadMapper lfAreaOrgReadMapper,  LfEmployee employee,Integer wpid) {

        List<Integer> resultList = null;
        List<Agent> agents = null;

        PositionInfo info = server.getEmployeePositionInfo(employee.getId(), SystemInfo.OperationManagementSystem);

        Integer rCityOrgId = info.getCityAreaOrgId();
        Integer rAreaOrgId = info.getAreaOrgId();
        Integer rStoreOrgId = info.getDoorAreaOrgId();


        if(cityOrgId != null && cityOrgId > 0)
        {
            rCityOrgId = cityOrgId;
        }

        if(areaOrgId != null && areaOrgId > 0)
        {
            rAreaOrgId = areaOrgId;
        }

        if(storeOrgId != null && storeOrgId > 0)
        {
            rStoreOrgId = storeOrgId;
        }

        //根据组织架构的ID获取经纪人列表
        if(rStoreOrgId != null && rStoreOrgId > 0) {
            if(info.getStorePosition() != null && info.getStorePosition().intValue() == 2)
            {
                agents = null;
            }
            else
            {
                agents =  server.getAgentList(null, null, rStoreOrgId, null, null, wpid);
            }
        }
        else if(rAreaOrgId != null && rAreaOrgId > 0) {
            agents =  server.getAgentList(null, rAreaOrgId, null, null, null, wpid);
        }
        else if(rCityOrgId != null && rCityOrgId > 0) {
            LfAreaOrg valudOrg = lfAreaOrgReadMapper.selectByPrimaryKey(rCityOrgId.longValue());
            if(valudOrg != null)
            {
                agents =  server.getAgentList(valudOrg.getCityId(), null, null, null, null, wpid);
            }
        }
        else
        {
            agents =  server.getAgentList(null, null, null, null, null, wpid);
        }

        Agent tempagent;
        if(agents != null && agents.size() > 0)
        {
            resultList = new ArrayList<Integer>();
            for (int i = 0; i < agents.size(); i++) {
                tempagent = agents.get(i);
                resultList.add(tempagent.getId());
            }
        }
        else
        {
            resultList = new ArrayList<Integer>();
           // resultList.add(employee.getId());
        }

        return resultList;
    }



    /**
     * 获取登录人员有权限的门店列表
     * @param server
     * @param employee
     * @return
     */
    public static List<Integer> getStoreListByEmployeeId(Integer cityId, Integer areaId, AgentSOAServer server, LfEmployee employee)
    {
        AreaOrgModel model = server.getEmployeeAreaOrgsMap(employee.getId(), SystemInfo.OperationManagementSystem);

        //获取城市列表
        List<AreaOrg> city = model.getCityAreaOrgs();

        //封装区域列表
        List<AreaOrg> area = new ArrayList<AreaOrg>();
        Map<Integer, List<AreaOrg>> modelMap = model.getMap();

        List<AreaOrg> resultOrg = new ArrayList<AreaOrg>();

        if(areaId != null && areaId > 0)
        {
            if(modelMap.containsKey(areaId) && modelMap.get(areaId) != null)
            {
                resultOrg.addAll(modelMap.get(areaId));
            }
        }
        else if(cityId != null && cityId > 0)
        {
            if(modelMap.containsKey(cityId) && modelMap.get(cityId) != null)
            {
                area.addAll(modelMap.get(cityId));
            }

            //封装门店列表
            for (AreaOrg areaOrg : area) {
                if(modelMap.containsKey(areaOrg.getId()) && modelMap.get(areaOrg.getId()) != null)
                {
                    resultOrg.addAll(modelMap.get(areaOrg.getId()));
                }
            }
        }
        else
        {
            for (AreaOrg areaOrg : city) {
                if(modelMap.containsKey(areaOrg.getId()) && modelMap.get(areaOrg.getId()) != null)
                {
                    area.addAll(modelMap.get(areaOrg.getId()));
                }
            }

            //封装门店列表
            for (AreaOrg areaOrg : area) {
                if(modelMap.containsKey(areaOrg.getId()) && modelMap.get(areaOrg.getId()) != null)
                {
                    resultOrg.addAll(modelMap.get(areaOrg.getId()));
                }
            }
        }


        List<Integer> resultIds = new ArrayList<Integer>();
        if(resultOrg != null && resultOrg.size() > 0)
        {
            for (AreaOrg areaOrg : resultOrg) {
                resultIds.add(areaOrg.getId());
            }
        }
        else
        {
            resultIds.add(0);
        }

        return resultIds;

    }



    /**
     * 获取登录人员有权限的区域列表
     * @param server
     * @param employee
     * @return
     */
    public static List<Integer> getAreadListByEmployeeId(Integer cityId, AgentSOAServer server, LfEmployee employee)
    {
        AreaOrgModel model = server.getEmployeeAreaOrgsMap(employee.getId(), SystemInfo.OperationManagementSystem);

        //获取城市列表
        List<AreaOrg> city = model.getCityAreaOrgs();

        //封装区域列表
        List<AreaOrg> area = new ArrayList<AreaOrg>();
        Map<Integer, List<AreaOrg>> modelMap = model.getMap();

        if(cityId != null && cityId > 0)
        {
            if(modelMap.containsKey(cityId) && modelMap.get(cityId) != null)
            {
                area.addAll(modelMap.get(cityId));
            }
        }
        else
        {
            for (AreaOrg areaOrg : city) {
                if(modelMap.containsKey(areaOrg.getId()) && modelMap.get(areaOrg.getId()) != null)
                {
                    area.addAll(modelMap.get(areaOrg.getId()));
                }
            }
        }


        List<Integer> resultIds = new ArrayList<Integer>();
        if(area != null && area.size() > 0)
        {
            for (AreaOrg areaOrg : area) {
                resultIds.add(areaOrg.getId());
            }
        }
        else
        {
            resultIds.add(0);
        }

        return resultIds;

    }

    /**
     * 获取登录人员有权限的城市区域门店AreaOrg列表
     * @param server
     * @param employee
     * @return
     */
    public Map<String, List<AreaOrg>> getCityAreaStoreListByEmployeeList(AgentSOAServer server, LfEmployee employee)
    {
        AreaOrgModel model = server.getEmployeeAreaOrgsMap(employee.getId(), SystemInfo.OperationManagementSystem);

        //获取城市列表
        List<AreaOrg> city = model.getCityAreaOrgs();

        //封装区域列表
        List<AreaOrg> area = new ArrayList<AreaOrg>();
        Map<Integer, List<AreaOrg>> modelMap = model.getMap();

        for (AreaOrg areaOrg : city) {
            if(modelMap.containsKey(areaOrg.getId()) && modelMap.get(areaOrg.getId()) != null)
            {
                area.addAll(modelMap.get(areaOrg.getId()));
            }
        }

        //封装门店列表
        List<AreaOrg> store = new ArrayList<AreaOrg>();
        for (AreaOrg areaOrg : area) {
            if(modelMap.containsKey(areaOrg.getId()) && modelMap.get(areaOrg.getId()) != null)
            {
                store.addAll(modelMap.get(areaOrg.getId()));
            }
        }


        Map<String, List<AreaOrg>> resultMap = new HashMap<String, List<AreaOrg>>();
        resultMap.put("city", city);
        resultMap.put("area", area);
        resultMap.put("store", store);

        return resultMap;

    }


    /**
     * 获取登录人员有权限的城市区域门店AreaOrg  Id 列表
     * @param server
     * @param employee
     * @return
     */
    public Map<String, List<Integer>> getCityAreaStoreIdByEmployeeList(AgentSOAServer server, LfEmployee employee)
    {
        /**
         * 获取城市详情
         */
        Map<String, List<AreaOrg>> mapmodel = getCityAreaStoreListByEmployeeList(server, employee);
        List<AreaOrg> city = mapmodel.get("city");
        List<AreaOrg> area = mapmodel.get("area");
        List<AreaOrg> store = mapmodel.get("store");

        /**
         * 计算城市ID列表
         */
        List<Integer> cityIds = new ArrayList<Integer>();
        List<Integer> areaIds = new ArrayList<Integer>();
        List<Integer> storeIds = new ArrayList<Integer>();

        for (AreaOrg areaOrg : city) {
            cityIds.add(areaOrg.getId());
        }

        for (AreaOrg areaOrg : area) {
            areaIds.add(areaOrg.getId());
        }

        for (AreaOrg areaOrg : store) {
            storeIds.add(areaOrg.getId());
        }

        /**
         * 组建返回值
         */
        Map<String, List<Integer>> resultMap = new HashMap<String, List<Integer>>();
        resultMap.put("cityIds", cityIds);
        resultMap.put("areaIds", areaIds);
        resultMap.put("storeIds", storeIds);

        return resultMap;

    }

    /**
     * 判断当前登录人是否经纪人
     * @param server
     * @param employee
     * @return
     */
    public static boolean isAgent(AgentSOAServer server, LfEmployee employee)
    {
        boolean isagent = false;
        Agent agent = server.getAgent(employee.getId());
        if(agent != null)
        {
            if(agent.getStorePosition() != null && agent.getStorePosition().intValue() == 2)
            {
                isagent = true;
            }
        }
        return isagent;
    }



}
