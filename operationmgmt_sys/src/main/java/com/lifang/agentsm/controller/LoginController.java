package com.lifang.agentsm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.MenuInfo;
import com.lifang.agentsm.service.LfEmployeeService;
import com.lifang.agentsm.utils.SessionUtils;
import com.lifang.json.FasterJsonTool;
import com.lifang.sso.SsoClientUtil;
import com.lifang.sso.SystemInfo;
import com.lifang.sso.entity.SsoPositionMenu;
import com.lifang.sso.entity.SsoSystem;
import com.lifang.sso.entity.SsoUser;
import com.lifang.sso.entity.SsoUserPositionOrgRelate;

@Controller
public class LoginController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LfEmployeeService lfEmployeeService;

//	@Autowired
//	private SsoClientUtil ssoClientUtil;
	@Autowired
	SsoClientUtil ssoClientUtil;
	@Value("${environment}")
	String environment;
	/**
	 * 登录
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "login")
//	@ResponseBody
	public String login(String username,String password,HttpSession session,Model model,HttpServletRequest request) {
//		Map<String,Object> map = new HashMap<>();
//		password = Md5Utils.md5(password);
//		map.put("loginName", username);
//		map.put("password", password);
//		LfEmployee lfEmployee = lfEmployeeService.loginCheck(map);
//		
//		if(lfEmployee != null && !"2".equals(lfEmployee.getDepartment())){
//			return ASMResponse.HouseDepartError.value;
//		}
//		
//		if(lfEmployee != null){
//			lfEmployee.setPassWord("");
//			session.setAttribute(Constants.LOGIN_SESSION,lfEmployee);
//			return ASMResponse.Success.value;
//		}else{
//			return ASMResponse.LoginFailure.value;
//		}
	    try {
	        List<SsoSystem> systemList = ssoClientUtil.getUserSystem();
	        System.out.println(systemList);
	        request.setAttribute("systemList", systemList);
	        request.setAttribute("environment", environment);
	        
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	    return "index";
	}
	
	
	/**
	 * 修改密码
	 * @param oldPw
	 * @param newPw
	 * @param session
	 * @return
	 */
//	@RequestMapping("/updatePw.action")
//	@ResponseBody
//	private String updatePw(String oldPw,String newPw,HttpServletResponse response,HttpSession session) {
//		String result = "";
//		Map<String,Object> map = new HashMap<>();
//		oldPw = Md5Utils.md5(oldPw);
//		
//		//验证旧密码
//		
//		LfEmployee employee= new LfEmployee();
//		try {
//			employee = getLoginEmployeeInfo(session);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//    	
//		
//		map.put("loginName", employee.getMobile());
//		map.put("password", oldPw);
//		
//		LfEmployee lfEmployee = lfEmployeeService.loginCheck(map);
//		if(lfEmployee != null){//验证通过
//			map.put("id", employee.getId());
//			map.put("newpassword", newPw);
//			lfEmployeeService.updatePw(map);		
//			
//			result = "1";
//		}else{
//			//验证不通过
//			result = "0";
//		}
//		try {
//			response.getWriter().write(result);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		session.removeAttribute(Constants.LOGIN_SESSION);
//		return null;
//	}
//	
	
	/**
	 * 功能描述:获取登录用户的信息
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     CK:   2015年5月28日      新建
	 * </pre>
	 *
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "loginInfo", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loginInfo(HttpSession session){
		SsoUser ssoUser = null;
		try
		{
			ssoUser = ssoClientUtil.getCurrentUser();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if(ssoUser != null){
		    List<SsoUserPositionOrgRelate> list;
            try {
                list = ssoClientUtil.getCurrentOrg(SystemInfo.OperationManagement);
                LfEmployee employee = new LfEmployee();
                employee.setId(ssoUser.getId());
                employee.setCityId(list.get(0).getCityId());
                employee.setMobile(ssoUser.getMobile());
                employee.setStatus(ssoUser.getStatus().intValue());
                employee.setWorkNumber(ssoUser.getWorkNumber());
                employee.setName(ssoUser.getName());
                return "agent="+FasterJsonTool.writeValueAsString(employee)+";";
            }
            catch (Exception e) {
               throw new RuntimeException(e);
            }
		}else{
			return "agent=null;";
		}
	}
	
	

	@RequestMapping("/logout.action")
	public String loginout(HttpServletRequest request, HttpSession session) {
//		session.removeAttribute(Constants.LOGIN_SESSION);
		try {
			ssoClientUtil.LogOut(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:"+ssoClientUtil.getDefLogoutUrl();
	}

	@RequestMapping("/mianForward.action")
	private String mianForward(HttpServletRequest request) {
		return "main";
	}

	@RequestMapping("/index.action")
	private String login(HttpServletRequest request, ModelMap model) {
		LfEmployee loginEmp = SessionUtils.getSession(request);
		if (null == loginEmp) {
			return "login";
		}
		return "main";
	}

	/**
	 * 主页
	 */
