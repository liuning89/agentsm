package com.lifang.agentsm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import com.easemob.server.example.comm.Constants;
//import com.easemob.server.example.httpclient.apidemo.EasemobIMUsers;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.JsonNodeFactory;
//import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.ExcelHouseCountTown;
import com.lifang.agentsm.model.HouseCountTown;
import com.lifang.agentsm.service.HouseDataReport;
import com.lifang.agentsm.utils.ExcelUtils;

@Controller
@RequestMapping("/house")
public class HouseDataController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(HouseDataController.class);
	
	@Autowired
	private HouseDataReport houseDAtaReport;


	/**
	 * 经纪人列表页面跳转 功能描述:TODO(描述这个方法的作用)
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月7日      新建
	 * </pre>
	 *
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/showTownReport.action")
	public String agentListIndex() {
		return "housedata/houseData_Town";
	}


	/**
	 * 
	 * @param pars
	 * @return
	 */
	@RequestMapping(value = "/houseDataTown.action")
	@ResponseBody
	public Map<String, Object> agentLogList(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
		getParamsByMiniUi(pars);
		LfEmployee employee = new LfEmployee();
		try {
			employee = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pars.put("agentCode",employee.getId());
		pars.put("agentCity", employee.getCityId());
		
		return houseDAtaReport.getHouseDataTown(pars);
	}
	
	@RequestMapping(value = "/exportExcel.action")
    @ResponseBody
    public String exportExcel(HttpSession session,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){
       String arr[] = {"日期", "城市","行政区域", "板块", "有效房源总数(套)", "待审批下架房源数(套)", "实景数(套)", "视频数(套)",  "描述数(套)", "速销房(套)", "钥匙房(套)","无效房源数(套)"};
//       processMiniParam(pars);
       LfEmployee emp = getLoginEmployeeInfo(session);
//       pars.put("cityId",emp.getCityId());
       Map<String, Object> map = new HashMap<String, Object>();
       map =  houseDAtaReport.getHouseDataTown(pars);
       List<HouseCountTown> prList = new ArrayList<HouseCountTown>();
//       List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//       list.add(map);
       
       prList = (List<HouseCountTown>) map.get("data");
       List<ExcelHouseCountTown> excelList = new ArrayList<ExcelHouseCountTown>();
       ExcelHouseCountTown ec = null;
       if(prList != null && prList.size() > 0){
           for(HouseCountTown pr : prList){
//               String time = new SimpleDateFormat("yyyy-mm-dd").format(pr.getCreateTime());
               ec = new ExcelHouseCountTown();
               ec.setCreateTime(pars.get("queryTimeBegin").toString());
               ec.setCityName(pr.getCityName());
               ec.setAreaName(pr.getAreaName());
               ec.setTownName(pr.getTownName());
               ec.setSellcount(pr.getSellcount());
               ec.setAuditcount(pr.getAuditcount());
               ec.setPicturecount(pr.getPicturecount());
               ec.setVideocount(pr.getVideocount());
               ec.setSellPointcount(pr.getSellPointcount());
               ec.setCommcount(pr.getCommcount());
               ec.setKeycount(pr.getKeycount());
               ec.setHouseStatecount(pr.getHouseStatecount());
               excelList.add(ec);
               
           }
       }
       try{
           ExcelUtils.export(response, "房源数据报表-板块", arr, excelList, ExcelHouseCountTown.class);
           
        }catch(Exception e){
            e.printStackTrace();
        }
       return null;
    }
	

}
