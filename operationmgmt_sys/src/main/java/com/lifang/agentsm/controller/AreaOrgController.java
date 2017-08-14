package com.lifang.agentsm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.DicArea;
import com.lifang.agentsm.entity.DicAreaNew;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.AreaOrg;
import com.lifang.agentsm.model.AreaOrgMiniuiEntity;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.model.TravelAllowanceSet;
import com.lifang.agentsm.model.TravelAllowanceSetDetail;
import com.lifang.agentsm.model.enums.AreaOrgLevel;
import com.lifang.agentsm.model.req.AreaOrgReq;
import com.lifang.agentsm.service.AreaOrgService;
import com.lifang.agentsm.service.DicAreaNewService;
import com.lifang.agentsm.service.TransferService;
import com.lifang.agentsm.service.TravelAllowanceSetService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.model.Response;
import com.lifang.utils.PositionTransformUtils;

/**
 * 
 * 组织架构
 *
 * @author   Yang'ushan
 * @Date	 2015年8月3日		下午6:29:06
 *
 * @see
 */
@Controller
@RequestMapping("/areaOrg")
@Log4j
public class AreaOrgController extends BaseController {

	@Autowired
	private AreaOrgService areaOrgService;
	@Autowired
	private AgentSOAServer agentSOAServer;
	@Autowired
	private TransferService transferService;
	@Autowired
	private DicAreaNewService dicAreaNewService;
	 @Autowired
	 private TravelAllowanceSetService travelAllowanceSetService;
	/**
	 * 
	 * 城市列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月3日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping("/city/list")
	@ResponseBody
	public Response list2City(AreaOrgReq req) {
		return areaOrgService.list2City(req);
	}
	
	/**
	 * 
	 * 添加
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月3日      新建
	 * </pre>
	 *
	 * @param areaOrg
	 * @param towns
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Response add(AreaOrg areaOrg, String towns) {
		Response response = new Response();
		response.setStatus(-1);
		if (areaOrg.getCityId() == null) {
			response.setMessage("请选择城市！");
		} else if (StringUtils.isBlank(areaOrg.getName())) {
			response.setMessage("名称不能为空！");
		} else if (areaOrg.getLevel() == null) {
			response.setMessage("等级不能为空！");
		} else if (areaOrg.getParentId() == null) {
			response.setMessage("请选择上级架构！");
		} else {
			try {
				int isExists = areaOrgService.validateNameIsExist(areaOrg.getParentId(), areaOrg.getName());
				if (isExists > 0) {
					response.setMessage("该名称已存在，无法使用！");
					return response;
				}
				List<DicArea> dicAreas = null;
				if (areaOrg.getLevel() == AreaOrgLevel.City.value) { // 城市需要验证，只能有一个组织架构
					int count = areaOrgService.validateCityHaveCount(areaOrg.getCityId());
					if (count > 0) {
						response.setMessage("该城市已经存在组织，无法再次添加！");
						return response;
					}
				} else if (areaOrg.getLevel() == AreaOrgLevel.Area.value) { // 区域界别
					if (areaOrg.getFranchiseeId() == null) {
						response.setMessage("请选择加盟商！");
						return response;
					}
				} else if (areaOrg.getLevel() == AreaOrgLevel.Store.value) { // 门店需要选择板块
					if (StringUtils.isBlank(towns)) {
						response.setMessage("请选择门店所对应的板块！");
						return response;
					}
					if (StringUtils.isBlank(areaOrg.getLongitude())) {
						response.setMessage("经度不能为空！");
						return response;
					}
					if (StringUtils.isBlank(areaOrg.getLatitude())) {
						response.setMessage("纬度不能为空！");
						return response;
					}
					try {
						String[] ts = towns.split(";");
						dicAreas = new ArrayList<DicArea>();
						for (String td : ts) {
							String[] tds = td.split(",");
							DicArea dicArea = new DicArea();
							dicArea.setDistrictid(Integer.parseInt(tds[0]));
							dicArea.setTownid(Integer.parseInt(tds[1]));
							dicAreas.add(dicArea);
						}
					} catch (Exception e) {
						log.error("添加门店的板块数据不正确！", e);
						response.setMessage("添加门店的板块数据不正确！");
						return response;
					}
				}
				areaOrgService.add(areaOrg, dicAreas);
				//保存行政补贴
				TravelAllowanceSet set = new TravelAllowanceSet();
				set.setCityId(areaOrg.getCityId());
				int i = 1;
				if(travelAllowanceSetService.verifyCityIdHaveExist(areaOrg.getCityId()) == 0)
					i = travelAllowanceSetService.saveTravelData(set);
				if(i>0){
				    for(int j=1;j<9;j++){
				        TravelAllowanceSetDetail setDetail =new TravelAllowanceSetDetail();
				        setDetail.setType(j);
				        setDetail.setTravelId(set.getId());
				        switch (j) {//'1.发布房源 2.实景（非现拍） 3.实景（现拍）4.视频（非现拍） 5.视频（现拍）6.好评 7.带盘 8 房源无效成功'
			                case 1:
			                    setDetail.setWkCoin(0);
			                    break;
			                case 2:
			                    setDetail.setWkCoin(0);
			                    break;
			                case 3:
			                    setDetail.setWkCoin(0);
			                    break;
			                case 4:
			                    setDetail.setWkCoin(0);
			                    break;
			                case 5:
			                    setDetail.setWkCoin(0);
			                    break;
			                case 6:
			                    setDetail.setWkCoin(0);
			                    break;
			                case 7:
			                    setDetail.setWkCoin(0);
			                    break;
			                case 8:
			                    setDetail.setWkCoin(0);
			                    break;
			            }
				        travelAllowanceSetService.saveInsertTravelDetail(setDetail);
				    }
				}
				response.setStatus(1);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("添加组织架构", e);
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
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/city/detail")
	public String city(@RequestParam int id, Model model) {
		AreaOrg areaOrg = areaOrgService.city(id);
		model.addAttribute("areaOrg", areaOrg);
		return "../../pages/areaOrg/city/areaOrg";
	}
	
	/**
	 * 
	 * 修改
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param areaOrg
	 * @param oldParentId
	 * @param towns 新增的板块列表
	 * @param deleteTowns 删除的板块列表
	 * @param oldName 旧名称
	 * @param oldFranchiseeId 旧名称
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Response update(AreaOrg areaOrg, @RequestParam int oldParentId,
			String towns, String deleteTowns, @RequestParam String oldName, Integer oldFranchiseeId) {
		Response response = new Response();
		response.setStatus(-1);
		if (StringUtils.isBlank(areaOrg.getName())) {
			response.setMessage("名称不能为空！");
		} else if (areaOrg.getLevel() == null) {
			response.setMessage("等级不能为空！");
		} else if (areaOrg.getParentId() == null) {
			response.setMessage("请选择上级架构！");
		} else if (areaOrg.getId() == null || StringUtils.isBlank(areaOrg.getCode())) {
			response.setMessage("数据不完整！");
		} else {
			try {
				if (!areaOrg.getName().equals(oldName)) {
					int isExists = areaOrgService.validateNameIsExist(areaOrg.getParentId(), areaOrg.getName());
					if (isExists > 0) {
						response.setMessage("该名称已存在，无法使用！");
						return response;
					}
				}
				List<DicArea> dicAreas = null;
				List<DicArea> deleteDicAreas = null;
				if (areaOrg.getLevel() == AreaOrgLevel.Store.value) { // 门店需要选择板块
					if (StringUtils.isBlank(areaOrg.getLongitude())) {
						response.setMessage("经度不能为空！");
						return response;
					}
					if (StringUtils.isBlank(areaOrg.getLatitude())) {
						response.setMessage("纬度不能为空！");
						return response;
					}
					try {
						if (StringUtils.isNotBlank(towns)) {
							String[] ts = towns.split(";");
							dicAreas = new ArrayList<DicArea>();
							for (String td : ts) {
								String[] tds = td.split(",");
								DicArea dicArea = new DicArea();
								dicArea.setDistrictid(Integer.parseInt(tds[0]));
								dicArea.setTownid(Integer.parseInt(tds[1]));
								dicAreas.add(dicArea);
							}
						}
						if (StringUtils.isNotBlank(deleteTowns)) {
							String[] ts = deleteTowns.split(";");
							deleteDicAreas = new ArrayList<DicArea>();
							for (String td : ts) {
								String[] tds = td.split(",");
								DicArea dicArea = new DicArea();
								dicArea.setDistrictid(Integer.parseInt(tds[0]));
								dicArea.setTownid(Integer.parseInt(tds[1]));
								deleteDicAreas.add(dicArea);
							}
						}
					} catch (Exception e) {
						log.error("修改门店的板块数据不正确！", e);
						response.setMessage("修改门店的板块数据不正确！");
						return response;
					}
				} else if (areaOrg.getLevel() == AreaOrgLevel.Area.value) { // 区域界别
					if (areaOrg.getFranchiseeId() == null || oldFranchiseeId == null) {
						response.setMessage("请选择加盟商！");
						return response;
					}
				}
				int count = areaOrgService.update(areaOrg, oldParentId, dicAreas, deleteDicAreas,
						oldFranchiseeId);
				if (count > 0) {
					response.setStatus(1);
				} else {
					response.setMessage("数据已被删除！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("修改组织架构", e);
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
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param code
	 * @param level
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Response delete(@RequestParam String code, @RequestParam int level) {
		Response response = new Response();
		response.setStatus(1);
		try {
			if (level != AreaOrgLevel.Store.value) {
				int count = areaOrgService.validateStoreByCode(code);
				if (count > 0) {
					response.setMessage("该组织架构下存在有效门店，无法删除！");
					response.setStatus(-1);
					return response;
				}
			} else { // 验证门店下面是否还有有效的经纪人
				List<Agent> agents = agentSOAServer.getAgentList(null, null, null, code, null);
				if (agents != null && agents.size() > 0) {
					response.setMessage("该门店下面存在有效经纪人，无法删除！");
					response.setStatus(-1);
					return response;
				}
			}
			areaOrgService.delete(code, level);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除组织架构", e);
			response.setStatus(-1);
			response.setMessage("操作失败");
		}
		return response;
	}
	
	/**
	 * 
	 * 区域组织列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping("/area/list")
	@ResponseBody
	public Response list2Area(AreaOrgReq req) {
		return areaOrgService.list2Area(req);
	}
	
	/**
	 * 
	 * 获取下拉框数据
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param level
	 * @param cityId
	 * @param parentId
	 * @param session
	 * @return
	 */
	@RequestMapping("/list/simple")
	@ResponseBody
	public List<AreaOrgMiniuiEntity> simple(@RequestParam int level, Integer cityId, 
			Integer parentId, HttpSession session) {
	    LfEmployee lf;
        try {
            lf = getLoginEmployeeInfo(session);
            System.out.println(lf.getCityId());
            if(lf.getCityId()==1){
                cityId = null;
            } else {
                cityId = lf.getCityId();
            }
        }
        catch (Exception e) {
            log.error("【获取城市列表出错：】", e);
            e.printStackTrace();
        }
		List<AreaOrgMiniuiEntity> list = areaOrgService.simple(level, cityId, parentId);
		return list;
	}
	
