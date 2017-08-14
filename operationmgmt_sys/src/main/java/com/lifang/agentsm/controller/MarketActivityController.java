package com.lifang.agentsm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfAgentActivity;
import com.lifang.agentsm.service.MarketActivityService;

@Controller
@RequestMapping("/market")
public class MarketActivityController extends BaseController {

    @Autowired
    private MarketActivityService markActivityService;

    /**
     * 跳转到活动页面.
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/showMarketActivity.action", method = { RequestMethod.GET, RequestMethod.POST })
    public Object getEmployeeList(HttpServletRequest request, HttpServletResponse response) {
        
    	List<LfAgentActivity> laaList = markActivityService.selectByList(); 
    	request.setAttribute("laaList", laaList);
        return "market/marketActivity";
    }
    
    
    
    /***
	 * 
	* @Title: addUserAppTopImg 
	* @Description: 添加首页更改图片
	* @param @param session
	* @param @param imgFile
	* @param @param appImg
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
    @RequestMapping(value = "/addAndEditUserAppTopImg.action",produces={"application/json;charset=UTF-8"}) 
    @ResponseBody
    public Object addAndEditUserAppTopImg(HttpSession session, @RequestParam CommonsMultipartFile imgFile, LfAgentActivity lfa){
        int result = 0;
        try{
        	System.out.println(lfa.getId());
            result = markActivityService.addAndUpdateUserAppTopImg(imgFile.getBytes(),lfa.getId());
        } catch (Exception e){
            return "未知错误，请联系管理员";
        }
        return result;
    }
    
    /***
	 * 
	* @Title: addUserAppTopImg 
	* @Description: 添加首页更改图片
	* @param @param session
	* @param @param imgFile
	* @param @param appImg
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
    @RequestMapping(value = "/updateOut.action",produces={"application/json;charset=UTF-8"}) 
    @ResponseBody
    public Object updateOut(HttpSession session, @RequestParam Map<String, Object> pars){
        int result = 0;
        try{
//        	System.out.println(lfa.getId());
            result = markActivityService.updateOut(pars);
//        	System.out.println(pars);
        } catch (Exception e){
            return "未知错误，请联系管理员";
        }
        return result;
    }
    
    /***
     * 
    * @Title: addUserAppTopImg 
    * @Description: 添加首页更改路径
    * @param @param session
    * @param @param imgFile
    * @param @param appImg
    * @param @return    设定文件 
    * @return Object    返回类型 
    * @throws
     */
    @RequestMapping(value = "/addAndEditUserAppTopUrl.action",produces={"application/json;charset=UTF-8"}) 
    @ResponseBody
    public Object addAndEditUserAppTopUrl(HttpSession session, @RequestParam Map<String, Object> pars){
        int result = 0;
        try{
        	result = markActivityService.addAndEditUserAppTopUrl(pars);
        } catch (Exception e){
            return "未知错误，请联系管理员";
        }
        return result;
    }
}
