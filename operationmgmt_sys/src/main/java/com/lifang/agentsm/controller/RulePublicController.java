package com.lifang.agentsm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.RulePublic;
import com.lifang.agentsm.service.RulePublicService;

@Controller
@RequestMapping("/ruleMng")
public class RulePublicController extends BaseController {
    @Autowired
    RulePublicService rulePublicService;

 // 显示公客规则界面
    @RequestMapping(value = "/showPublicRule.action")
    public String showPublicRule(HttpServletRequest request, HttpServletResponse response) {
    	RulePublic rp = null;
    	rp = rulePublicService.findRulePublic();
    	if(rp == null){
    		rp = new RulePublic();
    		rp.setAreaCount("0");
    		rp.setPublicToStore("0");
    		rp.setStoreCount("0");
    		rp.setStoreToArea("0");
    		rp.setViewAreaCount("0");
    		rp.setViewStoreCount("0");
    	}
    	request.setAttribute("rulePublic", rp);
        return "rulemng/publicRule";
    }
    // 显示公客脚本界面
    @RequestMapping(value = "/showruleScript.action")
    public String showruleScript(HttpServletRequest request, HttpServletResponse response) {
        return "rulemng/ruleScript";
    }

    //保存规则

    @RequestMapping(value = "/savePublicRule.action")
    @ResponseBody
    public Object savePublicRule(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){

		LfEmployee employee= new LfEmployee();
		try {
			employee = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		pars.put("createby",employee.getId());

        String result = "";

        try{

            result = rulePublicService.savePublicRule(pars);

        }catch (Exception e){
            result = "0";
            e.printStackTrace();
        }
        try {
            response.getWriter().write(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

