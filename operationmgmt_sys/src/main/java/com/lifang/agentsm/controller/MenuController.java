package com.lifang.agentsm.controller;

import java.util.List;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.model.Menu;
import com.lifang.agentsm.model.req.MenuReq;
import com.lifang.agentsm.service.MenuService;
import com.lifang.model.Response;

/**
 * 
 * 菜单
 *
 * @author   Yang'ushan
 * @Date	 2015年7月27日		下午2:23:41
 *
 * @see
 */
@Controller
@RequestMapping(value=("/menu"), produces = { "application/json;charset=UTF-8" })
@Log4j
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	/**
	 * 
	 * 列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Response list(MenuReq req) {
		return menuService.list(req);
	}
	
	/**
	 * 
	 * 获取父级tree树图
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param name
	 * @param appName
	 * @return
	 */
	@RequestMapping("/parentTree")
	@ResponseBody
	public String getMenuParentTree(String name, String appName) {
		List<Menu> menus = menuService.getMenuParentTree(name, appName);
		if (!menus.isEmpty()) {
			StringBuffer buffer = new StringBuffer("[");
			for (int i = 0; i < menus.size(); i++) {
				Menu menu = menus.get(i);
				buffer.append("{id:");
				buffer.append(menu.getId());
				buffer.append(",text:'");
				buffer.append(menu.getName());
				buffer.append("',pid:");
				buffer.append(menu.getParentId());
				buffer.append("}");
				if (i != menus.size() - 1) {
					buffer.append(",");
				}
			}
			buffer.append("]");
			return buffer.toString();
		}
		return "[]";
	}
	
	/**
	 * 
	 * 添加
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月27日      新建
	 * </pre>
	 *
	 * @param menu
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Response add(Menu menu) {
		Response response = new Response();
		response.setStatus(-1);
		if (StringUtils.isBlank(menu.getName())) {
			response.setMessage("名称不能为空！");
		} else if (menu.getParentId() == null) {
			response.setMessage("parentId不能为空！");
		} else if (menu.getIsLeaf() == null) {
			response.setMessage("是否子节点不能为空！");
		} else if (menu.getIsLeaf() == (byte)1 && 
				StringUtils.isBlank(menu.getUrl())) {
			response.setMessage("子节点的URL不能为空！");
		} else if (StringUtils.isBlank(menu.getAppName())) {
			response.setMessage("数据不完整");
		} else {
			try {
				log.info("添加菜单：" + menu);
				menuService.add(menu);
				response.setStatus(1);
			} catch (Exception e) {
				log.error("添加菜单", e);
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
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail")
	public String menu(@RequestParam int id, Model model) {
		Menu menu = menuService.menu(id);
		model.addAttribute("menu", menu);
		return "../../pages/menu/menu";
	}
	
	/**
	 * 
	 * 修改
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param menu
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Response update(Menu menu) {
		Response response = new Response();
		response.setStatus(-1);
		if (StringUtils.isBlank(menu.getName())) {
			response.setMessage("名称不能为空！");
		} else if (menu.getIsLeaf() == (byte)1 && 
				StringUtils.isBlank(menu.getUrl())) {
			response.setMessage("子节点的URL不能为空！");
		} else if (menu.getId() == null) {
			response.setMessage("数据不完整！");
		} else {
			try {
				log.info("修改菜单：" + menu);
				int count = menuService.update(menu);
				if (count > 0) {
					response.setStatus(1);
				} else {
					response.setMessage("数据已被删除！");
				}
			} catch (Exception e) {
				log.error("修改菜单", e);
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
	 *     Yang'ushan:   2015年7月28日      新建
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
			log.info("删除菜单：" + id);
			menuService.delete(id);
			response.setStatus(1);
		} catch (Exception e) {
			log.error("删除菜单", e);
			response.setMessage("操作失败");
		}
		return response;
	}
	
}
