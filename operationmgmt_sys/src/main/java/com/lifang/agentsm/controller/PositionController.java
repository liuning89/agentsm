package com.lifang.agentsm.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.model.AppnameMiniuiEntity;
import com.lifang.agentsm.model.Menu;
import com.lifang.agentsm.model.PriFunction;
import com.lifang.agentsm.model.req.PositionReq;
import com.lifang.agentsm.service.PositionService;
import com.lifang.model.Response;

/**
 * 
 * 职位
 *
 * @author   Yang'ushan
 * @Date	 2015年7月28日		下午4:07:21
 *
 * @see
 */
@Controller
@RequestMapping(value=("/position"), produces = { "application/json;charset=UTF-8" })
@Log4j
public class PositionController {

	@Autowired
	private PositionService positionService;
	
	/**
	 * 
	 * 列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Response list(PositionReq req) {
		return positionService.list(req);
	}
	
	/**
	 * 
	 * 根据部门获取应用列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param department
	 * @return
	 */
	@RequestMapping("/getAppnamesByDepartment")
	@ResponseBody
	public List<AppnameMiniuiEntity> getAppnamesByDepartment(@RequestParam int department) {
		return positionService.getAppnameByDepartment(department);
	}
	
	/**
	 * 
	 * 获取职位的功能列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param positionId
	 * @return
	 */
	@RequestMapping("/getPositionPriFunctions")
	@ResponseBody
	public List<PriFunction> getPositionPriFunctions(@RequestParam int positionId) {
		return positionService.getPositionPriFunctions(positionId);
	}
	
	/**
	 * 
	 * 获取所有功能列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月29日      新建
	 * </pre>
	 *
	 * @param appName
	 * @param name
	 * @return
	 */
	@RequestMapping("/getPriFunctions")
	@ResponseBody
	public List<PriFunction> getPriFunctions(@RequestParam String appName, String name) {
		List<PriFunction> list = positionService.getPriFunctions(appName, name);
		return list;
	}
	
	/**
	 * 
	 * 保存职位的权限
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月29日      新建
	 * </pre>
	 *
	 * @param positionId
	 * @param functionIds
	 * @return
	 */
	@RequestMapping("/savePositionPriFunction")
	@ResponseBody
	public Response savePositionPriFunction(@RequestParam int positionId, String functionIds) {
		Response response = new Response();
		response.setStatus(-1);
		try {
			List<Integer> fids = null;
			if (StringUtils.isNotBlank(functionIds)) {
				String[] fs = functionIds.split(",");
				fids = new ArrayList<Integer>();
				for (String f : fs) {
					fids.add(Integer.parseInt(f));
				}
			}
			positionService.savePositionPriFunction(positionId, fids);
			response.setStatus(1);
		} catch (Exception e) {
			log.error("保存职位权限", e);
			response.setMessage("操作失败");
		}
		return response;
	}
	
	/**
	 * 
	 * 获取树状图
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月29日      新建
	 * </pre>
	 *
	 * @param positionId
	 * @param appName
	 * @return
	 */
	@RequestMapping("/menuTree")
	@ResponseBody
	public String menuTree(@RequestParam int positionId, @RequestParam String appName) {
		List<Menu> menus = positionService.getLeafMenuList(appName); // 获取所有
		StringBuilder buffer = new StringBuilder("[");
		if (!menus.isEmpty()) {
			List<Menu> ms = positionService.getPositionMenuList(positionId); // 获取该职位下的数据
			for (int i = 0; i < menus.size(); i++) {
				Menu menu = menus.get(i);
				buffer.append("{id:");
				buffer.append(menu.getId());
				buffer.append(",text:'");
				buffer.append(menu.getName());
				buffer.append("',pid:");
				buffer.append(menu.getParentId());
				buffer.append(",checked:");
				boolean flag = false;
				if (ms != null) {
					for (int j = 0; j < ms.size(); j++) {
						if (menu.getId().equals(ms.get(j).getId())) { // 存在
							flag = true;
							ms.remove(j);
							break;
						}
					}
				}
				if (flag) {
					buffer.append("'true'");
				} else {
					buffer.append("'false'");
				}
				buffer.append(",isLeaf:");
				buffer.append(menu.getIsLeaf());
				buffer.append("}");
				if (i != menus.size() - 1) {
					buffer.append(",");
				}
			}
		}
		buffer.append("]");
		return buffer.toString();
	}
	
	/**
	 * 
	 * 设置菜单
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月30日      新建
	 * </pre>
	 *
	 * @param positionId
	 * @param leafMenuIds
	 * @return
	 */
	@RequestMapping("/saveMenu")
	@ResponseBody
	public Response saveMenu(@RequestParam int positionId, String leafMenuIds) {
		Response response = new Response();
		response.setStatus(1);
		try {
			List<Integer> menuIds = null;
			if (StringUtils.isNotBlank(leafMenuIds)) {
				menuIds = new ArrayList<>();
				for (String s : leafMenuIds.split(",")) {
					menuIds.add(Integer.parseInt(s));
				}
			}
			positionService.savePositionMenu(positionId, menuIds);
		} catch (Exception e) {
			log.error("保存菜单", e);
			response.setStatus(-1);
			response.setMessage("操作失败");
		}
		return response;
	}
	
