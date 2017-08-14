package com.lifang.agentsm.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.service.LfEmployeeService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;

@Controller
@RequestMapping("/lfEmployee")
public class LfEmployeeController extends BaseController {

    @Autowired
    private LfEmployeeService employeeService;
    
    @Autowired
    private AgentSOAServer agentSOAServer;

    // 获取员工列表
    @RequestMapping(value = "/getEmployeeList.action", method = { RequestMethod.GET, RequestMethod.POST }, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Object getEmployeeList(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pars) {
        processParam(pars);
        return employeeService.selectEmployeeListByPage(pars);
    }

    // 获取员工通过ID
    @RequestMapping(value = "/getEmployeeById.action", method = { RequestMethod.GET, RequestMethod.POST }, produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Object getEmployeeById(HttpServletRequest request, HttpServletResponse response, Integer id) {
        return employeeService.selectEmployeeById(id);
    }

    // 跳转到员工列表页面
    @RequestMapping(value = "/gotoEmployeeList.action")
    public String gotoEmployeeList(HttpServletRequest request, HttpServletResponse response) {
        return "employee/employeeList";
    }

    // 弹出添加员工的添加和修改框
    @RequestMapping(value = "/gotoEmployeeWindow.action")
    public String gotoEmployeeWindow(HttpServletRequest request, HttpServletResponse response) {
        return "employee/employeewindow";
    }
    
 // 弹出添加员工的添加和修改框
    @RequestMapping(value = "/gotoPasswordChange.action")
    public String gotoPasswordChange(HttpServletRequest request, HttpServletResponse response) {
        return "employee/employeePasswordChange";
    }

    // 保存员工
    @RequestMapping(value = "/saveOrEditLfEmployee.action", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object saveOrEditLfEmployee(HttpServletRequest request, HttpServletResponse response, String data) {
        LfEmployee employee = changeJsonToObject(data);
        if(employee.getId() != null && employee.getId() > 0)
        {
            return employeeService.updateLfEmployeeById(employee); 
        }
        return employeeService.addLfEmployee(employee);
    }

    // 更新员工
    @RequestMapping(value = "/updateLfEmployee.action", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object updateLfEmployee(HttpServletRequest request, HttpServletResponse response, LfEmployee lfEmployee) {
        return employeeService.updateLfEmployeeById(lfEmployee);
    }

    // 删除员工
    @RequestMapping(value = "/deleteLfEmployee.action", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object deleteLfEmployee(HttpServletRequest request, HttpServletResponse response, Integer id) {
        return employeeService.removeLfEmployeeById(id);
    }

    // 冻结员工
    @RequestMapping(value = "/updateLfEmployeeGrade.action", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object updateLfEmployeeGrade(HttpServletRequest request, HttpServletResponse response, LfEmployee lfEmployee) {
        lfEmployee.setStatus(2);
        return employeeService.updateLfEmployeeById(lfEmployee);
    }

    // 员工已经存在验证
    @RequestMapping(value = "/checkEmployeeExists.action", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Object checkEmployeeExists(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> pars) {
        return employeeService.checkEmployeeExists(pars);
    }
    
    //修改密码
    @RequestMapping(value = "/changeEmployeePassword.action", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object changeEmployeePassword(HttpServletRequest request,HttpServletResponse response, String data)
    {
        LfEmployee pars = changeJsonToObject(data);
        LfEmployee employee = new LfEmployee();
        employee.setId(pars.getId());
        employee.setPassWord(pars.getPassWord());
        return employeeService.updateLfEmployeeById(employee);
    }
    

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
        int end =   pageSize;

        pars.put("start", start);
        pars.put("end", end);
    }

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

    /**
     * 根据区域组织架构信息获取用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getByAreaOrg.action",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public List<LfEmployee> getByAreaOrg(HttpServletRequest request) {
        HashMap<String,	Object> param = new HashMap<String,Object>();
        String cityId = request.getParameter("cityId");
        String level = request.getParameter("level");
        String code = request.getParameter("code");
        param.put("level", level);
        param.put("cityId", cityId);
        param.put("code", code);
        return employeeService.getByAreaOrg(param);
    }
    
    @RequestMapping(value="/selectEmployeeByOrgId")
    @ResponseBody
    public List<MiniuiEntity> selectEmployeeByOrgId(String code)
    {
        List<Agent> agents = agentSOAServer.getAgentList(null, null, null, code, null);
        
        List<MiniuiEntity> list = new ArrayList<MiniuiEntity>();
        for (Agent agent : agents)
        {
            MiniuiEntity temp = new MiniuiEntity();
            temp.setId(agent.getId());
            temp.setText(agent.getName());
            list.add(temp);
        }
        
        return list;
    }
}
