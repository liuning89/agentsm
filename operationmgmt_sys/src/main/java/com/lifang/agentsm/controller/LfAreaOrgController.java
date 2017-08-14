package com.lifang.agentsm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfAreaOrg;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.service.LfAreaOrgService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.AreaOrg;
import com.lifang.agentsoa.model.AreaOrgModel;
import com.lifang.agentsoa.model.PositionInfo;
import com.lifang.agentsoa.model.enums.SystemInfo;
import com.lifang.sso.SsoClientUtil;
import com.lifang.sso.entity.SsoUser;

/**
 * Created by Administrator on 15-7-20.
 */
@Controller
@RequestMapping("/areaorg")
public class LfAreaOrgController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LfAreaOrgService lfAreaOrgService;

    
    @Autowired
    private AgentSOAServer agentSOAServer;

    @Autowired
    private SsoClientUtil ssoClient;
    /**
     * 根据参数获取区域、门店等信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getList.action",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<LfAreaOrg> getList(HttpServletRequest request) {
        HashMap<String,	Object> param = new HashMap<String,Object>();
        String cityId = request.getParameter("cityId");
        String level = request.getParameter("level");
        String code = request.getParameter("code");
        param.put("level", level);
        param.put("cityId", cityId);
        param.put("code", code);

        return lfAreaOrgService.selectBy(param);
    }
    
    /**
     * 根据参数获取区域、门店等信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getLists.action", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<LfAreaOrg> getLists(HttpServletRequest request) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        String cityId = request.getParameter("cityId");
        String level = request.getParameter("level");
        String parentLevel = request.getParameter("parentLevel");
        String parentId = request.getParameter("parentId");
        LfAreaOrg org = lfAreaOrgService.selectByCode(parentId);
        param.put("level", level);
        if (org != null) {
            param.put("parentId", org.getId());
        }
        param.put("cityId", cityId);
        param.put("parentLevel", parentLevel);
        return lfAreaOrgService.selectBy(param);
    }
    
    @RequestMapping(value = "/getAgent.action", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<LfAreaOrg> getAgent(HttpServletRequest request) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        String parentId = request.getParameter("parentId");


        param.put("parentId", parentId);
        return lfAreaOrgService.selectAgent(param);
    }
    
    /**
     * 根据参数获取区域、门店等信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getListByLogin.action", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<LfAreaOrg> getListByLogin(HttpServletRequest request) {
//        HashMap<String, Object> param = new HashMap<String, Object>();
//        String level = request.getParameter("level");
//        String parentId = request.getParameter("parentId");
//        LfAreaOrg org = lfAreaOrgService.selectByCode(parentId);
//        param.put("level", level);
//        if (org != null) {
//            param.put("parentId", org.getId());
//        }
//        
//        List<LfAreaOrg> orglist = lfAreaOrgService.selectBy(param);
//        
//        LfEmployee loginEmp = getLoginEmployeeInfo(request.getSession());
////        PositionCache cache = areaOrgPriSOAClient.getShowNameByPositionId(loginEmp.getPositionId());
//        PositionInfo cache = agentSOAServer.getEmployeePositionInfo(loginEmp.getId());
//        Integer lastOrgId = cache.getCityAreaOrgId();
//        if(cache.getAreaOrgId() != null && cache.getAreaOrgId() != 0)
//        {
//            lastOrgId = cache.getAreaOrgId();
//        }
//        
//        if(cache.getDoorAreaOrgId() != null && cache.getDoorAreaOrgId() != 0)
//        {
//            lastOrgId = cache.getDoorAreaOrgId();
//        }
//        
//        if(lastOrgId != null && lastOrgId != 0)
//        {
//            //获取组织code
//            LfAreaOrg codeorg = lfAreaOrgService.selectByPK(lastOrgId);
//            String lastCode = codeorg.getCode();
//            
//            //如果组织小于员工组织那么只加载当前员工所在的组织
//            List<LfAreaOrg> orglistNew = new ArrayList<LfAreaOrg>();
//            for (int i = 0; i < orglist.size(); i++) {
//                if(orglist.get(i).getCode().length() <= lastCode.length())
//                {
//                    if(lastCode.startsWith(orglist.get(i).getCode()))
//                    {
//                        orglistNew.add(orglist.get(i));
//                    }
//                }
//                else
//                {
//                    if(orglist.get(i).getCode().startsWith(lastCode))
//                    {
//                        orglistNew.add(orglist.get(i));
//                    }
//                }
//            }
//            return orglistNew;
//        }
//        else
//        {
//            return orglist;
//        }
    	
    	HashMap<String, Object> param = new HashMap<String, Object>();
        String level = request.getParameter("level");
        String parentId = request.getParameter("parentId");
        LfAreaOrg org = lfAreaOrgService.selectByCode(parentId);
        param.put("level", level);
        param.put("parentId", parentId);
        param.put("type", request.getParameter("type"));
        if (org != null) {
            param.put("parentId", org.getId());
        }
        
        List<LfAreaOrg> orglist = lfAreaOrgService.selectBy(param);

        
        LfEmployee loginEmp= new LfEmployee();
		try {
			loginEmp = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        PositionInfo cache = agentSOAServer.getEmployeePositionInfo(loginEmp.getId(),SystemInfo.OperationManagementSystem);
        
        Integer lastOrgId = cache.getCityAreaOrgId();
        if(cache.getAreaOrgId() != null && cache.getAreaOrgId() != 0)
        {
            lastOrgId = cache.getAreaOrgId();
        }
        
        if(cache.getDoorAreaOrgId() != null && cache.getDoorAreaOrgId() != 0)
        {
            lastOrgId = cache.getDoorAreaOrgId();
        }
        
        if(lastOrgId != null && lastOrgId != 0)
        {
            //获取组织code
            LfAreaOrg codeorg = lfAreaOrgService.selectByPK(lastOrgId);
            String lastCode = codeorg.getCode();
            
            //如果组织小于员工组织那么只加载当前员工所在的组织
            List<LfAreaOrg> orglistNew = new ArrayList<LfAreaOrg>();
            for (int i = 0; i < orglist.size(); i++) {
                if(orglist.get(i).getCode().length() <= lastCode.length())
                {
                    if(lastCode.startsWith(orglist.get(i).getCode()))
                    {
                        orglistNew.add(orglist.get(i));
                    }
                }
                else
                {
                    if(orglist.get(i).getCode().startsWith(lastCode))
                    {
                        orglistNew.add(orglist.get(i));
                    }
                }
            }
            return orglistNew;
        }
        else
        {
            return orglist;
        }
    }
    //获取加盟商区域
    /**
     * 根据参数获取区域、门店等信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getFranchiseeListByLogin.action", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<LfAreaOrg> getFranchiseeListByLogin(HttpServletRequest request) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        String level = request.getParameter("level");
        String parentId = request.getParameter("parentId");
//        LfAreaOrg org = lfAreaOrgService.selectByCode(parentId);
        LfAreaOrg org = lfAreaOrgService.selectByParentId(parentId);
        param.put("level", level);
        if (org != null) {
            param.put("parentId", org.getId());
        }
//        String type = "";
//        if("70".equals(level)){
//        	type = "2";
//        }
//        param.put("type",type);
        
        List<LfAreaOrg> orglist = lfAreaOrgService.selectByType(param);
        
        LfEmployee loginEmp= new LfEmployee();
		try {
			loginEmp = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        PositionInfo cache = agentSOAServer.getEmployeePositionInfo(loginEmp.getId(),SystemInfo.OperationManagementSystem);
        
        Integer lastOrgId = cache.getCityAreaOrgId();
        if(cache.getAreaOrgId() != null && cache.getAreaOrgId() != 0)
        {
            lastOrgId = cache.getAreaOrgId();
        }
        
        if(cache.getDoorAreaOrgId() != null && cache.getDoorAreaOrgId() != 0)
        {
            lastOrgId = cache.getDoorAreaOrgId();
        }
        
        if(lastOrgId != null && lastOrgId != 0)
        {
            //获取组织code
            LfAreaOrg codeorg = lfAreaOrgService.selectByPK(lastOrgId);
            String lastCode = codeorg.getCode();
            
            //如果组织小于员工组织那么只加载当前员工所在的组织
            List<LfAreaOrg> orglistNew = new ArrayList<LfAreaOrg>();
            for (int i = 0; i < orglist.size(); i++) {
                if(orglist.get(i).getCode().length() <= lastCode.length())
                {
                    if(lastCode.startsWith(orglist.get(i).getCode()))
                    {
                        orglistNew.add(orglist.get(i));
                    }
                }
                else
                {
                    if(orglist.get(i).getCode().startsWith(lastCode))
                    {
                        orglistNew.add(orglist.get(i));
                    }
                }
            }
            return orglistNew;
        }
        else
        {
            return orglist;
        }
    }
    
  //获取加盟商区域
    /**
     * 添加加盟商信息, 加载一次之后不在出现
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getFranchiseeOnlyListByLogin.action", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<LfAreaOrg> getFranchiseeOnlyListByLogin(HttpServletRequest request) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        String level = request.getParameter("level");
        String parentId = request.getParameter("parentId");
//        LfAreaOrg org = lfAreaOrgService.selectByCode(parentId);
        LfAreaOrg org = lfAreaOrgService.selectByParentId(parentId);
        param.put("level", level);
        if (org != null) {
            param.put("parentId", org.getId());
        }
        String type = "1";
        if("70".equals(level)){
        	type = "2";
        }
        param.put("type",type);
        
        List<LfAreaOrg> orglist = lfAreaOrgService.selectOnlyByType(param);
        
        LfEmployee loginEmp= new LfEmployee();
		try {
			loginEmp = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        PositionCache cache = areaOrgPriSOAClient.getShowNameByPositionId(loginEmp.getPositionId());
        PositionInfo cache = agentSOAServer.getEmployeePositionInfo(loginEmp.getId(),SystemInfo.OperationManagementSystem);
        Integer lastOrgId = cache.getCityAreaOrgId();
        if(cache.getAreaOrgId() != null && cache.getAreaOrgId() != 0)
        {
            lastOrgId = cache.getAreaOrgId();
        }
        
        if(cache.getDoorAreaOrgId() != null && cache.getDoorAreaOrgId() != 0)
        {
            lastOrgId = cache.getDoorAreaOrgId();
        }
        
        if(lastOrgId != null && lastOrgId != 0)
        {
            //获取组织code
            LfAreaOrg codeorg = lfAreaOrgService.selectByPK(lastOrgId);
            String lastCode = codeorg.getCode();
            
            //如果组织小于员工组织那么只加载当前员工所在的组织
            List<LfAreaOrg> orglistNew = new ArrayList<LfAreaOrg>();
            for (int i = 0; i < orglist.size(); i++) {
                if(orglist.get(i).getCode().length() <= lastCode.length())
                {
                    if(lastCode.startsWith(orglist.get(i).getCode()))
                    {
                        orglistNew.add(orglist.get(i));
                    }
                }
                else
                {
                    if(orglist.get(i).getCode().startsWith(lastCode))
                    {
                        orglistNew.add(orglist.get(i));
                    }
                }
            }
            return orglistNew;
        }
        else
        {
            return orglist;
        }
    }
    /**
     * 根据参数获取区域、门店等信息
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getListByLoginIn.action", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public List<LfAreaOrg> getListByLoginIn(HttpServletRequest request) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        String level = request.getParameter("level");
        String parentId = request.getParameter("parentId");
        LfAreaOrg org = lfAreaOrgService.selectByParentId(parentId);
        param.put("level", level);
        if (org != null) {
            param.put("parentId", org.getId());
        }
        
        List<LfAreaOrg> orglist = lfAreaOrgService.selectBy(param);
        
        LfEmployee loginEmp= new LfEmployee();
		try {
			loginEmp = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        PositionCache cache = areaOrgPriSOAClient.getShowNameByPositionId(loginEmp.getPositionId(),SystemInfo.OperationManagementSystem);
        PositionInfo cache = agentSOAServer.getEmployeePositionInfo(loginEmp.getId(),SystemInfo.OperationManagementSystem);
        Integer lastOrgId = cache.getCityAreaOrgId();
        if(cache.getAreaOrgId() != null && cache.getAreaOrgId() != 0)
        {
            lastOrgId = cache.getAreaOrgId();
        }
        
        if(cache.getDoorAreaOrgId() != null && cache.getDoorAreaOrgId() != 0)
        {
            lastOrgId = cache.getDoorAreaOrgId();
        }
        
        if(lastOrgId != null && lastOrgId != 0)
        {
            //获取组织code
            LfAreaOrg codeorg = lfAreaOrgService.selectByPK(lastOrgId);
            String lastCode = codeorg.getCode();
            
            //如果组织小于员工组织那么只加载当前员工所在的组织
            List<LfAreaOrg> orglistNew = new ArrayList<LfAreaOrg>();
            for (int i = 0; i < orglist.size(); i++) {
                if(orglist.get(i).getCode().length() <= lastCode.length())
                {
                    if(lastCode.startsWith(orglist.get(i).getCode()))
                    {
                        orglistNew.add(orglist.get(i));
                    }
                }
                else
                {
                    if(orglist.get(i).getCode().startsWith(lastCode))
                    {
                        orglistNew.add(orglist.get(i));
                    }
                }
            }
            return orglistNew;
        }
        else
        {
            return orglist;
        }
    }
    /**
     * 获取 agentId 能看到的所有city
     *
     * @return
     */

    @RequestMapping(value = "/getCityListByUserId.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<AreaOrg> getCityListByUserId() {
        SsoUser user = null;
        List<AreaOrg> cityList = new ArrayList<>();
        try {
            user = ssoClient.getCurrentUser();
            AreaOrgModel area = agentSOAServer.getEmployeeAreaOrgs(user.getId(), SystemInfo.OperationManagementSystem);
            if (null != area) {
                cityList = area.getCityAreaOrgs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cityList;
    }

    /**
     * 获取 agentId 能看到的所有加盟商
     *
     * @return
     */

    @RequestMapping(value = "/getPartnerByCityId.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<AreaOrg> getPartnerByCityId(int cityId) {
        //加盟商
        List<AreaOrg> partners = new ArrayList<>();
        try {
            List<AreaOrg> cityList = getCityListByUserId();
            for (AreaOrg city : cityList) {
                if (city.getId() == cityId) {
                    partners.addAll(city.getChilds());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return partners;
    }



    /**
     * 获取 agentId 能看到的所有门店
     *
     * @return
     */

    @RequestMapping(value = "/deptListByPartnerId.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<AreaOrg> deptListByPartnerId(@RequestParam("cityId") int cityId, @RequestParam("partnerId") int partnerId) {
        List<AreaOrg> franchiseeList = getPartnerByCityId(cityId);
        //加盟商
        List<AreaOrg> departmentList = null;
        for (AreaOrg dept : franchiseeList) {
            if (dept.getId() == partnerId) {
                departmentList = dept.getChilds();
                break;
            }
        }
        return departmentList;
    }
}
