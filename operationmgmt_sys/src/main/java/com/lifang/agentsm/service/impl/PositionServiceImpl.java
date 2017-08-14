package com.lifang.agentsm.service.impl;

import java.util.List;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.dao.read.PositionReadMapper;
import com.lifang.agentsm.dao.write.PositionWriteMapper;
import com.lifang.agentsm.model.AppnameMiniuiEntity;
import com.lifang.agentsm.model.Menu;
import com.lifang.agentsm.model.Position;
import com.lifang.agentsm.model.PriFunction;
import com.lifang.agentsm.model.enums.AreaOrgLevel;
import com.lifang.agentsm.model.enums.DepartmentType;
import com.lifang.agentsm.model.req.PositionReq;
import com.lifang.agentsm.service.PositionService;
import com.lifang.model.PageResponse;
import com.lifang.model.Response;

/**
 * 
 * 职位
 *
 * @author   Yang'ushan
 * @Date	 2015年7月28日		下午4:09:38
 *
 * @see
 */
@Service
@Log4j
public class PositionServiceImpl implements PositionService {

	@Autowired
	private PositionReadMapper positionReadMapper;

	
	@Autowired
	private PositionWriteMapper positionWriteMapper;
	
	@Override
	public Response list(PositionReq req) {
		if (req.getPageIndex() != null) {
			req.setPageIndex(req.getPageIndex()+1);
		}
		Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
		List<Position> ps = positionReadMapper.list(pagination);
		if (ps.size() > 0) {
			for (Position p : ps) {
//				PositionCache cache = areaOrgPriSOAClient.getShowNameByPositionId(p.getId());
//				if (cache != null) {
//					p.setAreaOrg(cache.getShowName());
//				}
			}
		}
		PageResponse response = new PageResponse("success", 1, ps);
		response.setTotal(pagination.getTotal());
		return response;
	}

	@Override
	public List<AppnameMiniuiEntity> getAppnameByDepartment(int department) {
		return positionReadMapper.getAppnameByDepartment(department);
	}

	@Override
	public List<PriFunction> getPositionPriFunctions(int id) {
		return positionReadMapper.getPositionPriFunctions(id);
	}

	@Override
	public List<PriFunction> getPriFunctions(String appName, String name) {
		return positionReadMapper.getPriFunctions(appName, name);
	}