//	@RequestMapping("menuTree")
//	public String getMenuTree(HttpSession session){
//		LfEmployee employee = getLoginEmployeeInfo(session);
//		String menuTree = null;
//		int type= 2;//employee.getGrade();
//		switch (type) {
//		case 1:
//			menuTree = "outlooktree.txt";
//			break;
//		case 2:
//			menuTree = "outlooktree_admin.txt";
//			break;
//		}
//		return "redirect:"+menuTree;
//	}
	
	/**
     * 主页
     */
    @RequestMapping(value="menuTree",produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public String getMenuTree(HttpSession session){
//        try {
//			LfEmployee employee = getLoginEmployeeInfo(session);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        //List<Menu> menu = areaOrgPriSOAServer.getPositionMenuList(employee.getPositionId());
		List<SsoPositionMenu> menu = null;
			try {
				menu = ssoClientUtil.getSystemMenu(SystemInfo.OperationManagement);//【当前人职位菜单】 
			} catch (Exception e) {
				// TODO: handle exception
			}

        MenuInfo tempMi = new MenuInfo();
		SsoPositionMenu tempM = null;

        List<MenuInfo> minfos = new ArrayList<MenuInfo>();

        tempMi.setId("root");
        tempMi.setText("经纪人运营系统");
        minfos.add(tempMi);

        for (int i = 0; i < menu.size(); i++) {
            tempM = menu.get(i);
            tempMi = new MenuInfo();
            tempMi.setId(tempM.getId() == null ? "0" :tempM.getId().intValue() + "");
            tempMi.setPid(tempM.getParentId() == null || tempM.getParentId().intValue() == 0  ? "root" :tempM.getParentId().intValue() + "");
            tempMi.setText(tempM.getName());
            tempMi.setUrl(tempM.getUrl());
            minfos.add(tempMi);

            if(tempM.getChildMenu() !=null && tempM.getChildMenu().size() > 0)
            {
                addMenu(tempM.getChildMenu(), minfos);
            }
        }

       JSONArray object =  JSONArray.fromObject(minfos);
       //System.out.println(object.toString());

       return object.toString();
    }


    /**
     *
    * @Title: addMenu
    * @Description: 把menu转化为菜单的递归调用
    * @param @param menu
    * @param @param minfos    设定文件
    * @return void    返回类型
    * @throws
     */
    public void addMenu(List<SsoPositionMenu> menu, List<MenuInfo> minfos)
    {
        MenuInfo tempMi = null;
		SsoPositionMenu tempM = null;
        for (int i = 0; i < menu.size(); i++) {
            tempM = menu.get(i);
            tempMi = new MenuInfo();
            tempMi.setId(tempM.getId() == null ? "0" :tempM.getId().intValue() + "");
            tempMi.setPid(tempM.getParentId() == null || tempM.getParentId().intValue() == 0  ? "root" :tempM.getParentId().intValue() + "");
            tempMi.setText(tempM.getName());
            tempMi.setUrl(tempM.getUrl());
            minfos.add(tempMi);
            if(tempM.getChildMenu() !=null && tempM.getChildMenu().size() > 0)
            {
                addMenu(tempM.getChildMenu(), minfos);
            }
        }
    }
}
