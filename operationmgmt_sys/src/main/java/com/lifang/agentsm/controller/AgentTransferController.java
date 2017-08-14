package com.lifang.agentsm.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.FranchiseeDic;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.LfFranchiseeInfo;
import com.lifang.agentsm.model.PublicReport;
import com.lifang.agentsm.model.TransferStore;
import com.lifang.agentsm.service.TransferService;
import com.lifang.agentsm.utils.ExcelUtils;
import com.lifang.hr.exception.BusinessException;
import com.lifang.hr.facade.EmployeeService;
import com.lifang.model.Response;

@Controller
@RequestMapping("/transferMng")
public class AgentTransferController extends BaseController {
	@Autowired
	private TransferService transferService;
	@Autowired
    EmployeeService employeeService;

    /**
     * 功能描述: // 跳转到经纪人资源转移页面
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/gotoTransferMng.action")
    public String gotoEmployeeList(HttpServletRequest request, HttpServletResponse response) {
        return "transfer/agentTransfer";
    }
//	
    /**
     * 功能描述:加载需要转移的资源
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月3日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/getTransferList.action")
  	@ResponseBody
  	public Map<String, Object> getTransferList(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){
    	processMiniParam(pars);
    	
//		LfEmployee employee = getLoginEmployeeInfo(request.getSession());
		
//		pars.put("agentCode",employee.getId());
		
//  		return houseImageService.getImageAuditList(pars);
        //计算页面传来的参数code
        if(pars.get("parentId") != null && pars.get("parentId") !="" && "null".equals(pars.get("parentId")))
        {
//            pars.put("parentId",pars.get("parentId"));
            
            pars.put("parentIdIn",pars.get("parentId"));
        }
        if(pars.get("storeId") != null && pars.get("storeId") !="" && "null".equals(pars.get("storeId")))
        {
//            pars.put("parentId", pars.get("storeId"));
            pars.put("parentIdIn",pars.get("parentId"));
            
        }


    	return transferService.findTransferList(pars);
  	}
    /**
     * 功能描述:暂时不用
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/getCustomerList.action")
  	@ResponseBody
  	public Map<String, Object> getCustomerList(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){
    	processMiniParam(pars);
    	
    	return transferService.findCustomerList(pars);
  	}

    
    /**
     * 功能描述:// 跳转到经纪人资源转移页面
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/gotoFranchisee.action")
    public String gotoFranchisee(HttpServletRequest request, HttpServletResponse response) {
        return "transfer/franchiseeInfo";
    }
    //	加载需要加盟商列表
    /**
     * 功能描述:加载需要加盟商列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月9日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/getFranchiseeList.action")
    @ResponseBody
    public Map<String, Object> getFranchiseeList(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){
        processMiniParam(pars);

//		LfEmployee employee = getLoginEmployeeInfo(request.getSession());

//		pars.put("agentCode",employee.getId());

//  		return houseImageService.getImageAuditList(pars);
        //cityId: cityId, corporate: corporate, corporatephone: corporatephone, repetitionimage: repetitionimage
        LfEmployee employee= new LfEmployee();
		try {
			employee = getLoginEmployeeInfo(request.getSession());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        pars.put("agentCode",employee.getId());
        
        if (pars.get("cityId") == null || "".equals(pars.get("cityId"))) {

            //上面返回的是cityId 我们需要的是id
            pars.put("cityId",employee.getCityId());
            String id = transferService.findIdByCityId(pars);
            pars.put("cityId", id);

        }
        
        String repetitionimage = pars.get("repetitionimage")+"";
        if("false".equals(repetitionimage)){
        	repetitionimage = "0";
        }else{
        	repetitionimage = "1";
        }
        pars.put("repetitionimage",repetitionimage);
        return transferService.findFranchiseeList(pars);
    }
    

    /**
     * 功能描述: // 跳转到添加加盟商
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addFranchisee.action")
    public String addFranchisee(HttpServletRequest request, HttpServletResponse response) {
        return "transfer/addFranchisee";
    }

    

    /**
     * 功能描述://保存加盟商
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/saveFranchiseeInfo.action")
    @ResponseBody
    public Object saveFranchiseeInfo(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){

		LfEmployee employee= new LfEmployee();
		try {
			employee = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

//		pars.put("agentCode",employee.getId());

//  		return houseImageService.getImageAuditList(pars);
		
		pars.put("createBy", employee.getId());
        String result = "";
        String isseed = pars.get("isseed")+"";
        if("false".equals(isseed)){
        	isseed = "0";
        }else{
        	isseed = "1";
        }
        pars.put("isseed",isseed);
        try {

            result = transferService.saveFranchiseeInfo(pars);
        }catch (BusinessException ex){
            result = ex.getMessage();

            try {
//                result = new String(result.getBytes(), "UTF-8");

                result=  URLEncoder.encode(result, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

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



    //重置权限
    @RequestMapping(value = "/resetLimit.action")
    @ResponseBody
    public Response resetLimit(String franchiseePhone,HttpSession session){
        LfEmployee employee=null;
        Response res = null;
        try {
            employee = getLoginEmployeeInfo(session);
             res = employeeService.resetWPHRAccountRight(franchiseePhone, employee.getId());
        } catch (Exception e1) {
            logger.error("获取登录人出错",e1);
        }
        return res;
        
    }
    /**
     * 功能描述://保存加盟商
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/resetPwd.action")
    @ResponseBody
    public Object resetPwd(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){
        LfEmployee employee= new LfEmployee();
        try {
            employee = getLoginEmployeeInfo(request.getSession());
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        String result = "1";

        try{
            employeeService.resetPassword(pars.get("franchiseePhone")+"",employee.getId());
           // result = transferService.saveFranchiseeInfo(pars);

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
    
    /**
     * 功能描述://修改加盟商
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/editFranchiseeInfo.action")
    @ResponseBody
    public Object editFranchiseeInfo(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){

        LfEmployee employee= new LfEmployee();
        try {
            employee = getLoginEmployeeInfo(request.getSession());
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        pars.put("createBy", employee.getId());

        String result = "";
        String isseed = pars.get("isseed")+"";
        if("false".equals(isseed)){
        	isseed = "0";
        }else{
        	isseed = "1";
        }
        pars.put("isseed",isseed);
        try{

            result = transferService.updateFranchiseeInfo(pars);

            result = "1";
        }catch (BusinessException ex){
            result = ex.getMessage();

            try {
//                result = new String(result.getBytes(), "UTF-8");

                result=  URLEncoder.encode(result, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    
    /**
     * 功能描述://查看加盟商
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/showFranchisee.action")
    public Object showFranchisee(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
    	

		List<LfFranchiseeInfo> lfiList = transferService.findFranchiseeById(pars);

    	request.setAttribute("laaList", lfiList);
    	
        return "transfer/showFranchisee";
    }



    /**
     * 功能描述:加载可选区域
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/getAreaByCityId.action")
    @ResponseBody
    public List<FranchiseeDic> getAreaByCityId(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){
//        processMiniParam(pars);


        return transferService.getAreaByCityId(pars);
    }
    /**
     * 跳转到合作伙伴选择区域界面
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/openAdd.action")
    public Object openAdd(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
        request.setAttribute("cityId",pars.get("cityId"));
        request.setAttribute("isupdate", pars.get("isupdate"));
        if (pars.get("areaId") != null && !"".equals(pars.get("areaId"))) {
        	request.setAttribute("areaId",pars.get("areaId"));	
		}
        
        return "transfer/add";
    }
    
    
    /**
     * 功能描述:加载已选区域
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/getData2.action")
    @ResponseBody
    public List<FranchiseeDic> getData2(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){
//        processMiniParam(pars);

    	String cityId = pars.get("cityId")+"";
    	if (cityId == null || "".equals(cityId)) {
    		return new ArrayList<FranchiseeDic>();
		}
    	String[] cityIds = cityId.split("\\|",-1);
    	List<String> list = new ArrayList<String>();
    	for(String s : cityIds){
    		list.add(s);
    	}
    	if (list.size() >= 1) {
			
    		return transferService.getData2AreaByAreaId(list);
		}else{
			return new ArrayList<FranchiseeDic>();
		}
    }

    
    /**
     * 功能描述://编辑加盟商
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/editFranchisee.action")
    public Object editFranchisee(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
    	

		List<LfFranchiseeInfo> lfiList = transferService.findFranchiseeById(pars);

    	request.setAttribute("laaList", lfiList);
    	
        return "transfer/editFranchisee";
    }
    

    /**
     * 功能描述:    //打开加盟商门店
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/showFranchiseeStore.action")
    public String showFranchiseeStore(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
    	
    	request.setAttribute("franchiseeId", pars.get("code"));
    	
        return "transfer/franchiseeStore";
    }
    
    /**
     * 功能描述:查询加盟商对应的门店
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月3日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/getFranchiseeStoreList.action")
    @ResponseBody
    public Map<String, Object> getFranchiseeStoreList(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){
        processMiniParam(pars);

        
        return transferService.findFranchiseeStoreList(pars);
    }
    
    
    /**
     * 功能描述://编辑加盟商门店
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/editFranchiseeStore.action")
    public String editFranchiseeStore(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
    	
    	request.setAttribute("franchiseeId", pars.get("franchiseeId"));

    	request.setAttribute("storeId", pars.get("storeId"));
    	request.setAttribute("areaId", pars.get("areaId"));

    	request.setAttribute("id", pars.get("id"));
        if (!"0".equals(pars.get("id"))){

            List<TransferStore> nameList = transferService.findNameById(pars);
            if(nameList != null && nameList.size() > 0){
                request.setAttribute("chartername", nameList.get(0).getChartername());
                request.setAttribute("storeaddress",nameList.get(0).getStoreaddress());

            }

        }
        String name = transferService.findAreaOrg(pars);
        request.setAttribute("name", name);
    	
        return "transfer/addFranchiseeStore";
    }
    
    /**
     * 功能描述://添加加盟商门店
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/saveFranchiseeStore.action")
    public String saveFranchiseeStore(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
    	String result = "";
        try{
        	
            transferService.saveFranchiseeStore(pars);
            if (!"0".equals(pars.get("id"))) {
                request.setAttribute("id", pars.get("id"));
            }
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
    //打开转入界面
    
    
    /**
     * 功能描述://打开加盟商门店
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/openTransfer.action")
    public String openTransfer(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
    	
    	request.setAttribute("cityIdIn", pars.get("cityId"));
    	request.setAttribute("areaIdIn", pars.get("areaId"));
    	request.setAttribute("storeIdIn", pars.get("storeId"));
    	
        return "transfer/openTransfer";
    }
    
   
    
    /**
     * 功能描述: //资源转移
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2015年12月4日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/saveResouce.action")
    public String saveResouce(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
    	String result = "";
    	LfEmployee employee= new LfEmployee();
		try {
			employee = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try{
	    	//计算页面传来的参数code 转出
	        if(pars.get("areaIdIn") != null && pars.get("areaIdIn") !="" && !"null".equals(pars.get("areaIdIn")))
	        {
	            pars.put("parentIdOut",pars.get("areaIdIn"));
	        }
	        if(pars.get("storeIdIn") != null && pars.get("storeIdIn") !="" && !"null".equals(pars.get("storeIdIn")))
	        {
	            pars.put("parentIdOut", pars.get("storeIdIn"));
	        }
        //计算页面传来的参数code 转入
          if(pars.get("areaId") != null && pars.get("areaId") !="" && !"null".equals(pars.get("areaId")))
          {
              pars.put("parentIdIn",pars.get("areaId"));
          }
          if(pars.get("storeId") != null && pars.get("storeId") !="" && !"null".equals(pars.get("storeId")))
          {
              pars.put("parentIdIn", pars.get("storeId"));
          }
          pars.put("createBy",employee.getId());
        	
          result = transferService.transferResouce(pars);

//            result = "1";
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

    /**
     * 功能描述:端口同步
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
    @RequestMapping(value = "/openPush.action")
    public String openPush(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
        String result = "";

        try{
             transferService.updatePush(pars);
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

    /**
     * 功能描述:加盟商终止合作
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
    @RequestMapping(value = "/onStops.action")
    public String onStops(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
        String result = "";
        LfEmployee employee= new LfEmployee();
        try {
            employee = getLoginEmployeeInfo(request.getSession());
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try{

            boolean b = transferService.transferResouce(pars.get("id")+"");
            if (!b){
                employeeService.stopWPByfranchiseeId(Integer.parseInt(pars.get("id")+""),employee.getId());
                result = "1";
            }else{
                result = "2";
            }


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
    
    /**
     * 功能描述:打开公客报表页面
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     luogq:   2016年1月11日      新建
     * </pre>
     *
     * @param request
     * @param response
     * @param pars
     * @return
     */
    @RequestMapping(value = "/openPubReport.action")
    public String openPubReport(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
        
        return "transfer/pubReport";
    }
    
    
    @RequestMapping(value = "/getPubReportList.action")
    @ResponseBody
    public Map<String, Object> getPubReportList(HttpSession session,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){
        processMiniParam(pars);
        LfEmployee emp;
            emp = getLoginEmployeeInfo(session);
            pars.put("cityId",emp.getCityId());
        
        return transferService.getPubReportList(pars);
    }
    
