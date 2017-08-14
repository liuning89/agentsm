package com.lifang.bzsm.console.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.bzsm.console.entity.LfCustomer;
import com.lifang.bzsm.console.model.LfCustomerRequest;
import com.lifang.bzsm.console.model.LfCustomerRequirementInfo;
import com.lifang.bzsm.console.service.BZLfAreaOrgService;
import com.lifang.bzsm.console.service.LfCustomerService;
import com.lifang.imgsoa.facade.ImageService;
import com.lifang.imgsoa.model.ImageKeyObject;
import com.lifang.model.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/customer")
public class LfCustomerController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(LfCustomerController.class);

    @Autowired
    private LfCustomerService lfCustomerService;

    @Autowired
    private BZLfAreaOrgService lfAreaOrgService;

    @Autowired
    private AgentSOAServer agentSOAServer;

    @Autowired
    private ImageService imageService;
 
    @RequestMapping(value = "/gotoCustomerList.action")
    public String gotoCustomerList(HttpServletRequest request, HttpServletResponse response) {
        return "customer/customerList";
    }

    /***
     * 调整到客户详情页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/gotoCustomerDetail.action")
    public String gotoCustomerDetail(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("customerId");
        int customerId = 0;
        try {
            customerId = Integer.parseInt(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        request.setAttribute("customerId", customerId);
        return "customer/customerDetail";
    }

    /**
     * 跳转到客户基本信息页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/gotoCustomerBasicInfo.action")
    public String gotoCustomerBasicInfo(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("customerId");
        long customerId = 0;
        try {
            customerId = Integer.parseInt(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        LfCustomer customer = lfCustomerService.selectByPK(customerId);
        LfCustomerRequirementInfo cusReq = lfCustomerService.selectCustomerReq(customerId);

        request.setAttribute("customer", customer);
        request.setAttribute("cusReq", cusReq);

        return "customer/customerBasicInfo";
    }

    /**
     * 跳转到A客列表页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/gotoCustomerAMark.action")
    public String gotoCustomerAMark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("customerId");
        int customerId = 0;
        try {
            customerId = Integer.parseInt(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        request.setAttribute("customerId", customerId);
        return "customer/customerAMarkPage";
    }



    /**
     * 跳转到客户带看页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/gotoCustomerHouseSee.action")
    public String gotoCustomerHouseSee(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("customerId");
        int customerId = 0;
        try {
            customerId = Integer.parseInt(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        request.setAttribute("customerId", customerId);
        return "customer/customerHouseSee";
    }

    /**
     * 跳转到客户跟进页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/gotoCustomerFollow.action")
    public String gotoCustomerFollow(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("customerId");
        int customerId = 0;
        try {
            customerId = Integer.parseInt(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        request.setAttribute("customerId", customerId);
        return "customer/customerFollow";
    }

    /**
     * 获取客户A列表数据
     * @param session
     * @param req
     * @return
     */
    @RequestMapping(value = "/getCustomerAMark.action")
    @ResponseBody
    public PageResponse getCustomerAMark(HttpSession session, LfCustomerRequest req) {
        PageResponse response = new PageResponse();
        response = lfCustomerService.getCustomerAMarkList(req);
        return response;
    }


    @RequestMapping(value = "/getCustomerHouseSee.action")
    @ResponseBody
    public PageResponse getCustomerHouseSee(HttpSession session, LfCustomerRequest req) {
        PageResponse response = new PageResponse();
        response = lfCustomerService.getCustomerHouseSeeList(req);
        return response;
    }


    /**
     * 获取客户跟进数据功能
     * @param session
     * @param req
     * @return
     */
    @RequestMapping(value = "/getCustomerFollow.action")
    @ResponseBody
    public PageResponse getCustomerFollow(HttpSession session, LfCustomerRequest req) {
        PageResponse response = new PageResponse();
        response = lfCustomerService.getCustomerFollowList(req);
        return response;
    }

    /**
     * 跳转到显示图片页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/gotoShowImage.action")
    public String gotoShowImage(HttpServletRequest request, HttpServletResponse response) {
        String key = request.getParameter("key");

        String headKey = key;// key
        if (!StringUtils.isEmpty(headKey)) {
            ImageKeyObject imageObj = imageService.getImageKeyObject(headKey);
            if (imageObj != null)
            {
                request.setAttribute("path", imageObj.getOriginal());
            }
        }

        return "customer/customerHouseSeeImage";
    }

    /**
     * 获取客户列表数据
     * @param session
     * @param req
     * @return
     */
    @RequestMapping(value = "/getCustomerList.action")
    @ResponseBody
    public PageResponse getCustomerList(HttpSession session, LfCustomerRequest req) {
        PageResponse response = new PageResponse();

        // 计算出当前员工的组织结构的code
        LfEmployee emp = getLoginEmployeeInfo(session);

        //处理时间
        processDate(req);

        response = lfCustomerService.getCustomerList(req, emp);
        return response;
    }

    private void processDate(LfCustomerRequest req)
    {
        if(req.getDateEnd() != null)
        {
            Calendar g = Calendar.getInstance();
            g.setTime(req.getDateEnd());
            g.add(Calendar.DATE, 1);
            Date d2 = g.getTime();
            req.setDateEnd(d2);
        }
    }
    
    public static void main(String[] args)
    {
//        Agent a = new Agent();
//        a.getName();
    }

}
