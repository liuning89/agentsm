package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.dao.read.MenuReadMapper;
import com.lifang.agentsm.dao.write.MenuWriteMapper;
import com.lifang.agentsm.model.Menu;
import com.lifang.agentsm.model.PriFunction;
import com.lifang.agentsm.model.req.MenuReq;
import com.lifang.agentsm.service.MenuService;
import com.lifang.agentsm.service.PriFunctionService;
import com.lifang.model.PageResponse;
import com.lifang.model.Response;

/**
 * 
 * 菜单
 *
 * @author   Yang'ushan
 * @Date	 2015年7月27日		下午2:38:09
 *
 * @see
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuReadMapper menuReadMapper;
	
	@Autowired
	private MenuWriteMapper menuWriteMapper;
	
	@Autowired
	private PriFunctionService priFunctionService;
	
	@Override
	public Response list(MenuReq req) {
		if (req.getPageIndex() != null) {
			req.setPageIndex(req.getPageIndex()+1);
		}
		Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
		PageResponse response = new PageResponse("success", 1, menuReadMapper.list(pagination));
		response.setTotal(pagination.getTotal());
		return response;
	}

	@Override
	public List<Menu> getMenuParentTree(String name, String appName) {
		return menuReadMapper.getMenuParentTree(name, appName);
	}

	@Override
	@Transactional
	public int add(Menu menu) {
		try {
			if (menu.getIsLeaf() == (byte)1) { // 是子节点
				PriFunction priFunction = new PriFunction();
				priFunction.setName(menu.getName()+"[菜单]");
				priFunction.setAppName(menu.getAppName());
				priFunction.setType((byte)1); // 菜单自动生成
				priFunctionService.add(priFunction);
				menu.setFunctionId(priFunction.getId()); // 设置functionId
			} else { // 不是子节点
				Integer functionId = priFunctionService.getMenuPriFunction(menu.getAppName()+"_菜单父节点", menu.getAppName());
				if (functionId == null) { // 不存在，先添加这个function
					PriFunction priFunction = new PriFunction();
					priFunction.setName(menu.getAppName()+"_菜单父节点");
					priFunction.setAppName(menu.getAppName());
					priFunction.setType((byte)1); // 菜单自动生成
					priFunctionService.add(priFunction);
					functionId = priFunction.getId();
				}
				menu.setFunctionId(functionId);
			}
			return menuWriteMapper.add(menu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public Menu menu(int id) {
		return menuReadMapper.menu(id);
	}

	@Override
	public int update(Menu menu) {
		return menuWriteMapper.update(menu);
	}

	@Override
	@Transactional
	public int delete(int id) {
		try {
			List<Integer> cids = new ArrayList<Integer>();
			cids.add(id);
			getChildMenuIds(id, cids);
			int start = 0;
			int end = 0;
			int count = 0;
			do {
				end = start + 1000;
				if (end > cids.size()) {
					end = cids.size();
				}
				List<Integer> ids = cids.subList(start, end);
				count += menuWriteMapper.deletes(ids);
				start = end;
			} while (end < cids.size());
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 
	 * 递归获取子节点列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月28日      新建
	 * </pre>
	 *
	 * @param id
	 * @param ids
	 */
	private void getChildMenuIds(int id, List<Integer> ids) {
		List<Integer> cids = menuReadMapper.getChildMenuIds(id);
		if (cids != null && cids.size() > 0) {
			ids.addAll(cids);
			for (Integer cid : cids) {
				getChildMenuIds(cid, ids);
			}
		}
	}

}
