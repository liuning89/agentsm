package com.lifang.agentsm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lifang.bzsm.console.model.AgentRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.leo.common.util.MD5Digest;
import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfAgent;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.entity.ResourceTransferRequest;
import com.lifang.agentsm.exception.BizException;
import com.lifang.agentsm.model.MiniuiEntity;
import com.lifang.agentsm.service.LfAgentService;
import com.lifang.agentsm.service.LfSellHouseAgentService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.bzsm.console.entity.LfAreaOrg;
import com.lifang.imgsoa.facade.ImageService;
import com.lifang.imgsoa.model.ImageKeyObject;
//import com.easemob.server.example.comm.Constants;
//import com.easemob.server.example.httpclient.apidemo.EasemobIMUsers;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.JsonNodeFactory;
//import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping("/agent")
public class AgentController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(AgentController.class);
	
	@Autowired
	private LfAgentService lfAgentService;

	@Autowired
	private LfSellHouseAgentService lfSellHouseAgentService;

    @Autowired
    private ImageService imgSOAClient;

    @Autowired
    private AgentSOAServer agentSOAServer;
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
	@RequestMapping(value = "/agentListIndex.action")
	public String agentListIndex() {
		return "agent/agentList";
	}
	
	@RequestMapping(value = "/agentListMiniIndex.action")
    public String agentListMiniIndex() {
        return "agent/agentMiniList";
    }

	/**
	 * 功能描述:获取经纪人列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     fanxin:   2015年4月21日      新建
	 * </pre>
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentListData.action")
	@ResponseBody
	public Object agentListData(HttpServletRequest request, AgentRequest req) {

		//计算出当前员工的组织结构的code
		LfEmployee emp = getLoginEmployeeInfo(request.getSession());

		return lfAgentService.getAgentList(req, emp);
	}
	
	
	 @RequestMapping(value = "/getAgentByAreaOrg.action", method = {RequestMethod.GET, RequestMethod.POST})
	    @ResponseBody
	    public List<Agent> getAgentByAreaOrg(LfAreaOrg areaOrg) {
	        return agentSOAServer.getAgentList(null, areaOrg.getAreaId(), areaOrg.getStoreId(),
	                null, null);
	    }
	/***
	 * 
	* @Title: getAgentById 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param request
	* @param @param response
	* @param @param id
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	
	@RequestMapping(value = "getAgentById.action")
	@ResponseBody
	public Object getAgentById(HttpServletRequest request,HttpServletResponse response,Integer id)
	{
	    return lfAgentService.getAgentById(id);
	}
	
	/**
	 * 
	* @Title: gotoAddAgent 
	* @Description: 跳转到添加经纪人
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/gotoAddAgent.action")
	public String gotoAddAgent()
	{
	    return "agent/agentAddwindow";
	}
	
	/***
	 * 
	* @Title: gotoEditAgent 
	* @Description: 跳转到编辑经纪人
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/gotoEditAgent.action")
	public String gotoEditAgent()
	{
	    return "agent/agentEditwindow";
	}
	
	/**
	 * 
	* @Title: gotoUpdatePasswordwindow 
	* @Description: 跳转到修改密码页面 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/gotoUpdatePassword.action")
	public String gotoUpdatePasswordwindow()
	{
	    return "agent/updatePasswordwindow";
	}
	
	/**
	 * 
	* @Title: gotoResourceTranswindnow 
	* @Description: 跳转到资源转移页面
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/gotoResourceTrans")
	public String gotoResourceTranswindnow()
	{
	    return "agent/resourceTranswindow";
	}

	/**
	 * 功能描述:新增经纪人
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月7日      新建
	 * </pre>
	 *
	 * @param agent
	 */
	@RequestMapping(value = "/addAgent.action",produces={"application/json;charset=UTF-8"})	
	@ResponseBody
	public Object addAgent(@RequestParam CommonsMultipartFile imgFile,LfAgent agent){
		int result = 0;
		try{
			result = lfAgentService.insertAgent(agent,imgFile.getBytes());
            //为新增的经纪人注册环信账号（暂时不用）
            /*
            ObjectNode datanode = JsonNodeFactory.instance.objectNode();
            datanode.put("username",agent.getId());
            datanode.put("password", Constants.DEFAULT_PASSWORD);
            ObjectNode objectNode = EasemobIMUsers.createNewIMUserSingle(datanode);
            if(objectNode == null || !"200".equals(String.valueOf(objectNode.get("statusCode")))){
                return "为经纪人注册环信账号失败";
            }
            */
		}catch (DuplicateKeyException e){
			logger.info("手机号或者用户名已存在,mobile={},realName={}",agent.getMobile(),agent.getRealName());
			return "手机号或者用户名已存在";
		} catch (Exception e){
            logger.info("====>url={},error={}","/addAgent.action",e.getMessage());
            return "未知错误，请联系管理员";
        }
		return result;
	}
	
	/**
	 * 功能描述:更新经纪人信息
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月7日      新建
	 * </pre>
	 *
	 * @param agent
	 * @param
	 * @param
	 */
	@RequestMapping(value = "/updateAgent.action",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public Object updateAgent(@RequestParam CommonsMultipartFile imgFile,LfAgent agent){
		int result = 0;
		try{
			result = lfAgentService.updateAgent(agent,imgFile.getBytes());
		}catch(DuplicateKeyException e){
			logger.info("手机号或者用户名已存在,mobile={},realName={}",agent.getMobile(),agent.getRealName());
			return "手机号或者用户名已存在";
		}
		return result;
	}

	@RequestMapping(value = "/updatePassword.action",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public Object updatePassword(String id, String newPassword,String confirmPassword){
		if(StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(confirmPassword)){
			return "新密码不能为空";
		}
		if(!newPassword.equals(confirmPassword)){
			return "两次输入的密码不相同";
		}
		LfAgent agent = new LfAgent();
		agent.setId(Long.parseLong(id));
		agent.setPassword(MD5Digest.getMD5Digest(newPassword));
		return lfAgentService.updateAgent(agent);
	}
	
	public void deleteAgentById(Integer id,HttpServletRequest request, HttpServletResponse response){
		int result = lfAgentService.deleteAgentById(id);
		responseWrite(result, request, response);
	}
	@RequestMapping(value = "/getAgentListByPars.action",method = {RequestMethod.GET,RequestMethod.POST},produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public List<LfAgent> getAgentListByPars(HttpServletRequest request, HttpServletResponse response,int storeId){
		return lfAgentService.getAgentListByParms(storeId);
	}
	
	@RequestMapping(value = "/resourceTransfer.action",method = {RequestMethod.GET,RequestMethod.POST},produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public Object resourceTransfer(ResourceTransferRequest resourceTransferRequest){
		try{
			lfSellHouseAgentService.transferResource(resourceTransferRequest);
		}catch (Exception e) {
			e.printStackTrace();
			return "更新失败";
		}
		return 1;
	}

	
	@RequestMapping(value = "/isResourceTransfer.action",method = {RequestMethod.GET,RequestMethod.POST},produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public Object isResourceTransfer(Integer userId){
		return lfSellHouseAgentService.isResourceTransfer(userId);
	}
	


    /**
     * 最近带看跳转 功能描述:TODO(描述这个方法的作用)
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     maqi:   2015年5月28日      新建
     * </pre>
     * @return
     */
    @RequestMapping(value = "/agentRecentlySeeList.action")
    public String agentRecentlySeeList(HttpServletRequest request, @RequestParam Map<String, Object> pars) {
        //判断是否列表请求，页面上做不同显示
        pars.put("isList",1);
        if(pars.get("houseId") != null){
            pars.put("isList",0);
        }
        for(Map.Entry<String,Object> entry : pars.entrySet()) {
            request.setAttribute(entry.getKey(),entry.getValue());
        }
        return "agent/agentRecentlySeeList";
    }

    /**
     * 功能描述:最近带看列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     fanxin:   2015年4月21日      新建
     * </pre>
     *
     * @param pars
     * @return
     */
    @RequestMapping(value = "/agentRecentlySeeListData.action")
    @ResponseBody
    public Object agentRecentlySeeListData(@RequestParam Map<String, Object> pars, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        LfEmployee emp = getLoginEmployeeInfo(request.getSession());
        try{
            getParamsByMiniUi(pars);

            LfEmployee employee= new LfEmployee();
            try {
                employee = getLoginEmployeeInfo(request.getSession());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            pars.put("agentCode",employee.getId());
            pars.put("agentCity", employee.getCityId());
            
        
            map = lfAgentService.getAgentRecentlySeeList(pars, emp);

        } catch (BizException e) {
            map.put("msg",e.getMessage());
        } finally {
            return map;
        }
    }

    /**
     * 根据key返回图片地址 功能描述:TODO(描述这个方法的作用)
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     maqi:   2015年5月28日      新建
     * </pre>
     * @return
     */
    @RequestMapping(value = "/getImageKeyObject.action")
    public void getImageKeyObject(HttpServletRequest request, HttpServletResponse response, @RequestParam String key) throws IOException {
        if(StringUtils.isEmpty(key))
            throw new BizException("参数错误！");
        ImageKeyObject obj = imgSOAClient.getImageKeyObject(key);
        Writer out = response.getWriter();
        responseWrite(obj, request, response);
    }

    /**
     * 根据 key 下载图片
     * @return
     */
    @RequestMapping(value = "/downloadImageKeyObject.action")
    public void downloadImageKeyObject(HttpServletRequest request, HttpServletResponse response, @RequestParam String key) throws IOException {
        if(StringUtils.isEmpty(key))
            throw new BizException("参数错误！");
        ImageKeyObject obj = imgSOAClient.getImageKeyObject(key);
        URL url = new URL(obj.getOriginal());
        response.setContentType("application/x-download");
        OutputStream out = null;
        InputStream in = null;
        try{
            out = response.getOutputStream();
            in = url.openStream();
            byte[] b = new byte[1024];
            int i = 0;
            while((i = in.read(b)) > 0){
                out.write(b, 0, i);
            }
            out.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
        }
    }
    
    /**
     * 功能描述:获取门店下的经纪人的简单信息列表
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     CK:   2015年5月30日      新建
     * </pre>
     *
     * @param storeId
     * @return
     */
    @RequestMapping("simple/list")
    @ResponseBody
    public List<MiniuiEntity> getAgentSimleListByStoreId(Integer storeId){
    	return lfAgentService.getAgentSimpleListByStoreId(storeId);
    }
    
	@RequestMapping(value = "/evaluateListIndex.action")
	public String evaluateListIndex(HttpServletRequest request,String agentId) {
		request.setAttribute("agentId", agentId);
		return "agent/evaluateList";
	}
	
	
	/**
	 * 功能描述:获取经纪人评价信息
	 *
	 * <pre>
	 *     luogq:   2015年6月5日      新建
	 * </pre>
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/agentEvaluateData.action")
	@ResponseBody
	public Object agentEvaluateData(@RequestParam Map<String, Object> pars) {
		getParamsByMiniUi(pars);
		return lfAgentService.getAgentEvalueteList(pars);
	}
	
	
	/**
	 * 
	* @Title: gotoagentLog 
	* @Description: 跳转到经纪人操作日志页面
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/gotoagentLog.action")
	public String gotoagentLog()
	{
	    return "agent/agentOperateLog";
	}
	/**
	 * 
	 * @param pars
	 * @return
	 */
	@RequestMapping(value = "/agentLogList.action")
	@ResponseBody
	public Object agentLogList(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
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
		
		return lfAgentService.getAgentLogList(pars);
	}
	
	/**
	 * 
	* @Title: getAgentByStoreOrg 
	* @Description: 根据门店获取员工
	* @param @param request
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getAgentByStoreOrg.action")
    @ResponseBody
    public Object getAgentByStoreOrg(HttpServletRequest request) {
        String orgCode = request.getParameter("orgCode");
        String cid = request.getParameter("cid");
        Map pars = new HashMap();
        pars.put("orgCode", orgCode);
        if ("1".equals(cid)) {
            if ("".equals(orgCode)) {
                return agentSOAServer.getAgentList(null, null, null, null, null);
            }else {
                
                return agentSOAServer.getAgentList(Integer.parseInt(orgCode), null, null, null, null);
            }
        }else if ("2".equals(cid)) {
            if ("".equals(orgCode)) {
                return agentSOAServer.getAgentList(null, null, null, null, null);
            }else {
                return agentSOAServer.getAgentList(null, Integer.parseInt(orgCode), null, null, null);
            }
        }else {
            if ("".equals(orgCode)) {
                return agentSOAServer.getAgentList(null, null, null, null, null);
            }else {
                return agentSOAServer.getAgentList(null, null, Integer.parseInt(orgCode), null, null);
            }
        }
        
       
//        return lfAgentService.getAgentByStoreOrg(pars);
    }
}