	@Override
	@Transactional
	public void savePositionPriFunction(int positionId,
			List<Integer> functionIds) {
		try {
			List<Integer> relationids = positionReadMapper.getPositionFunctionRelationIdsAndNotExistsFunctionIds(positionId, functionIds, 2);
			if (functionIds != null && !functionIds.isEmpty()) {
				List<Integer> eids = positionReadMapper.existsFunctionIds(positionId, functionIds);
				if (eids.size() > 0) {
					functionIds.removeAll(eids);
				}
				if (functionIds.size() > 0) {
					positionWriteMapper.addPositionFunctionRelation(positionId, functionIds);
				}
			}
			// 删除不存在这里面的列表
			if (relationids != null && relationids.size() > 0) {
				positionWriteMapper.deletePositionFunctionRelationNotExistsFunctionIds(relationids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public List<Menu> getLeafMenuList(String appName) {
		return positionReadMapper.getLeafMenuList(appName);
	}

	@Override
	public List<Menu> getPositionMenuList(int positionId) {
		return positionReadMapper.getPositionMenuList(positionId);
	}

	@Override
	@Transactional
	public void savePositionMenu(int positionId, List<Integer> menuIds) {
		try {
			if (menuIds != null && menuIds.size() > 0) {
				List<Integer> functionIds = positionReadMapper.getFunctionIdsByMenuIds(menuIds);
				if (functionIds != null && functionIds.size() > 0) {
					List<Integer> relationIds = positionReadMapper.getPositionFunctionRelationIdsAndNotExistsFunctionIds(
							positionId, functionIds, 1);
					List<Integer> eids = positionReadMapper.existsFunctionIdsByMenuIds(positionId, menuIds);
					if (eids != null && eids.size() > 0) {
						functionIds.removeAll(eids);
					}
					positionWriteMapper.addPositionFunctionRelation(positionId, functionIds);
					if (relationIds != null && relationIds.size() > 0) {
						positionWriteMapper.deletePositionFunctionRelationNotExistsFunctionIds(relationIds);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public Response list2(PositionReq req) {
		if (req.getPageIndex() != null) {
			req.setPageIndex(req.getPageIndex() + 1);
		}
		Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
		List<Position> ps = positionReadMapper.list2(pagination);
		for (Position p : ps) {
			switch (AreaOrgLevel.newInstance(p.getLevel())) {
				case City :
					switch (DepartmentType.newInstance(p.getDepartment())) {
						case YeWu :
							p.setName("业务经理");
							break;
						case YunYing:
							p.setName("运营经理");
							break;
						case RenShi:
							p.setName("人事经理");
							break;
						case CaiWu:
							p.setName("财务经理");
							break;
						case FaWu:
							p.setName("法务经理");
							break;
						default :
							break;
					}
					break;
				case Area :
					if (p.getDepartment() == DepartmentType.RenShi.value) {
						p.setName("区域人事经理");
					} else {
						p.setName("区域经理");
					}
					break;
				case Store :
					p.setName("门店经理");
					break;
				default :
					break;
			}
		}
		PageResponse response = new PageResponse("success", 1, ps);
		response.setTotal(pagination.getTotal());
		return response;
	}

	@Override
	public List<PriFunction> getPositionPriFunctions2(byte department,
			int cityId, int level) {
		return positionReadMapper.getPositionPriFunctions2(department, cityId, level);
	}

	@Override
	@Transactional
	public int savePositionPriFunction2(byte department, int cityId, int level,
			List<Integer> addFunctionIds, List<Integer> deleteFunctionIds) {
		try {
			List<Integer> positionIds = positionReadMapper.getPositionIdsByDepAndLevelAndCityId(department, cityId, level);
			if (positionIds != null && positionIds.size() > 0) {
				// 需要添加的
				if (addFunctionIds != null && addFunctionIds.size() > 0) {
					for (Integer pid : positionIds) {
						positionWriteMapper.addPositionFunctionRelation(pid, addFunctionIds);
					}
				}
				// 需要删除的
				if (deleteFunctionIds != null && deleteFunctionIds.size() > 0) {
					for (Integer pid : positionIds) {
						positionWriteMapper.deletePositionFunctionRelation(pid, deleteFunctionIds);
					}
				}
				return positionIds.size();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public List<Menu> getPositionMenuList2(byte department, int cityId,
			int level, String appName) {
		return positionReadMapper.getPositionMenuList2(department, cityId, level, appName);
	}

	@Override
	@Transactional
	public int savePositionMenu2(byte department, int cityId, int level,
			List<Integer> addMenuIds, List<Integer> deleteMenuIds) {
		try {
			List<Integer> positionIds = positionReadMapper.getPositionIdsByDepAndLevelAndCityId(department, cityId, level);
			if (positionIds != null && positionIds.size() > 0) {
				// 需要添加的
				if (addMenuIds != null && addMenuIds.size() > 0) {
					List<Integer> functionIds = positionReadMapper.getFunctionIdsByMenuIds(addMenuIds);
					if (functionIds == null || functionIds.size() == 0) {
						log.error("保存职位菜单时，获取到的方法编号列表不正确！");
						throw new RuntimeException();
					}
					for (Integer pid : positionIds) {
						positionWriteMapper.addPositionFunctionRelation(pid, functionIds);
					}
				}
				// 需要删除的
				if (deleteMenuIds != null && deleteMenuIds.size() > 0) {
					List<Integer> functionIds = positionReadMapper.getFunctionIdsByMenuIds(deleteMenuIds);
					if (functionIds == null || functionIds.size() == 0) {
						log.error("保存职位菜单时，获取到的方法编号列表不正确！");
						throw new RuntimeException();
					}
					for (Integer pid : positionIds) {
						positionWriteMapper.deletePositionFunctionRelation(pid, functionIds);
					}
				}
				return positionIds.size();
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} 
	}

}
