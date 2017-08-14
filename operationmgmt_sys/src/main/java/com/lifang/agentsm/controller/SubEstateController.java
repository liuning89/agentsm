package com.lifang.agentsm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.model.SubEstateTipsModel;
import com.lifang.agentsm.service.impl.SubEstateService;
import com.lifang.search.client.bean.AcParamBean;
import com.lifang.search.client.bean.AcWordRltBean;
import com.lifang.search.client.bean.SearchEntity;
import com.lifang.search.client.service.SearchFacadeService;

/**
 * Author:CK
 * 创建时间：2015年5月29日
 */
@Controller
@RequestMapping("subEstate")
public class SubEstateController extends BaseController{
	
	@Autowired
	private SearchFacadeService searchServiceClient;
	
	@Autowired
	private SubEstateService subEstateService;
	
	/**
	 * 功能描述:获取小区tips
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     CK:   2015年5月29日      新建
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("tips")
	@ResponseBody
	public List<MiniuiEntity> getEstateTips(String key,String cityId){
	    System.out.println(cityId);
	       AcParamBean acParamBean = new AcParamBean(key);
    	    if(!"null".equals(cityId)&&null!=cityId&&!"".equals(cityId)){
    	        acParamBean.setCityid(Integer.valueOf(cityId));
    	    }
		AcWordRltBean acWordRltBean = searchServiceClient.queryAcOnlyEstateRlt(acParamBean);
		List<MiniuiEntity> miniuiEntities = new ArrayList<>();
		String displayName = null;
		if(acWordRltBean != null && acWordRltBean.getResult()!=null){
			for(SearchEntity se:acWordRltBean.getResult()){
				displayName = se.getEstatename();
				if(se.getSubestatename() != null && !"".equals(se.getSubestatename())){
					displayName = se.getEstatename()+"("+se.getSubestatename()+")";
				}
				MiniuiEntity miniuiEntity = new MiniuiEntity(displayName,se.getSubestateid(),null);
				miniuiEntities.add(miniuiEntity);
			}
		}
		return miniuiEntities;
	}
	
	
	/**
	 * 功能描述:根据子小区id获取有效的楼栋列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     CK:   2015年5月30日      新建
	 * </pre>
	 *
	 * @param subEstateId
	 * @return
	 */
	@RequestMapping("building/list")
	@ResponseBody
	public List<MiniuiEntity> getBuildingListBySubEstateId(Integer subEstateId){
		return subEstateService.getBuildingListBySubEstateId(subEstateId);
	}
}