    @RequestMapping(value = "/exportExcel.action")
    @ResponseBody
    public String exportExcel(HttpSession session,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> pars){
       String arr[] = {"日期", "城市","区域", "门店", "经纪人", "区公客数", "店公客数", "区公客认领数",  "区公客查看手机数", "店公客认领数", "店公客查看手机数"};
//       processMiniParam(pars);
       LfEmployee emp = getLoginEmployeeInfo(session);
       pars.put("cityId",emp.getCityId());
       Map<String, Object> map = new HashMap<String, Object>();
       map =  transferService.getPubReportList(pars);
       List<PublicReport> prList = new ArrayList<PublicReport>();
//       List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//       list.add(map);
       
       prList = (List<PublicReport>) map.get("data");
       for(PublicReport pr : prList){
           pr.setAgentId("");
           
       }
       
       try{
           ExcelUtils.export(response, "公客报表", arr, prList, PublicReport.class);
           
        }catch(Exception e){
            e.printStackTrace();
        }
       return null;
    }
   
    //恢复合作
    @RequestMapping(value = "/resumOperator")
    @ResponseBody
    public com.lifang.model.Response resumOperator(int id,HttpSession session){
        // to do 此处调人事系统接口
        LfEmployee employee= new LfEmployee();
        try {
            employee = getLoginEmployeeInfo(session);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return employeeService.recoveryWPByfranchiseeId(id, employee.getId());
    }
}

