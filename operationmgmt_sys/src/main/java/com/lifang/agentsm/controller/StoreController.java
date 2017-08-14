package com.lifang.agentsm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfStore;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.model.StoreInfoModel;
import com.lifang.agentsm.service.StoreService;
@Controller
@RequestMapping("/store")
public class StoreController extends BaseController{
	@Autowired
	private StoreService storeService;
	
	@RequestMapping(value = "/getStoreList.action",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object getStoreList(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars){
		getPars(pars);
		return storeService.getStore(pars);
	}
	@RequestMapping(value = "/getStoreListBySearch.action",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<StoreInfoModel> getStoreListBySearch(HttpServletRequest request, HttpServletResponse response,Integer companyId,String storeName){
		logger.info("根据条件搜索门店列表  request ---:companyId:{},storeName:{}",companyId,storeName);
		return storeService.getStoreListByPars(companyId, storeName);
	}
	
	@RequestMapping(value = "/storeListIndex.action",method={RequestMethod.GET,RequestMethod.POST})
	public String storeListIndex(HttpServletRequest request, HttpServletResponse response){
		return "store/storeList";
	}
	
	@RequestMapping(value = "/addStore.action",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object addStore(HttpServletRequest request, HttpServletResponse response,LfStore pars){
		int status = storeService.addStore(pars);
		return status;

	}
	@RequestMapping(value = "/updateStore.action",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Object updateStore(HttpServletRequest request, HttpServletResponse response,LfStore pars){
		int status = 0;
		try {
			status = storeService.updateStoreByStoreId(pars);
		} catch (Exception e) {
			logger.info("更新门店出错：",JSONObject.fromObject(pars).toString());
		}
		
		return status;

	}
	
	@RequestMapping(value = "/deleteStore.action", method = {RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Object deleteStore(HttpServletRequest request,HttpServletResponse response,String ids) {
		logger.info("批量删除公司:ids:{}",ids);
		int status = 0 ;
		String[] idsTmp = ids.split(",");
		try{
			for(int i = 0; i < idsTmp.length; i ++){
				status = storeService.deleteStoreByStoreId(Integer.parseInt(idsTmp[i]));
			}
		}catch(Exception e){
			logger.info("批量删除公司出错:ids:{}",ids);
		}
		return status;
	}
	/**
	 * 功能描述:根据公司Id获得门店
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月7日      新建
	 * </pre>
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getStoreListByCompanyId.action")
    @ResponseBody
	public List<LfStore> getStoreListByCompanyId(Integer companyId,HttpServletRequest request, HttpServletResponse response){
		List<LfStore> stores = new ArrayList<LfStore>();
		if(companyId!=null){
			stores = storeService.getStoreByCompanyId(companyId);
			logger.info("根据公司id查询门店,companyId={},查询出门店：{}",companyId,stores);
		}
		return stores;
	}
	
	
	/**
	 * 功能描述:获取门店的简单信息列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     CK:   2015年5月30日      新建
	 * </pre>
	 *
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value = "simple/list")
    @ResponseBody
	public List<MiniuiEntity> getStoreSimpleList(Integer cityId){
		return storeService.getStoreSimpleList(cityId);
	}
}
