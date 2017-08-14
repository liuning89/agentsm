package com.lifang.agentsm.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.DicAreaNew;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.service.DicAreaNewService;

@Controller
@RequestMapping("/dicAreaNew")
public class DicAreaNewController extends BaseController {
	
	
	
protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DicAreaNewService dicAreaNewService;
	
	
	/**
	 * 获取所有的城市
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCity.action")
	@ResponseBody
	public List<DicAreaNew> getCity(HttpServletRequest request) {
		return dicAreaNewService.selectCity();
	}
	
	/**
	 * 获取城市下面的区域
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDistrictidByCity.action",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<DicAreaNew> getDistrictidByCity(HttpServletRequest request) {
		
		HashMap<String,	Object> map=new HashMap<String,Object>();
		String parentId=request.getParameter("parentId");
		map.put("parentId", Integer.parseInt(parentId));
		return dicAreaNewService.selectDistrictidByCity(map);
	}
	
	
	/**
	 * 功能描述:获取可用的城市列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     CK:   2015年5月28日      新建
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("getEnableCitySimpleList")
	@ResponseBody
	public List<MiniuiEntity> getEnableCitySimpleList(HttpSession session){
	    LfEmployee lf= new LfEmployee();
		try {
			lf = getLoginEmployeeInfo(session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if(lf.getCityId()==1){
	        lf.setCityId(null);
	    }
	    
		return dicAreaNewService.getEnableCitySimpleList(lf.getCityId());
	}
	
	/**
	 * 
	 * 根据上级区域，获取下级区域
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月27日      新建
	 * </pre>
	 *
	 * @param parentId
	 * @return
	 */
	@RequestMapping("getDicAreaNewByParentId")
	@ResponseBody
	public List<MiniuiEntity> getDicAreaNewByParentId(@RequestParam int parentId) {
		return dicAreaNewService.getDicAreaNewByParentId(parentId);
	}
	/**
	 * 
	 * 功能描述:机构专用
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     ln:   2015年11月23日      新建
	 * </pre>
	 *
	 * @param session
	 * @return
	 */
	@RequestMapping("getEnableCityUsedArea")
    @ResponseBody
    public List<MiniuiEntity> getEnableCityUsedArea(HttpSession session){
	    LfEmployee lf= new LfEmployee();
		try {
			lf = getLoginEmployeeInfo(session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(lf.getCityId()==1){
            lf.setCityId(null);
        }
        
        return dicAreaNewService.getEnableCityUsedArea(lf.getCityId());
    }
}
