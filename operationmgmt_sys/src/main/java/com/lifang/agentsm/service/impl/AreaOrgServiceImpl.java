package com.lifang.agentsm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.dao.read.AreaOrgReadMapper;
import com.lifang.agentsm.dao.read.PositionReadMapper;
import com.lifang.agentsm.dao.write.AreaOrgWriteMapper;
import com.lifang.agentsm.dao.write.LfFranchiseeAreaWriteMapper;
import com.lifang.agentsm.dao.write.PositionWriteMapper;
import com.lifang.agentsm.entity.DicArea;
import com.lifang.agentsm.model.AreaOrg;
import com.lifang.agentsm.model.AreaOrgMiniuiEntity;
import com.lifang.agentsm.model.Position;
import com.lifang.agentsm.model.enums.AreaOrgLevel;
import com.lifang.agentsm.model.req.AreaOrgReq;
import com.lifang.agentsm.service.AreaOrgService;
import com.lifang.hr.facade.EmployeeService;
import com.lifang.model.PageResponse;
import com.lifang.model.Response;

import lombok.extern.log4j.Log4j;

/**
 * 
 * 组织架构
 *
 * @author   Yang'ushan
 * @Date	 2015年8月3日		下午6:30:06
 *
 * @see
 */
@Service
@Log4j
public class AreaOrgServiceImpl implements AreaOrgService {

	@Autowired
	private AreaOrgReadMapper areaOrgReadMapper;
	@Autowired
	private AreaOrgWriteMapper areaOrgWriteMapper;
	@Autowired
	private PositionReadMapper positionReadMapper;
	@Autowired
	private PositionWriteMapper positionWriteMapper;
	@Autowired
	private LfFranchiseeAreaWriteMapper franchiseeAreaWriteMapper;
	@Autowired
	private EmployeeService employeeServiceTest;

	@Override
	public Response list2City(AreaOrgReq req) {
		if (req.getPageIndex() != null) {
			req.setPageIndex(req.getPageIndex()+1);
		}
		Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
		PageResponse response = new PageResponse("success", 1, areaOrgReadMapper.list2City(pagination));
		response.setTotal(pagination.getTotal());
		return response;
	}

