package com.lifang.agentsm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfUserappHomeImg;
import com.lifang.agentsm.service.UserAppService;
import com.lifang.agentsm.utils.ImageUtils;
import com.lifang.imgsoa.facade.ImageService;
import com.lifang.imgsoa.model.ImageKeyObject;

@Controller
@RequestMapping("/userApp")
public class UserAppController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(UserAppController.class);
	
	@Autowired
	private UserAppService userAppService;
	
	@Autowired
	private ImageService imgSOAClient;
	
	/***
	 * 
	* @Title: gotoUserAppTopImgPage 
	* @Description: 跳转到首页图片管理页面
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value = "gotoUserAppTopImgPage.action")
    public Object gotoUserAppTopImgPage(HttpServletRequest request)
    {
	    LfUserappHomeImg appImg = userAppService.selectByTypeLimitOne(1);
	    
	    //获取图像地址
	    if(appImg != null && appImg.getImgKey() != null)
	    {
	        ImageKeyObject obj = ImageUtils.getPublicUrlByKey(imgSOAClient, appImg.getImgKey());
            if (obj != null)
            {
	            appImg.setImgPath(obj.getOriginal());
	        }
	    }
	    request.setAttribute("topbanner", appImg);
        return "userApp/userAppHome";
    }
	
	/***
	 * 
	* @Title: getUserAppTopImg 
	* @Description: 返回首页图片路径和key
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value = "getUserAppTopImg.out")
	@ResponseBody
	public Object getUserAppTopImg()
	{
	    LfUserappHomeImg appImg = userAppService.getUserAppTopImg();
	    
	    //获取图像地址
        if(appImg.getImgKey() != null)
        {
            ImageKeyObject obj = ImageUtils.getPublicUrlByKey(imgSOAClient, appImg.getImgKey());
            if (obj != null)
            {
                appImg.setImgPath(obj.getOriginal());
            }
        }
	    return appImg;
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
    public Object addAndEditUserAppTopImg(HttpSession session, @RequestParam CommonsMultipartFile imgFile, LfUserappHomeImg appImg){
        int result = 0;
        try{
            result = userAppService.addAndUpdateUserAppTopImg(imgFile.getBytes(), getLoginEmployeeInfo(session), appImg.getUrl());
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
    public Object addAndEditUserAppTopUrl(HttpSession session, LfUserappHomeImg appImg){
        int result = 0;
        try{
            result = userAppService.addAndUpdateUserAppTopImg(null, getLoginEmployeeInfo(session), appImg.getUrl());
        } catch (Exception e){
            return "未知错误，请联系管理员";
        }
        return result;
    }
}
