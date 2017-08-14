package com.lifang.agentsm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.model.AppnameMiniuiEntity;
import com.lifang.agentsm.model.PriFunction;
import com.lifang.agentsm.model.PriUrl;
import com.lifang.agentsm.model.req.PriFunctionReq;
import com.lifang.agentsm.service.PriFunctionService;
import com.lifang.model.Response;

/**
 * 
 * 功能
 *
 * @author   Yang'ushan
 * @Date	 2015年7月22日		下午4:23:47
 *
 * @see
 */
@Controller
@RequestMapping("/priFunction")
public class PriFunctionController {

	@Autowired
	private PriFunctionService priFunctionService;
	
	/**
	 * 
	 * 列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月22日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Response list(PriFunctionReq req) {
		return priFunctionService.list(req);
	}
	
	/**
	 * 
	 * 获取所有的应用列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/app/simpleList")
	@ResponseBody
	public List<AppnameMiniuiEntity> appSimpleList() {
		List<AppnameMiniuiEntity> list = priFunctionService.appSimpleList();
		return list;
	}
	
	/**
	 * 
	 * 添加
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param priFunction
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Response add(PriFunction priFunction) {
		Response response = new Response();
		response.setStatus(-1);
		if (StringUtils.isBlank(priFunction.getName())) {
			response.setMessage("名称不能为空");
		} else if (StringUtils.isBlank(priFunction.getAppName())) {
			response.setMessage("应用名称不能为空");
		} else if (priFunction.getType() == null) {
			response.setMessage("类型不能为空");
		} else {
			try {
				priFunctionService.add(priFunction);
				response.setStatus(1);
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("操作失败");
			}
		}
		return response;
	}
	
	/**
	 * 
	 * 详情
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/detail")
	public String priFunction(@RequestParam int id, Model model) {
		PriFunction priFunction = priFunctionService.priFunction(id);
		model.addAttribute("priFunction", priFunction);
		return "../../pages/priFunction/priFunction";
	}
	
	/**
	 * 
	 * 修改
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param priFunction
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Response update(PriFunction priFunction) {
		Response response = new Response();
		response.setStatus(-1);
		if (StringUtils.isBlank(priFunction.getName())) {
			response.setMessage("名称不能为空");
		} else if (StringUtils.isBlank(priFunction.getAppName())) {
			response.setMessage("应用名称不能为空");
		} else if (priFunction.getType() == null) {
			response.setMessage("类型不能为空");
		} else if (priFunction.getId() == null) {
			response.setMessage("编号不能为空");
		} else {
			try {
				int count = priFunctionService.update(priFunction);
				if (count > 0) {
					response.setStatus(1);
				} else {
					response.setMessage("数据已被删除");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("操作失败");
			}
		}
		return response;
	}
	
	/**
	 * 
	 * 删除
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Response delete(@RequestParam int id) {
		Response response = new Response();
		response.setStatus(-1);
		try {
			int count = priFunctionService.delete(id);
			if (count > 0) {
				response.setStatus(1);
			} else {
				response.setMessage("数据已被删除");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("操作失败");
		}
		return response;
	}
	
	/**
	 * 
	 * 获取所有URL列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param functionId
	 * @param name
	 * @return
	 */
	@RequestMapping("/url/list")
	@ResponseBody
	public Response list(@RequestParam int functionId, String name) {
		Response response = new Response();
		response.setData(priFunctionService.urlList(functionId, name));
		return response;
	}
	
	/**
	 * 
	 * 保存URL信息
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param json
	 * @param functionId
	 * @param deleteUrlIds
	 * @return
	 */
	@RequestMapping("/url/save")
	@ResponseBody
	public Response saveUrl(String json, @RequestParam int functionId, 
			String deleteUrlIds) {
		Response response = new Response();
		response.setStatus(-1);
		try {
			PriUrl[] urls = null;
			if (StringUtils.isNotBlank(json)) {
				JSONArray array = JSONArray.fromObject(json);
				urls = (PriUrl[]) JSONArray.toArray(array, PriUrl.class);
			}
			List<Integer> ds = null;
			if (StringUtils.isNotBlank(deleteUrlIds)) {
				ds = new ArrayList<Integer>();
				for (String d : deleteUrlIds.split(",")) {
					ds.add(Integer.parseInt(d));
				}
			}
			priFunctionService.saveUrls(urls != null ? Arrays.asList(urls) : null, functionId, ds);
			response.setStatus(1);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("操作失败");
		}
		return response;
	}
	
	/**
	 * 
	 * 获取非菜单的功能列表，在添加菜单页上使用
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping("/getUrlFunctionList")
	@ResponseBody
	public Response getUrlFunctionList(PriFunctionReq req) {
		return priFunctionService.getUrlFunctionList(req);
	}
	
}