	/**
	 * 
	 * 区域组织详情
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月4日      新建
	 * </pre>
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/area/detail")
	public String area(int id, Model model) {
		AreaOrg areaOrg = areaOrgService.area(id);
		model.addAttribute("areaOrg", areaOrg);
		return "../../pages/areaOrg/area/areaOrg";
	}
	
	/**
	 * 
	 * 门店列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月5日      新建
	 * </pre>
	 *
	 * @param req
	 * @return
	 */
	@RequestMapping("/store/list")
	@ResponseBody
	public Response list2Store(AreaOrgReq req) {
		return areaOrgService.list2Store(req);
	}
	
	/**
	 * 
	 * 门店详情
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年8月5日      新建
	 * </pre>
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/store/detail")
	public String store(@RequestParam int id, Model model) {
		AreaOrg areaOrg = areaOrgService.store(id);
		List<DicArea> towns = areaOrgService.getStrowTownList(id);
		if (towns != null && towns.size() > 0) {
			for (DicArea dic : towns) {
				dic.setTown(dic.getTown().replaceAll("'", "\'"));
				dic.setDistrict(dic.getDistrict().replaceAll("'", "\'"));
			}
		}
		model.addAttribute("areaOrg", areaOrg);
		model.addAttribute("towns", towns);
		return "../../pages/areaOrg/store/areaOrg";
	}
	
	/**
	 * 
	 * 获取添加门店时，专属显示的区域列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年9月29日      新建
	 * </pre>
	 *
	 * @param districtId
	 * @return
	 */
	@RequestMapping("/store/getStorePageTownListByDistrictId")
	@ResponseBody
	public List<DicArea> getStorePageTownListByDistrictId(@RequestParam int districtId) {
		return areaOrgService.getStorePageTownListByDistrictId(districtId);
	}
	