	@Override
	@Transactional
	public void add(AreaOrg areaOrg, List<DicArea> dicAreas) {
		try {
			areaOrg.setCode(getAreaOrgCode(areaOrg.getParentId()));
			// 添加
			areaOrgWriteMapper.add(areaOrg);
			if (areaOrg.getLevel() == AreaOrgLevel.Store.value) { // 门店级别
				areaOrgWriteMapper.addStoreTownRelations(areaOrg.getId(), dicAreas);
			} else if (areaOrg.getLevel() == AreaOrgLevel.Area.value) { // 区域级别
				// 添加区域和加盟商的关系
				franchiseeAreaWriteMapper.add(areaOrg.getId(), areaOrg.getFranchiseeId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 
	 * 获取组织架构的code
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	private String getAreaOrgCode(int id) {
		String parentCode = areaOrgReadMapper.getCodeById(id);
		if (parentCode == null) {
			log.error("根据组织添加职位时，获取parentCode为Null");
			throw new RuntimeException();
		}
		String maxCode = areaOrgReadMapper.getParentMaxCode(id);
		if (maxCode == null) {
			parentCode += "001";
		} else {
			String last = maxCode.substring(maxCode.length() - 3);
			parentCode += String.format("%03d", Integer.parseInt(last)+1);
		}
		return parentCode;
	}
	
	/**
	 * 
	 * 设置职位
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param areaOrg
	 * @param department
	 * @param name
	 * @return
	 */
	/*private Position addPosition(AreaOrg areaOrg, byte department, String name) {
		return addPosition(areaOrg, department, name, null, null);
	}*/
	
	/**
	 * 
	 * 设置职位
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param areaOrg
	 * @param department
	 * @param name
	 * @param parentId
	 * @param parentCode
	 * @return
	 */
	@SuppressWarnings("unused")
	private Position addPosition(AreaOrg areaOrg, byte department, String name, Integer parentId, String parentCode) {
		Position position = new Position();
		position.setName(name);
		position.setDepartment(department);
		position.setAreaOrgId(areaOrg.getId());
		position.setCityId(areaOrg.getCityId());
		if (parentId == null) {
			parentId = positionReadMapper.getPositionIdByDepartmentAndChildAreaOrgId(areaOrg.getParentId(), department);
			if (parentId == null) {
				log.error("根据组织架构添加职位的时候，没有获取到相应的上级职位编号ID, 组织架构：" + areaOrg);
				throw new RuntimeException();
			}
		}
		position.setParentId(parentId);
		position.setCode(getPositionCode(parentId, parentCode));
		positionWriteMapper.add(position);
		// 获取相同职位，权限，部门的人的权限
		List<Integer> functionIds = positionReadMapper.getFunctionIdsByDepAndCityIdAndLevel(department, areaOrg.getCityId(), areaOrg.getLevel());
		if (functionIds != null && !functionIds.isEmpty()) {
			positionWriteMapper.addPositionFunctionRelation(position.getId(), functionIds);
		}
		return position;
	}
	
	/**
	 * 
	 * 获取职位的code
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param id
	 * @param parentCode
	 * @return
	 */
	private String getPositionCode(int id, String parentCode) {
		String code = null;
		if (parentCode == null) {
			parentCode = positionReadMapper.getCodeById(id);
			String maxCode = positionReadMapper.getMaxCodeByParentId(id);
			if (maxCode != null) {
				String last = maxCode.substring(maxCode.length() - 3);
				code = parentCode + String.format("%03d", Integer.parseInt(last)+1);
			} else {
				code = parentCode + "001";
			}
		} else {
			code = parentCode + "001";
		}
		return code;
	}

	@Override
	public AreaOrg city(int id) {
		return areaOrgReadMapper.city(id);
	}

	@Override
	@Transactional
	public int update(AreaOrg areaOrg, int oldParentId, List<DicArea> towns, List<DicArea> deleteTowns,
			Integer oldFranchiseeId) {
		try {
			if (areaOrg.getParentId() != oldParentId) { // 修改了上级
				// 修改code包含子列表
				String code = getAreaOrgCode(areaOrg.getParentId());
				areaOrgWriteMapper.updateCode(code, areaOrg.getCode());
			}
			int count = areaOrgWriteMapper.update(areaOrg);
			// 添加关系
			if (count > 0) {
				if (areaOrg.getLevel() == AreaOrgLevel.Store.value) {
					// 判断门店之前是否存在于板块的关系，如果存在修改，不存在则添加
					if (towns != null && towns.size() > 0) {
						for (int i = 0; i < towns.size(); i++) {
							DicArea town = towns.get(i);
							areaOrgWriteMapper.addStoreTownRelation(areaOrg.getId(), town.getTownid(), town.getDistrictid());
						}
					}
					// 删除解除的关系
					if (deleteTowns != null && deleteTowns.size() > 0) {
						areaOrgWriteMapper.deleteStoreTownRelations(areaOrg.getId(), deleteTowns);
					}
					// 获取区域的加盟商编号
					AreaOrg area = areaOrgReadMapper.area(areaOrg.getParentId());
					if (area == null) { // 为空
						throw new RuntimeException();
					}
					employeeServiceTest.updateFranchiseeIdByStoreId(areaOrg.getId(), area.getFranchiseeId());
				} else if (areaOrg.getLevel() == AreaOrgLevel.Area.value) { // 区域级别
					if (oldFranchiseeId.intValue() != areaOrg.getFranchiseeId().intValue()) { // 修改了区域
						franchiseeAreaWriteMapper.delete(areaOrg.getId());
						franchiseeAreaWriteMapper.add(areaOrg.getId(), areaOrg.getFranchiseeId());
						// 调用人事系统，更新人事的franchiseeId
						employeeServiceTest.updateFranchiseeIdByAreaOrgId(areaOrg.getId(), areaOrg.getFranchiseeId());
					}
				}
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	@Transactional
	public int delete(String code, int level) {
		try {
			int count = areaOrgWriteMapper.delete(code);
			if (count > 0 && level == AreaOrgLevel.Area.value) { // 区域级别，删除区域和志远的关系
				AreaOrg areaOrg = areaOrgReadMapper.getAreaOrgByCode(code);
				franchiseeAreaWriteMapper.delete(areaOrg.getId());
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int validateStoreByCode(String code) {
		return areaOrgReadMapper.validateStoreByCode(code);
	}

	@Override
	public Response list2Area(AreaOrgReq req) {
		if (req.getPageIndex() != null) {
			req.setPageIndex(req.getPageIndex()+1);
		}
		Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
		PageResponse response = new PageResponse("success", 1, areaOrgReadMapper.list2Area(pagination));
		response.setTotal(pagination.getTotal());
		return response;
	}

	@Override
	public List<AreaOrgMiniuiEntity> simple(int level, Integer cityId, Integer parentId) {
		return areaOrgReadMapper.simple(level, cityId, parentId);
	}

	@Override
	public AreaOrg area(int id) {
		return areaOrgReadMapper.area(id);
	}

	@Override
	public Response list2Store(AreaOrgReq req) {
		if (req.getPageIndex() == null) {
			req.setPageIndex(0);
		}
		req.setPage(req.getPageIndex()*req.getPageSize());
		PageResponse response = new PageResponse();
		List<AreaOrg> list = areaOrgReadMapper.list2Store(req);
		response.setData(list);
		response.setStatus(1);
		response.setTotal(areaOrgReadMapper.list2StoreCount(req));
		return response;
	}

	@Override
	public AreaOrg store(int id) {
		return areaOrgReadMapper.store(id);
	}

	@Override
	public int validateCityHaveCount(int cityId) {
		return areaOrgReadMapper.validateCityHaveCount(cityId);
	}

	/**
	 * @see com.lifang.agentsm.service.AreaOrgService#getStorePageTownListByDistrictId(int)
	 */
	@Override
	public List<DicArea> getStorePageTownListByDistrictId(int districtId) {
		return areaOrgReadMapper.getStorePageTownListByDistrictId(districtId);
	}

	/**
	 * @see com.lifang.agentsm.service.AreaOrgService#getStrowTownList(int)
	 */
	@Override
	public List<DicArea> getStrowTownList(int id) {
		return areaOrgReadMapper.getStrowTownList(id);
	}

	/**
	 * @see com.lifang.agentsm.service.AreaOrgService#validateNameIsExist(int, java.lang.String)
	 */
	@Override
	public int validateNameIsExist(int parentId, String name) {
		return areaOrgReadMapper.validateNameIsExist(parentId, name);
	}

	/**
	 * @see com.lifang.agentsm.service.AreaOrgService#validateStoreHaveEmployee(java.lang.String)
	 */
	@Override
	public int validateStoreHaveEmployee(String code) {
		return areaOrgReadMapper.validateStoreHaveEmployee(code);
	}
	
}
