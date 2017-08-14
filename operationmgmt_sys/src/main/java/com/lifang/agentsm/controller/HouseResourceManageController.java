package com.lifang.agentsm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.base.model.Response;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.entity.LfHouse;
import com.lifang.agentsm.exception.BizException;
import com.lifang.agentsm.model.ComboModel;
import com.lifang.agentsm.model.HouseContactSeeLog;
import com.lifang.agentsm.model.HouseEditRecord;
import com.lifang.agentsm.model.HouseInvalidReasonRecord;
import com.lifang.agentsm.model.HouseInvalidStatusReason;
import com.lifang.agentsm.model.HouseOperationLog;
import com.lifang.agentsm.model.LfAuditInvalid;
import com.lifang.agentsm.model.LfHouseFollowUp;
import com.lifang.agentsm.model.LfHouseFollowUpReq;
import com.lifang.agentsm.model.LfHouseOwnReq;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.model.SellHouseListModel;
import com.lifang.agentsm.model.SellHouseListRequest;
import com.lifang.agentsm.model.enums.ASMResponse;
import com.lifang.agentsm.service.DicAreaNewService;
import com.lifang.agentsm.service.HouseOperationLogService;
import com.lifang.agentsm.service.HouseResourceService;
import com.lifang.agentsm.service.LfAreaOrgService;
import com.lifang.agentsm.utils.EntityUtils.HouseStateEnum;
import com.lifang.housesoa.facade.service.HouseConcatService;
import com.lifang.housesoa.facade.service.HouseService;
import com.lifang.housesoa.model.HouseDetailInfo;
import com.lifang.housesoa.model.HouseInfoRequest;
import com.lifang.housesoa.model.HouseResourceConcact;
import com.lifang.housesoa.model.HouseSupporting;
import com.lifang.model.PageResponse;
import com.lifang.model.enums.AppEnum;

/**
 * 
 * @author ln
 * @desc:房源管理
 * @data 2015-5-28 16:17:33
 * 
 */
@Controller
@RequestMapping("/houseResource")
@Slf4j
public class HouseResourceManageController extends BaseController {

	@Autowired
	private HouseResourceService houseResourceService;
	@Autowired
	private HouseService houseSOAClient;
	
	@Autowired
	private DicAreaNewService dicAreaNewService;
	@Autowired
    private LfAreaOrgService lfAreaOrgService;
	@Autowired
	private HouseConcatService houseConcatService;
	@Autowired
	private HouseOperationLogService houseOperationLogService;
	
	
	
	/**
	 * 
	 * 功能描述:TODO(描述这个方法的作用)房源管理页面加载
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     ln:   2015年11月30日      新建
	 * </pre>
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sell/houseListPage")
	public String loadHousListPage(HttpServletRequest request,Model model){
	    LfEmployee lf;
	    Integer city_Id =0;
        try {
            lf = getLoginEmployeeInfo(request.getSession());
            city_Id = lf.getCityId();
            if(city_Id==1){
                city_Id=null;
            }
        }
        catch (Exception e) {
            log.error("【获取城市列表出错：】", e);
            e.printStackTrace();
        }
	    model.addAttribute("cityId",city_Id);
	    return "/house/sellHouseList";
	}
	/**
	 * 出售房源详情页面加载
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sell/houseDetailPage")
	public String loadPage(ModelMap model,@RequestParam("houseId") Integer houseId,@RequestParam("cityId") Integer cityId ) {
	    com.lifang.housesoa.model.Response<HouseDetailInfo> resInfo = houseSOAClient
                .getHouseDetailInfoByHouseId(houseId,AppEnum._lf_agentPC.getResource());
        HouseDetailInfo info = resInfo.getData();
		List<HouseInvalidStatusReason> reason = houseResourceService.getInvalidReasonByHouseId(houseId);
		HouseSupporting sup=info.getHouseSupporting();
		StringBuffer sb = new StringBuffer();
		if(sup!=null){
			if(sup.getHasBad()!=null&&sup.getHasBad()==1){
				sb.append(1).append(",");
			}
			if(sup.getHasSofa()!=null&&sup.getHasSofa()==1){
				sb.append(2).append(",");
			}
			if(sup.getHasDesk()!=null&&sup.getHasDesk()==1){
				sb.append(3).append(",");
			}
			if(sup.getHasTV()!=null&&sup.getHasTV()==1){
				sb.append(4).append(",");
			}
			if(sup.getHasInternet()!=null&&sup.getHasInternet()==1){
				sb.append(5).append(",");
			}
			if(sup.getHasAirConditioning()!=null&&sup.getHasAirConditioning()==1){
				sb.append(6).append(",");
			}
			if(sup.getHasWashMachine()!=null&&sup.getHasWashMachine()==1){
				sb.append(7).append(",");
			}
			if(sup.getHasWardrobe()!=null&&sup.getHasWardrobe()==1){
				sb.append(8).append(",");
			}
			if(sup.getHasCookerHood()!=null&&sup.getHasCookerHood()==1){
				sb.append(9).append(",");
			}
			if(sup.getHasWaterHeater()!=null&&sup.getHasWaterHeater()==1){
				sb.append(10);
			}
		}
		model.addAttribute("houseId", houseId);
		model.addAttribute("cityId", cityId);
		model.addAttribute("info", info);
		model.addAttribute("sup", sb.toString());
		model.addAttribute("reason",reason);
		return "house/sell_house_detail";
	}
	/**
	 * tab页面跳转
	 */
	@RequestMapping(value = "sell/tabPage")
    public String tabPage(ModelMap model,@RequestParam("houseId") Integer houseId,@RequestParam("cityId") Integer cityId ){
		model.addAttribute("houseId", houseId);
		model.addAttribute("cityId", cityId);
		return "house/house_agent_tab";
    }
	