	/**
	 * 
	 * 列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月6日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping("/list2")
	@ResponseBody
	public Response list2(PositionReq req) {
		return positionService.list2(req);
	}
	
	/**
	 * 
	 * 获取职位存在的权限
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月6日      新建
	 * </pre>
	 *
	 * @param department
	 * @param cityId
	 * @param level
	 * @return
	 */
	@RequestMapping("/getPositionPriFunctions2")
	@ResponseBody
	public List<PriFunction> getPositionPriFunctions2(@RequestParam byte department, @RequestParam int cityId, 
			@RequestParam int level) {
		return positionService.getPositionPriFunctions2(department, cityId, level);
	}

	/**
	 * 
	 * 保存职位的权限列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月6日      新建
	 * </pre>
	 *
	 * @param department
	 * @param cityId
	 * @param level
	 * @param addFunctionIds
	 * @param deleteFunctionIds
	 * @return
	 */
	@RequestMapping("/savePositionPriFunction2")
	@ResponseBody
	public Response savePositionPriFunction2(@RequestParam byte department, @RequestParam int cityId, 
			@RequestParam int level, String addFunctionIds, String deleteFunctionIds) {
		Response response = new Response();
		response.setStatus(-1);
		try {
			List<Integer> aFids = null;
			if (StringUtils.isNotBlank(addFunctionIds)) {
				String[] fs = addFunctionIds.split(",");
				aFids = new ArrayList<Integer>();
				for (String f : fs) {
					aFids.add(Integer.parseInt(f));
				}
			}
			List<Integer> dFids = null;
			if (StringUtils.isNotBlank(deleteFunctionIds)) {
				String[] fs = deleteFunctionIds.split(",");
				dFids = new ArrayList<Integer>();
				for (String f : fs) {
					dFids.add(Integer.parseInt(f));
				}
			}
			if (aFids != null || dFids != null) {
				positionService.savePositionPriFunction2(department, cityId, level, aFids, dFids);
			}
			response.setStatus(1);
		} catch (Exception e) {
			log.error("保存职位权限", e);
			response.setMessage("操作失败");
		}
		return response;
	}
	
	/**
	 * 
	 * 菜单树状图
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月6日      新建
	 * </pre>
	 *
	 * @param level
	 * @param appName
	 * @param cityId
	 * @param department
	 * @return
	 */
	@RequestMapping("/menuTree2")
	@ResponseBody
	public String menuTree2(@RequestParam int level, @RequestParam String appName, 
			@RequestParam int cityId, @RequestParam byte department) {
		List<Menu> menus = positionService.getLeafMenuList(appName); // 获取所有
		StringBuilder buffer = new StringBuilder("[");
		if (!menus.isEmpty()) {
			List<Menu> ms = positionService.getPositionMenuList2(department, cityId, level, appName); // 获取该职位下的数据
			for (int i = 0; i < menus.size(); i++) {
				Menu menu = menus.get(i);
				buffer.append("{id:");
				buffer.append(menu.getId());
				buffer.append(",text:'");
				buffer.append(menu.getName());
				buffer.append("',pid:");
				buffer.append(menu.getParentId());
				buffer.append(",checked:");
				boolean flag = false;
				if (ms != null) {
					for (int j = 0; j < ms.size(); j++) {
						if (menu.getId().equals(ms.get(j).getId())) { // 存在
							flag = true;
							ms.remove(j);
							break;
						}
					}
				}
				if (flag) {
					buffer.append("'true'");
				} else {
					buffer.append("'false'");
				}
				buffer.append(",isLeaf:");
				buffer.append(menu.getIsLeaf());
				buffer.append("}");
				if (i != menus.size() - 1) {
					buffer.append(",");
				}
			}
		}
		buffer.append("]");
		return buffer.toString();
	}
	
	/**
	 * 
	 * 保存职位菜单
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月6日      新建
	 * </pre>
	 *
	 * @param department
	 * @param cityId
	 * @param level
	 * @param addMenuIds
	 * @param deleteMenuIds
	 * @return
	 */
	@RequestMapping("/saveMenu2")
	@ResponseBody
	public Response saveMenu2(@RequestParam byte department, @RequestParam int cityId, 
			@RequestParam int level, String addMenuIds, String deleteMenuIds) {
		Response response = new Response();
		response.setStatus(-1);
		try {
			List<Integer> aMids = null;
			if (StringUtils.isNotBlank(addMenuIds)) {
				String[] fs = addMenuIds.split(",");
				aMids = new ArrayList<Integer>();
				for (String f : fs) {
					aMids.add(Integer.parseInt(f));
				}
			}
			List<Integer> dMids = null;
			if (StringUtils.isNotBlank(deleteMenuIds)) {
				String[] fs = deleteMenuIds.split(",");
				dMids = new ArrayList<Integer>();
				for (String f : fs) {
					dMids.add(Integer.parseInt(f));
				}
			}
			if (aMids != null || dMids != null) {
				positionService.savePositionMenu2(department, cityId, level, aMids, dMids);
			}
			response.setStatus(1);
		} catch (Exception e) {
			log.error("保存职位菜单", e);
			response.setMessage("操作失败");
		}
		return response;
	}
	
}
