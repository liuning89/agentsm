package com.lifang.agentsm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfCompany;
import com.lifang.agentsm.model.CompanyInfoModel;
import com.lifang.agentsm.service.CompanyService;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {
	@Autowired
	private CompanyService companyService;

	/**
	 * 功能描述:根据城市Id获得公司集合
	 * 
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月7日      新建
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getCompanyListByCityId.action")
	@ResponseBody
	public List<LfCompany> getCompanyListByCityId(Integer cityId,HttpServletRequest request, HttpServletResponse response) {
		List<LfCompany> coms = new ArrayList<LfCompany>();
		if (cityId != null) {
			coms = companyService.selectByPars(cityId, null);
			logger.info("根据城市id查询公司,cityId={},查询出公司：{}", cityId, coms);
		}
		return coms;
	}

	@RequestMapping(value = "/getCompany.action")
	@ResponseBody
	public List<CompanyInfoModel> getCompany(HttpServletRequest request,
			HttpServletResponse response) {
		return companyService.getCompany();
	}

	/**
	 * @author fangyouhui 公司列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getCompanyList.action", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Object getCompanyList(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> pars) {
		getPars(pars);
		return companyService.getCompany(pars);

	}
	
	@RequestMapping(value = "/companyListIndex.action", method = {RequestMethod.GET, RequestMethod.POST })
	public String companyListIndex(HttpServletRequest request,HttpServletResponse response) {
		return "company/companyList";
	}

	@RequestMapping(value = "/addCompany.action", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Object Object(HttpServletRequest request,HttpServletResponse response, LfCompany pars) {
		int status = companyService.addCompany(pars);
		return status;
	}
	@RequestMapping(value = "/updateCompany.action", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Object updateCompany(HttpServletRequest request,HttpServletResponse response, LfCompany pars) {
		int status = 0;
		try {
			status = companyService.updateCompanyById(pars);
		} catch (Exception e) {
			logger.info("更新公司信息出错：",JSONObject.fromObject(pars).toString());
		}
		return status;
	}
	@RequestMapping(value = "/deleteCompany.action", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public Object deleteCompany(HttpServletRequest request,HttpServletResponse response, String ids) {
		logger.info("批量删除公司:ids:{}",ids);
		int status = 0 ;
		String[] idsTmp = ids.split(",");
		try{
			for(int i = 0;i < idsTmp.length;i++){
				status = companyService.deleteCompanyById(Integer.parseInt(idsTmp[i]));
			}
		}catch(Exception e){
			logger.info("批量删除公司出错:ids:{}",ids);
		}
		return status;
	}
}
