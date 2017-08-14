package com.lifang.agentsm.controller;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.CommissionRule;
import com.lifang.agentsm.model.req.CommissionRuleReq;
import com.lifang.agentsm.service.CommissionRuleService;
import com.lifang.model.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guanshuli on 2015/10/20.
 */
@Controller
@RequestMapping("/commissionRule")
public class CommissionRuleController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(CommissionRuleController.class);
    @Autowired
    CommissionRuleService commissionRuleService;


    /**
     * 显示角色列表
     *
     * @param req
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Response list(CommissionRuleReq req) {
        return commissionRuleService.list(req);
    }


    /**
     * @param json
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Response save(HttpServletRequest request, String json) {
        Response response = new Response();
        try {
            CommissionRule[] commissionRules = null;
            if (StringUtils.isNotBlank(json)) {
                JSONArray array = JSONArray.fromObject(json);
                commissionRules = (CommissionRule[]) JSONArray.toArray(array, CommissionRule.class);
                List<CommissionRule> removedCommsRules = new ArrayList<CommissionRule>();
                List<CommissionRule> addedCommsRules = new ArrayList<CommissionRule>();
                LfEmployee createby = null;
                for (CommissionRule cr : commissionRules) {
                    if ("modified".equals(cr.get_state())) {
                        commissionRuleService.update(cr);
                    } else if ("removed".equals(cr.get_state())) {
                        removedCommsRules.add(cr);
                    } else {
                        createby = getLoginEmployeeInfo(request.getSession());
                        cr.setCreateby(createby != null ? createby.getId() : null);
                        addedCommsRules.add(cr);
                    }

                }
                if (addedCommsRules.size() > 0) {
                    commissionRuleService.insert(addedCommsRules);
                }
                if (removedCommsRules.size() > 0) {
                    commissionRuleService.delete(removedCommsRules);
                }
            }
            response.setStatus(1);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("操作失败");
        }
        return response;
    }


    @RequestMapping("/savePercentage")
    @ResponseBody
    public Object savePercentage(HttpSession session, String data) {
        CommissionRule commissionRule = null;
        try {
            commissionRule = (CommissionRule) JSONObject.toBean(JSONObject.fromObject(data), CommissionRule.class);
            LfEmployee operator = getLoginEmployeeInfo(session);
            Integer id = (commissionRule == null) ? null : commissionRule.getId();
            if (0 == id) {
                commissionRule.setCreateby(operator != null ? operator.getId() : null);
                commissionRuleService.insertPercentage(commissionRule);
            } else {
                commissionRule.setUpdateby(operator != null ? operator.getId() : null);
                commissionRuleService.updatePercentage(commissionRule);
            }
        } catch (Exception e) {
            logger.info("======>url={},error={}", "/savePercentage.action", e.getMessage());
            e.printStackTrace();
        }
        return commissionRule;
    }


    @RequestMapping(value = "/getPercentage", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object getPercentage(HttpServletRequest request, HttpServletResponse response) {
        return commissionRuleService.getPercentage();
    }

}