	/**
	 * 
	 * 获取加盟商列表，根据城市，(志远是特殊的)
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年12月23日      新建
	 * </pre>
	 *
	 * @param cityId
	 * @return
	 */
	@RequestMapping("/franchisee/getSimpleList/byCityId")
	@ResponseBody
	public List<MiniuiEntity> getFranchiseeListByCityId(@RequestParam int cityId) {
		return transferService.getSimpleFranchiseeListByCityId(cityId);
	}


	/**
	 * 打开地图页面
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yangushan:   2016-01-11 19:03:49     新建
	 * </pre>
	 *
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @return java.lang.String
	 */
	@RequestMapping("/store/openContour")
	public String openContour(String longitude, String latitude, int cityId, Model model) {
		if (StringUtils.isNotBlank(longitude) &&
				StringUtils.isNotBlank(latitude)) {
			String position = PositionTransformUtils.gcj2bd(longitude, latitude);
			if (position != null) {
				String[] positions = position.split(",");
				model.addAttribute("longitude", longitude);
				model.addAttribute("latitude", latitude);
			}
		}
		DicAreaNew dicAreaNew = dicAreaNewService.selectByPrimaryKey((long)cityId);
		if (dicAreaNew != null) {
			model.addAttribute("cityName", dicAreaNew.getName());
		} else {
			model.addAttribute("cityName", "上海市");
		}
		return "../../pages/areaOrg/store/StoreMap";
	}

	/**
	 * 百度转成国测局
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yangushan:   2016-01-12 11:58:53     新建
	 * </pre>
	 *
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @return java.lang.String 返回经度和纬度,拼接
	 */
	@RequestMapping("/store/bd2gcj")
	@ResponseBody
	public String bd2gcj(@RequestParam String longitude, @RequestParam String latitude) {
		if (StringUtils.isBlank(longitude) ||
				StringUtils.isBlank(latitude)) {
			return "";
		}
		return PositionTransformUtils.bd2gcj(longitude, latitude);
	}
	
}