	/**
	 * 业绩归属人列表
	 */
	@RequestMapping(value="sell/achievementOwnList")
	@ResponseBody
	public List<LfHouseOwnReq> achievementOwnList(LfHouseOwnReq req){
	    List<LfHouseOwnReq> list = houseResourceService.getAchievementOwnList(req);
		return list;
	}
	
	@RequestMapping(value="sell/houseContactData")
	@ResponseBody
	public List<HouseResourceConcact> houseContactData(
			@RequestParam("houseId") Integer houseId,int flag) {
	    /*List<HouseResourceConcact> list =  houseConcatService.getHostInfo4SellByHouseId(houseId);
	    for(HouseResourceConcact concact:list){
	        if(concact.getCompanyname()==null||concact.getCompanyname()==""){
	            concact.setCompanyname("━━");
	        }
	    }*/
		List<HouseResourceConcact> list  = houseConcatService.getAllHostInfoByHouseId(houseId);
		for(HouseResourceConcact hsc:list){
			hsc.setHostMobile("");
		}
		return list;
	}

	@RequestMapping(value="sell/getHouseContactDataByContactId")
	@ResponseBody
	public HouseResourceConcact houseContactDataByContactId(int contactId,HttpSession session,HttpServletRequest request){
		HouseResourceConcact houseResourceConcact = houseResourceService.getHouseContactDataByContactId(contactId);
		LfEmployee lf;
		HouseContactSeeLog seelog = new HouseContactSeeLog();
		try {
			lf = getLoginEmployeeInfo(session);
			seelog.setAgentId(lf.getId());
			seelog.setHouseId(houseResourceConcact.getHouseId());
			seelog.setHouseContactTel(houseResourceConcact.getHostMobile());
			seelog.setAgentName(lf.getName());
			seelog.setIp(getIpAddr(request));
		}
		catch (Exception e) {
			log.error("【获取登录人信息出错】:", e);
			e.printStackTrace();
		}
		houseResourceService.saveHouseContactLog(seelog);
		return houseResourceConcact;
	}
	/**
	 * 获取房源跟进列表
	 */
	@RequestMapping(value = "sell/getLfHouseFollowUpList")
	@ResponseBody
	public PageResponse getLfHouseFollowUpList(LfHouseFollowUpReq req) {
		return houseResourceService.getLfHouseFollowUpList(req);
	}
	/**
	 * 获取房源跟进列表(所有的)
	 */
	@RequestMapping(value = "sell/getLfHouseFollowUpListAll")
	@ResponseBody
	public PageResponse getLfHouseFollowUpListAll(HttpSession session, LfHouseFollowUpReq req) {
		
		         // 计算出当前员工的组织结构的code
		         LfEmployee emp;
                try {
                    emp = getLoginEmployeeInfo(session);
                    req.setCityId(emp.getCityId()+"");
//                    LfAreaOrg areaOrg = lfAreaOrgService.selectByEmployeeId(emp.getId());
                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//		         pars.put("agentCity", employee.getCityId());
		         String empOrgCode = null;//areaOrg.getCode();
		         
		         // 如果登陆员工的code长于参数的code那么用登陆员工code代替页面参数code
//		         if ((req.getOrgCode() == null && !"".equals(req.getOrgCode())) || empOrgCode.length() > req.getOrgCode().length())
//		         {
//		             req.setOrgCode(empOrgCode);
//		         }
		
		
		return houseResourceService.getLfHouseFollowUpListAll(req);
	}
	/**
	 * @return 获取房源跟进列表界面(所有的)
	 */
	@RequestMapping(value = "/getLfHouseFollowUpPageAll.action")
	public String getLfHouseFollowUpPageAll(ModelMap modelMap) {
		return "house/houseFollowUpListAll";
	}
	/**
	 * @return 获取房源跟进列表界面
	 */
	@RequestMapping(value = "sell/getLfHouseFollowUpPage")
	public String getLfHouseFollowUpPage(Integer houseId,ModelMap modelMap) {
		modelMap.addAttribute("houseId", houseId);
		return "house/houseFollowUpList";
	}
	/**
	 * 修改业绩归属人
	 */
	@RequestMapping(value="sell/editPage")
	public String editPage(@RequestParam("houseId") Integer houseId ,@RequestParam("storeId") Integer storeId,@RequestParam("flag") String flag ,int id,int franchiseeId,ModelMap model ){
		model.addAttribute("houseId", houseId);
		model.addAttribute("flag", flag);
		model.addAttribute("storeId", storeId);
		model.addAttribute("id", id);
		model.addAttribute("franchiseeId", franchiseeId);
		return "house/house_agent_edit";
	}
	
	@RequestMapping(value="sell/getStoreListByHouseId")
	@ResponseBody
	public List<ComboModel> getStoreListByHouseId(@RequestParam("houseId") Integer houseId,int franchiseeId,HttpServletRequest request  ){
	    LfEmployee lf;
        Integer city_Id =0;
        try {
            lf = getLoginEmployeeInfo(request.getSession());
            city_Id = lf.getCityId();
            if(city_Id==1){
                city_Id=null;
            }
        } catch (Exception e) {
            log.error("【获取城市列表出错：】", e);
            e.printStackTrace();
        }
		return houseResourceService.getStoreListByHouseId(houseId,city_Id,franchiseeId);
	}
	
	@RequestMapping(value="sell/getAgentListByStoreId")
	@ResponseBody
	public List<ComboModel> getAgentListByStoreId(@RequestParam("storeId") Integer storeId,@RequestParam("houseId") Integer houseId   ){
		return houseResourceService.getAgentListByStoreId(houseId,storeId);
	}
	
	@RequestMapping(value="sell/saveUpdateAgent")
	@ResponseBody
	public Response saveUpdateAgent(HttpSession session,@RequestParam("houseAgentId") Integer houseAgentId,@RequestParam("houseId") Integer houseId,@RequestParam("flag") String flag,int id){
		if(houseResourceService.saveUpdateAgent(houseAgentId,houseId,flag,id)){
		    HouseOperationLog operationLog = new HouseOperationLog();
            LfEmployee  lf = getLoginEmployeeInfo(session);
            operationLog.setOpType(1);
            operationLog.setMemo(lf.getName()+"修改经纪人：{类型:"+ flag+"},原来经纪人id:"+id+"，修改后经纪人Id:"+houseAgentId);
            operationLog.setHouseId(houseId);
            operationLog.setEmpId(lf.getId());
            houseOperationLogService.insertHouseOperationLog(operationLog);
			return new Response("success",1,null);
		}
		return null;
	}
	
	@RequestMapping(value="sell/deleteAgent")
	@ResponseBody
	public Response deleteAgent(@RequestParam("houseAgentId") Integer houseAgentId,@RequestParam("houseId") Integer houseId,@RequestParam("flag") String flag,int id){
		if(houseResourceService.deleteAgent(houseAgentId,houseId,flag,id)){
			return new Response("success",1,null);
		}
		return null;
	}

	/**
	 * @return 下载音频播放
	 */
	@RequestMapping(value = "sell/downLfHouseFollowUpVideo")
	public void downLfHouseFollowUpVideo(@RequestParam("id") Integer id,
			HttpServletResponse response, HttpServletRequest request) {
		LfHouseFollowUp followUp = houseResourceService.getLfHouseFollowUpDetail(id);
		String src = followUp.getNote();
		//获取单条记录
		OutputStream out = null;
		InputStream inputStream = null;
		response.setContentType("application/x-download");// 设置为下载application/x-download
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String date = sdf.format(d);
		
//		String filedisplay = (followUp.getStoreName() == null?"":(followUp.getStoreName()+"_"))+followUp.getRealName()+"_跟进录音" + followUp.getNote().substring(followUp.getNote().lastIndexOf("."));;
		String filedisplay = date + followUp.getNote().substring(followUp.getNote().lastIndexOf("."));
		try {
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filedisplay);
		URL url;
		try {
			//先通过链接获取流资源，然后输出下载
			out = response.getOutputStream();
			url = new URL(src);
			HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
			httpUrl.connect();
			inputStream = httpUrl.getInputStream();
			//输出
 			byte[] buffer = new byte[512];
 			while(inputStream.read(buffer)!=-1){
 				out.write(buffer);
 				out.flush();
 			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}finally{
			try {
				inputStream.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 功能描述:获取售房列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     CK:   2015年5月29日      新建
	 * </pre>
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("sell/list")
	@ResponseBody
	public PageResponse getSellHouseList(SellHouseListRequest request,HttpSession session){
	    LfEmployee lf=null;
        try {
            lf = getLoginEmployeeInfo(session);
        }
        catch (Exception e) {
            log.error("【获取登录人信息出错：】",e);
            e.printStackTrace();
        }
	    if(request.getCityId()==null){
	        request.setCityId(lf.getCityId());
	    }
	    if(request.getCityId()==1){
	        request.setCityId(null);
	    }
		return houseResourceService.getSellHouseList(request);
	}
	
	/**
	 * @param houseId 房源Id
	 * @param houseResourceStatus 有效状态
	 * @return 结果
	 * 设置房源有效状态
	 */
	@RequestMapping(value="sell/setHouseResourceStatus")
	@ResponseBody
	public com.lifang.model.Response setHouseResourceStatus(@RequestParam("reason") String reason,@RequestParam("memo") String memo,
			@RequestParam("houseId") Integer houseId,@RequestParam("houseResourceStatus")Integer houseResourceStatus,HttpSession session) {
		//房源状态为2/4时
		if(houseResourceStatus!=null && (houseResourceStatus == HouseStateEnum.SELL.getValue() || houseResourceStatus == HouseStateEnum.NEITHER.getValue())){
			//返回的Response对象不统一
			com.lifang.housesoa.model.Response response = houseSOAClient.houseDown(houseId, houseResourceStatus, null, null);
			LfEmployee  lf = getLoginEmployeeInfo(session);
			if(response.getStatus()==1){
	           // houseResourceService.updateHouseInfoByHouseId(houseId,0) ;
	            HouseInvalidReasonRecord hirr = new HouseInvalidReasonRecord();
	            hirr.setAgentId(lf.getId());
	            hirr.setAgentName(lf.getName());
	            hirr.setHouseId(houseId);
	            hirr.setReason(reason);
	            hirr.setMemo(memo);
	            hirr.setCityId(lf.getCityId());
	            houseResourceService.saveUnstatusReason(hirr);
	        }
//			//记录房源管理操作日志
//			if(response.getStatus() == 1){
//				HouseOperationLog operationLog = new HouseOperationLog();
//				LfEmployee object = (LfEmployee)session.getAttribute(Constants.LOGIN_SESSION);
//				operationLog.setHouseId(houseId);
//				operationLog.setEmpId(object.getId());
//				//有效日志
//				if(houseResourceStatus == HouseStateEnum.SELL.getValue()){
//					operationLog.setOpType(2);
//					operationLog.setMemo(object.getLoginName()+"设置房源"+houseId+"状态为：出售（有效）");
//				//设置为无效日志
//				}else if(houseResourceStatus == HouseStateEnum.NEITHER.getValue()){
//					operationLog.setOpType(1);
//					operationLog.setMemo(object.getLoginName()+"设置房源"+houseId+"状态为：既不租也不售（无效）");
//				}
//				houseOperationLogService.insertHouseOperationLog(operationLog);
//			}
			HouseOperationLog operationLog = new HouseOperationLog();
			operationLog.setOpType(1);
			operationLog.setMemo(lf.getName()+"设置房源"+houseId+"状态为：既不租也不售（无效）");
			operationLog.setHouseId(houseId);
			operationLog.setEmpId(lf.getId());
			houseOperationLogService.insertHouseOperationLog(operationLog);
			return new com.lifang.model.Response(response.getMessage(),response.getStatus(),response.getData());
		}
		//否则错误
		return ASMResponse.HouseStateError.value;
	}
	
	/**
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="sell/openToShow")
	public String openToShow(@RequestParam("id") Integer id,ModelMap modelMap){
		LfHouseFollowUp followUp = houseResourceService.getLfHouseFollowUpDetail(id);
		String type = "audio/"+followUp.getNote().substring(followUp.getNote().lastIndexOf(".")+1);
		modelMap.addAttribute("type", type);
		modelMap.addAttribute("src", followUp.getNote());
		return "house/openToShpw";
	}

    /**
     * 发布房源审核记录跳转 功能描述:TODO(描述这个方法的作用)
     */
    @RequestMapping(value = "sell/showHouseSellCheckRecordList")
    public String showHouseSellCheckRecordList(HttpServletRequest request) {
        Map<String,String[]> params = request.getParameterMap();
        for(Map.Entry<String,String[]> entry : params.entrySet()) {
            request.setAttribute(entry.getKey(),entry.getValue()[0]);
        }
        return "house/houseSellCheckRecordList";
    }

    /**
     * 功能描述:发布房源审核记录列表
     * @param params
     * @return
     */
    @RequestMapping(value = "sell/houseSellCheckRecordList")
    @ResponseBody
    public Object checkRecord(@RequestParam Map<String, Object> params,HttpServletRequest request) {
        if(params == null || params.size() == 0 || params.get("houseId") == null)
            throw new BizException("参数错误！");
        Map<String, Object> map = new HashMap<String, Object>();
        try{
            getParamsByMiniUi(params);
            map = houseResourceService.getHouseSellCheckRecordList(params);
        } catch (BizException e) {
            map.put("msg",e.getMessage());
        } finally {
            return map;
        }
    }
	/**
	 * 编辑房东信息页面
	 */
	@RequestMapping(value="sell/editContactPage")
	public String editContactPage(@RequestParam("contactId") String contactId ,@RequestParam("hostName") String hostName ,@RequestParam("hostMobile") String hostMobile ,ModelMap model ){
		model.addAttribute("contactId", contactId);
		model.addAttribute("hostName", hostName);
		model.addAttribute("hostMobile", hostMobile);
		return "house/house_contact_edit";
	}
	/**
	 * 房东信息添加
	 */
	@RequestMapping(value="sell/addContactPage")
	public String editContactPage(@RequestParam("houseId") String houseId ,@RequestParam("cityId") String cityId ,ModelMap model ){
		model.addAttribute("houseId", houseId);
		model.addAttribute("cityId", cityId);
		return "house/house_contact_add";
	}
	/**
	 * 房东信息删除
	 */
	@RequestMapping(value = "sell/deleteContact")
    @ResponseBody
	public Response deleteContact(@RequestParam("contactId") int contactId,HttpSession session){
		com.lifang.housesoa.model.Response result = houseConcatService.deleteHouseSellConcat(contactId);
		if(result.getStatus()==1){
		    LfEmployee  lf = getLoginEmployeeInfo(session);
            HouseOperationLog operationLog = new HouseOperationLog();
            operationLog.setOpType(1);
            operationLog.setMemo(lf.getName()+"删除房东联系人：{contactId:"+ contactId+"}");
            operationLog.setHouseId(contactId);
            operationLog.setEmpId(lf.getId());
            houseOperationLogService.insertHouseOperationLog(operationLog);
			return new Response("success",1,null);
		}else{
		    if(result.getMessage().equals("该房源只有一个联系人，不能删除")){
		        result.setMessage("该公司只有一个联系人，不能删除");
		    }
			return new Response(result.getMessage(),result.getStatus(),null);
		}
	}
	/**
	 * 房东信息增加
	 */
	@RequestMapping(value = "sell/addContact")
    @ResponseBody
	public Response addContact(@RequestParam("houseId") int houseId,@RequestParam("cityId") int cityId,@RequestParam("hostName") String hostName ,@RequestParam("hostMobile") String hostMobile
	        ,String jmsId,HttpSession session){
	    
	    MiniuiEntity entity= dicAreaNewService.getEnableCity(cityId);
	    HouseResourceConcact con = new HouseResourceConcact ();
		con.setHouseId(houseId);
		con.setHostMobile(hostMobile);
		con.setHostName(hostName);
		con.setHouseState(2);
		if(jmsId!=null && jmsId!=""){
		    con.setCompanyId(Integer.valueOf(jmsId));
		}else{
		    con.setCompanyId(0);
		}
		con.setProvince(entity.getText());
		con.setProvinceid(entity.getParentId());
		con.setCity(entity.getText());
		con.setCityid(entity.getId());
		com.lifang.housesoa.model.Response result = houseConcatService.addHouseSellConcat(con);
		if(result.getStatus()==1){
		    LfEmployee  lf = getLoginEmployeeInfo(session);
            HouseOperationLog operationLog = new HouseOperationLog();
            operationLog.setOpType(1);
            operationLog.setMemo(lf.getName()+"增加房东联系人：{houseId:"+ houseId+"}");
            operationLog.setHouseId(houseId);
            operationLog.setEmpId(lf.getId());
            houseOperationLogService.insertHouseOperationLog(operationLog);
			return new Response("success",1,null);
		}
		return new Response(result.getMessage(),result.getStatus(),null);
	}
	
	/**
	 * 房东信息修改
	 */
	@RequestMapping(value = "sell/updateContact")
    @ResponseBody
	public Response updateContact(HttpSession session,@RequestParam("hostMobile") String hostMobile,@RequestParam("contactId") int contactId,@RequestParam("hostName") String hostName){
		com.lifang.housesoa.model.Response result = houseConcatService.updateHouseSellConcatById( contactId,  hostName,  hostMobile);
		if(result.getStatus()==1){
		    LfEmployee  lf = getLoginEmployeeInfo(session);
            HouseOperationLog operationLog = new HouseOperationLog();
            operationLog.setOpType(1);
            operationLog.setMemo(lf.getName()+"修改房东联系人：{contactId:"+ contactId+"}");
            operationLog.setHouseId(contactId);
            operationLog.setEmpId(lf.getId());
            houseOperationLogService.insertHouseOperationLog(operationLog);
			return new Response("success", 1, null);
		}
		return null;
	}
	/**
	 * 房源信息修改
	 */
	@RequestMapping(value="sell/editHousePage")
	public String editHousePage(@RequestParam("houseId") Integer houseId ,ModelMap model ){
		List<Integer> list = new ArrayList<Integer>();
		list.add(houseId);
		com.lifang.housesoa.model.Response<HouseDetailInfo> resInfo = houseSOAClient
                .getHouseDetailInfoByHouseId(houseId,AppEnum._lf_agentPC.getResource());
		HouseDetailInfo info = resInfo.getData();
		HouseSupporting sup=info.getHouseSupporting();
		StringBuffer sb = new StringBuffer();
		if(sup!=null){
			if(sup.getHasBad()!=null&&sup.getHasBad()==1){
				sb.append(1).append(",");
			}
			if(sup.getHasSofa()!=null&&sup.getHasSofa()==1){
				sb.append(2).append(",");
			}
			if(sup.getHasDesk()!=null&&sup.getHasDesk()==1){
				sb.append(3).append(",");
			}
			if(sup.getHasTV()!=null&&sup.getHasTV()==1){
				sb.append(4).append(",");
			}
			if(sup.getHasInternet()!=null&&sup.getHasInternet()==1){
				sb.append(5).append(",");
			}
			if(sup.getHasAirConditioning()!=null&&sup.getHasAirConditioning()==1){
				sb.append(6).append(",");
			}
			if(sup.getHasWashMachine()!=null&&sup.getHasWashMachine()==1){
				sb.append(7).append(",");
			}
			if(sup.getHasWardrobe()!=null&&sup.getHasWardrobe()==1){
				sb.append(8).append(",");
			}
			if(sup.getHasCookerHood()!=null&&sup.getHasCookerHood()==1){
				sb.append(9).append(",");
			}
			if(sup.getHasWaterHeater()!=null&&sup.getHasWaterHeater()==1){
				sb.append(10);
			}
		}
		model.addAttribute("houseId", houseId);
		model.addAttribute("info", info);
		model.addAttribute("sup", sb.toString());
		return "house/house_edit";
	}
	
	/**
	 * 房源信息修改
	 */
	@RequestMapping(value = "sell/saveHouse")
    @ResponseBody
	public Response saveHouse(LfHouse house,HttpSession session){
		HouseInfoRequest  request = new HouseInfoRequest ();
		request.setCarSpace(house.getCarSpace());
		request.setCompleted(String.valueOf(house.getCompleted()));
		request.setFloor(house.getFloor());
		request.setHouseId(house.getHouseId());
		request.setHtype(house.getHtype());
		request.setIsHaveKey(house.getIsHaveKey());
		request.setOrientation(house.getOrientation());
		request.setProperty(house.getProperty());
		request.setRenovation(house.getRenovation());
		request.setSellPoint(house.getSellPoint());
		request.setSource(house.getSource());
		request.setSpaceArea(house.getSpaceArea());
		request.setSellPrice(house.getSellPrice());
		request.setGefuPrice(house.getGefuPrice());
		if(house.getHouseChildType()!=null){
			request.setHouseChildType(house.getHouseChildType());
		}
		if("true".equals(house.getIsFiveYears())){
			request.setIsFiveYears(1);
		}else{
			request.setIsFiveYears(0);
		}
		if("true".equals(house.getIsOnlyOne())){
			request.setIsOnlyOne(1);
		}else{
			request.setIsOnlyOne(0);
		}
		 HouseSupporting support = new HouseSupporting();
		 String hs = house.getHouseSupporting().replace("[", "").replace("]", "");
		 List list = new ArrayList();
		 if(hs.length()>0){
			 String[] s = hs.split(",");
			 for(int i=0;i<s.length;i++){
				 list.add(Integer.valueOf(s[i]));
			 }
		 }
			 if(list.contains(1)){
				 support.setHasBad((byte) 1);
			 }else{
				 support.setHasBad((byte) 0);
			 }
			 if(list.contains(2)){
				 support.setHasSofa((byte) 1);
			 }else{
				 support.setHasSofa((byte) 0);
			 }
			 if(list.contains(3)){
				 support.setHasDesk((byte) 1);
			 }else{
				 support.setHasDesk((byte) 0);
			 }
			 if(list.contains(4)){
				 support.setHasTV((byte) 1);
			 }else{
				 support.setHasTV((byte) 0);
			 }
			 if(list.contains(5)){
				 support.setHasInternet((byte) 1);
			 }else{
				 support.setHasInternet((byte)0);
			 }
			 if(list.contains(6)){
				 support.setHasAirConditioning((byte) 1);
			 }else{
				 support.setHasAirConditioning((byte) 0);
			 }
			 if(list.contains(7)){
				 support.setHasWashMachine((byte) 1);
			 }else{
				 support.setHasWashMachine((byte) 0);
			 }
			 if(list.contains(8)){
				 support.setHasWardrobe((byte) 1);
			 }else{
				 support.setHasWardrobe((byte) 0);
			 }
			 if(list.contains(9)){
				 support.setHasCookerHood((byte) 1);
			 }else{
				 support.setHasCookerHood((byte) 0);
			 }
			 if(list.contains(10)){
				 support.setHasWaterHeater((byte) 1);
			 }else{
				 support.setHasWaterHeater((byte) 0);
			 }
			 request.setHouseSupporting(support);
			 saveRecord(house,session);
			 com.lifang.housesoa.model.Response result = houseSOAClient.updateHouseInfoByHouseId(request);
		 if(result.getStatus()==1){
			 return new Response("success",1,null);
		 }
		return null;
	}
	
	public void saveRecord(LfHouse house,HttpSession session){
	    com.lifang.housesoa.model.Response<HouseDetailInfo> resInfo= houseSOAClient
                .getHouseDetailInfoByHouseId(house.getHouseId(),AppEnum._lf_agentPC.getResource());
	    HouseDetailInfo info = resInfo.getData();
        LfEmployee lfEmployee=null;
        try {
            lfEmployee = getLoginEmployeeInfo(session);
        }
        catch (Exception e) {
            log.error("【获取登录人信息出错：】", e);
            e.printStackTrace();
        }
        HouseEditRecord eRecord = new HouseEditRecord();
        eRecord.setCreateTime(new Date());
        eRecord.setHouseId(house.getHouseId());
        eRecord.setAgentId(lfEmployee.getId());
        //8.朝向 9.装修 10.楼层 11.卖点
        if(info.getOrientation().intValue() !=house.getOrientation().intValue()){
            eRecord.setBefore(info.getOrientation().toString());
            eRecord.setAfter(house.getOrientation().toString());
            eRecord.setEditType(8);
            eRecord.setOtherOperation(2);
            houseResourceService.saveHouseEditRecord(eRecord);
        }
        if(info.getRenovation()!=house.getRenovation()){
            eRecord.setBefore(String.valueOf(info.getRenovation()));
            eRecord.setAfter(house.getRenovation().toString());
            eRecord.setEditType(9);
            eRecord.setOtherOperation(2);
            houseResourceService.saveHouseEditRecord(eRecord);
        }
        
        if(info.getFloor()!=house.getFloor()){
            eRecord.setBefore(String.valueOf(info.getFloor()));
            eRecord.setAfter(house.getFloor().toString());
            eRecord.setEditType(10);
            eRecord.setOtherOperation(2);
            houseResourceService.saveHouseEditRecord(eRecord);
        }
        if(info.getSellPoint()!=null&&!info.getSellPoint().equals(house.getSellPoint())){
            eRecord.setBefore(String.valueOf(info.getSellPoint()));
            eRecord.setAfter(house.getSellPoint().toString());
            eRecord.setEditType(11);
            eRecord.setOtherOperation(2);
            houseResourceService.saveHouseEditRecord(eRecord);
        }
	}
	/**
	 * 往业绩归属人表中默认增加一条记录
	 * @param houseId
	 * @return
	 */
	@RequestMapping(value = "sell/addOwn")
    @ResponseBody
	public Response updateContact(@RequestParam("houseId") Integer houseId){
		return houseResourceService.addOwn(houseId);
	}
	
	/**
	 * 
	 * 功能描述:保存房东电话查看记录
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     ln:   2015年11月19日      新建
	 * </pre>
	 *
	 * @param houseId
	 * @param hostMobile
	 * @return
	 */
	@RequestMapping(value = "sell/saveHouseContactLog")
    @ResponseBody
	public Response saveHouseContactLog(HttpServletRequest request,HttpSession session ,@RequestParam("houseId") Integer houseId,@RequestParam("hostMobile") String hostMobile){
	    LfEmployee lf;
        HouseContactSeeLog seelog = new HouseContactSeeLog();
        try {
            lf = getLoginEmployeeInfo(session);
            seelog.setAgentId(lf.getId());
            seelog.setHouseId(houseId);
            seelog.setHouseContactTel(hostMobile);
            seelog.setAgentName(lf.getName());
            seelog.setIp(getIpAddr(request));
        }
        catch (Exception e) {
            log.error("【获取登录人信息出错】:", e);
            e.printStackTrace();
        }
	    return houseResourceService.saveHouseContactLog(seelog);
	}
	/**
	 * 
	 * 功能描述:ip地址解析
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     ln:   2015年11月23日      新建
	 * </pre>
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "本地";
        }
        return ip;
    }
	/**
	 * 
	 * 功能描述:TODO(描述这个方法的作用)获取加盟商列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     LN:   2015年12月3日      新建
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping(value = "sell/getFranchiseeList")
    @ResponseBody
	public List<ComboModel> getFranchiseeList(int houseId){
	    return houseResourceService.getFranchiseeList(houseId);
	}
	/**
	 * 
	 * 功能描述:TODO(描述这个方法的作用)根据城市查询加盟商
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     ln:   2015年12月24日      新建
	 * </pre>
	 *
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value = "sell/getFranchiseeListByCityId")
    @ResponseBody
    public List<ComboModel> getFranchiseeListByCityId(int cityId){
	    List<ComboModel> list =  houseResourceService.getFranchiseeListByCityId(cityId);
	    ComboModel cm = new ComboModel();
	    cm.setId(1);
	    cm.setText("志远");
	    list.add(0, cm);
        return list;
    }
	/**
	 * 
	 * 功能描述:房源申请无效原因展示
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     ln:   2016年1月7日      新建
	 * </pre>
	 *
	 * @param houseId
	 * @return
	 */
	@RequestMapping(value="sell/lookReasonPage")
	public String lookReasonPage(int houseId,ModelMap model){
	    model.addAttribute("houseId", houseId);
	    return "house/houseValid";
	}
	/**
	 * 
	 * 功能描述:获取申请无效原因列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     ln:   2016年1月7日      新建
	 * </pre>
	 *
	 * @param houseId
	 * @return
	 */
	@RequestMapping(value = "sell/houseValidReasonList")
    @ResponseBody
	public List<LfAuditInvalid> houseValidReasonList(int houseId){
	    return houseResourceService.houseValidReasonList(houseId);
	}
	
	/**
	 * 
	 * 功能描述:房源置为无效原因
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     ln:   2016年1月8日      新建
	 * </pre>
	 *
	 * @param houseId
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value="sell/setHouseResourceStatusPage")
	public String setHouseResourceStatusPage(int houseId,int status,ModelMap model){
	    model.addAttribute("houseId", houseId);
	    model.addAttribute("status", status);
        return "house/houseResourceStatus";
	}
	/**
	 * 
	 * 功能描述:获取原因列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     ln:   2016年1月8日      新建
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping(value = "sell/getReasionList")
    @ResponseBody
	public List<MiniuiEntity>  getReasionList(){
	    return houseResourceService.getReasionList();
	}


	/**
	 * 功能描述:房源跟进记录屏蔽
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     luogq:   2015年12月30日      新建
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @param pars
	 * @return
	 */
	@RequestMapping(value = "/openShield.action")
	public String openShield(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
		String result = "";

		try{
			houseResourceService.updateShield(pars);
			result = "1";
		}catch (Exception e){
			result = "0";
			e.printStackTrace();
		}
		try {
			response.getWriter().write(result);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	//无效原因页面
	@RequestMapping(value="/invalidReasionPage")
	public String invalidReasionPage(){
	   return  "house/invalid_reason_list";
	}
	
	//无效原因数据加载
	@RequestMapping(value = "/invalidReasonList")
    @ResponseBody
	public  PageResponse invalidReasonList(HouseInvalidReasonRecord hir,HttpSession session){
	    LfEmployee lf=null;
        try {
            lf = getLoginEmployeeInfo(session);
            if(hir.getCityId()==null){
                hir.setCityId(lf.getCityId());
            }
            if(hir.getCityId()==1){
                hir.setCityId(null);
            }
        }
        catch (Exception e) {
            log.error("【获取登录人信息出错：】",e);
            e.printStackTrace();
        }
	   return houseResourceService.invalidReasonList(hir);
	}
	//查看发布人页面sell/lookPublisherPage.action
	@RequestMapping("sell/lookPublisherPage")
	public String lookPublisherPage(int houseId,ModelMap model){
	    model.addAttribute("houseId",houseId);
	    return "house/look_house_publisher";
	}
	
	//获取发布人页面数据加载
	@RequestMapping("sell/getPublisherList")
	@ResponseBody
	public List<SellHouseListModel> getPublisherList(int houseId){
	    return houseResourceService.getPublisherList(houseId);
	}

	//获取发布人页面数据加载
	@RequestMapping("sell/getKeyList")
	@ResponseBody
	public List<SellHouseListModel> getKeyList(int houseId){
		return houseResourceService.getKeyList(houseId);
	}

	//获取发布人页面数据加载
	@RequestMapping("sell/getCommonList")
	@ResponseBody
	public List<SellHouseListModel> getCommonList(int houseId){
		return houseResourceService.getCommonList(houseId);
	}

	//获取发布人页面数据加载
	@RequestMapping("sell/getPictureList")
	@ResponseBody
	public List<SellHouseListModel> getPictureList(int houseId){
		return houseResourceService.getPictureList(houseId);
	}
}
