package com.lifang.agentsm.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfAreaOrg;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.FeedBackRequest;
import com.lifang.agentsm.service.LfAgentService;
import com.lifang.agentsm.service.LfAppFeedbackService;
import com.lifang.agentsm.service.LfAreaOrgService;
import com.lifang.agentsm.service.StoreService;

@Controller
@RequestMapping("/lfAppFeedBack")
public class LfAppFeedBackController extends BaseController {

    @Autowired
    private LfAppFeedbackService lfAppFeedbaService;
    
    @Autowired
    private StoreService storeService;
    
    @Autowired
    private LfAgentService lfAgentService;
    
    @Autowired
    private LfAreaOrgService lfAreaOrgService;

    // 获取员工列表
    @RequestMapping(value = "/getFeedBackList.action", method = { RequestMethod.GET, RequestMethod.POST }, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Object getFeedBackList(HttpSession session, FeedBackRequest req) {
        
        if (req.getCityCode() != null && !"".equals(req.getCityCode())) {
            req.setOrgCode(req.getCityCode());
        }
        if (req.getAreaCode() != null && !"".equals(req.getAreaCode())) {
            req.setOrgCode(req.getAreaCode());
        }
        if (req.getStoreCode() != null && !"".equals(req.getStoreCode())) {
            req.setOrgCode(req.getStoreCode());
        }
        
        // 计算出当前员工的组织结构的code
        LfEmployee emp= new LfEmployee();
		try {
			emp = getLoginEmployeeInfo(session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        LfAreaOrg areaOrg = lfAreaOrgService.selectByEmployeeId(emp.getId());
        String empOrgCode = areaOrg.getCode();

        // 如果登陆员工的code长于参数的code那么用登陆员工code代替页面参数code
        if (req.getOrgCode() == null || empOrgCode.length() > req.getOrgCode().length())
        {
            req.setOrgCode(empOrgCode);
        }
        
        return lfAppFeedbaService.selectLfAppFeedbackListByPage(req);
    }

    // 跳转到反馈列表页面
    @RequestMapping(value = "/gotoFeedBackList.action")
    public String gotoEmployeeList(HttpServletRequest request, HttpServletResponse response) {
        return "feedback/feedbackList";
    }
    
    //门店下拉列表
    @RequestMapping(value="/getStoreList", method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Object getStoreList(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,Object> pars)
    {
        return storeService.selectStoreListByPars(pars);
    }
    
    //经纪人下拉列表
    @RequestMapping(value="/getAgentList",method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Object getAgentList(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,Object> pars)
    {
        return lfAgentService.selectAgentListByPars(pars);
    }
    
    
    //工具类出来miniui分页参数
    private void processParam(Map<String, Object> pars) {
        int pageIndex = 0;
        int pageSize = 0;

        try {
            pageIndex = Integer.parseInt(pars.get("pageIndex") + "");
            pageSize = Integer.parseInt(pars.get("pageSize") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        int start = pageIndex * pageSize;
        int end = pageSize;

        pars.put("start", start);
        pars.put("end", end);
    }

    //工具类出来页面提交的json参数
    private LfEmployee changeJsonToObject(String jsonStr)
    {
        JSONArray new_jsonArray=JSONArray.fromObject(jsonStr);  
        Collection java_collection=JSONArray.toCollection(new_jsonArray);  
        if(java_collection!=null && !java_collection.isEmpty())  
        {  
            Iterator it=java_collection.iterator();  
            while(it.hasNext())  
            {  
                JSONObject jsonObj=JSONObject.fromObject(it.next());  
                LfEmployee employee=(LfEmployee) JSONObject.toBean(jsonObj,LfEmployee.class);  
                return employee;
            }  
        } 
        return null;
    }
 
}
