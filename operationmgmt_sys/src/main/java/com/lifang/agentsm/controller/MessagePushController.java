package com.lifang.agentsm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.LfMessagePush;
import com.lifang.agentsm.model.LfMessagePushLimit;
import com.lifang.agentsm.model.enums.MessageTypeEnum;
import com.lifang.agentsm.service.MessagePushService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.mipushsoa.facade.MiPushSOAServer;
import com.lifang.model.Response;

@Controller
@RequestMapping("/message")
public class MessagePushController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(MessagePushController.class);
	
	@Autowired
	private MessagePushService messagePushService;
	@Autowired
    private MiPushSOAServer pc;
	@Autowired
	private AgentSOAServer agentSOAServer;

	/**
	 * 页面跳转 功能描述:TODO(描述这个方法的作用)
	 * 
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     周智明: 2015年5月18日 新建
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/gotomessageList.action")
	public String messageListIndex(HttpServletRequest request,
			HttpServletResponse response) {
		return "message/messageList";
	}

	// 加载图片审核列表
	@RequestMapping(value = "/showMessage.action")
	@ResponseBody
	public Object getVideoAuditList(@RequestParam Map<String, Object> pars) {
		processMiniParam(pars);

		return messagePushService.getMessagePushList(pars);
	}

	// 跳转到新增消息页面
	@RequestMapping(value = "/addmessagePage.action")
	public String openRejectedPage(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> pars) {

		request.setAttribute("id", pars.get("id"));
		 
		 
		 request.setAttribute("istiming", pars.get("istiming"));
		 
		 String title = pars.get("title") + "";
		 
		 String pushtime = pars.get("pushtime") + "";
		 String sb = null;
		 if(!"null".equals(pushtime) && pushtime != null){
			 
			 Date dat=new Date(Long.parseLong(pushtime));  
			 GregorianCalendar gc = new GregorianCalendar();   
			 gc.setTime(dat);  
			 java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
			 sb = format.format(gc.getTime());  
			 System.out.println(sb);  
		 }
		 
         request.setAttribute("pushtime", sb);
		 String pushContent = pars.get("pushContent") + "";
		 try {
				// rooms = java.net.URLDecoder.decode(rooms , "UTF-8");
				pushContent = java.net.URLDecoder.decode(pushContent, "UTF-8");
				title = java.net.URLDecoder.decode(title, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		 if("null".equals(title)){
			 title = "";
		 }
		 if("null".equals(pushContent)){
			 pushContent = "";
		 }
		 request.setAttribute("title", title);
		 request.setAttribute("pushContent", pushContent);
		//
		return "message/addmessagePage";
	}

	@RequestMapping(value = "/updatemessagePage.action")
    public String updatemessagePage(int id,ModelMap map) {
         LfMessagePush push = messagePushService.getMessageById(id);
         map.put("push", push);
        return "message/updatemessagePage";
    }
	// 
	@RequestMapping(value = "/sendMessage.action")
	@ResponseBody
	public Object updateAudit(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> pars) {
		String result = null;
		try {
			LfEmployee employee = getLoginEmployeeInfo(request.getSession());
			pars.put("createUserId", employee.getId());
			pars.put("pushUserId", employee.getId());
			String istiming = pars.get("istiming") + "";
			String pushContent = pars.get("pushContent") + "";
			String title = pars.get("title") + "";
			String cityIds=pars.get("cityIds")+"";
			String companyIds=pars.get("companyIds")+"";
			String pushPlatform=pars.get("pushPlatform")+"";
			try {
				// rooms = java.net.URLDecoder.decode(rooms , "UTF-8");
				pushContent = java.net.URLDecoder.decode(pushContent, "UTF-8");
				title = java.net.URLDecoder.decode(title, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			List<String> mobileLists = new ArrayList<String>();
			mobileLists = messagePushService.findMobileList();
			
			if ("2".equals(pars.get("saves"))) {// 只是保存
				pars.put("pushstatus", 4);// 保存
			} else {
				
				if ("false".equals(istiming)) {// 不是定时消息，实时发送
					try {
					//	List<String> mobile = new ArrayList<String>();
				//		mobile.add("13599999995");
						
					    if(pars.get("pushPlatform").equals("3")){
					        pc.sendMessage(mobileLists, title, pushContent, null, 2, 0, MessageTypeEnum.NOTIFICATION.getCode());//.sendAndroidNotification(mobileList, title,
					    }else{
					        String[] cityId  = cityIds.split(",");
					        String[] companyId  =companyIds.split(",");
					        List<Agent> listAll = new ArrayList<Agent>();
					        for(int i=0;i<cityId.length;i++){
					            List<Agent>  list = agentSOAServer.getAgentList(Integer.valueOf(cityId[i]), null, null, null, null,Integer.valueOf(companyId[i]));
					            listAll.addAll(list);
					        }
					        List<String> allMobile = new ArrayList<String>();
					        if(!listAll.isEmpty()&&listAll.size()>0){
					            for(Agent a:listAll){
					                allMobile.add(a.getMobile());
					            }
					            pc.sendMessage(allMobile, title, pushContent, null, 2, 0, MessageTypeEnum.NOTIFICATION.getCode());//.sendAndroidNotification(mobileList, title,
					        }
					    }
						// message, "0", "");
						pars.put("pushstatus", 2);// 发送成功；
					} catch (Exception e) {
						// 如果发送失败不报错
						pars.put("pushstatus", 3);// 记录发送失败
						
					}
					pars.put("istiming", 2);// 不是定时发送
				} else {
					pars.put("pushstatus", 1);// 待发送
					pars.put("istiming", 1);// 定时发送
				}
				
			}
			
			pars.put("pushContent", pushContent);
			pars.put("title", title);
			if ("".equals(pushContent)) {
				System.out.println("短信内容不能为空");
				result = "3";
				response.getWriter().write(result);
				return null;
			}
			//是否是删除
			if("2".equals(pars.get("isdelete"))){
				
				messagePushService.deleteMessage(pars);
			}else{
				//是否是修改编辑
				if(!"".equals(pars.get("id")) && pars.get("id") != null){
					if ("3".equals(pars.get("pushstatus"))) {// 发送失败
						
					}else{//发送成功
						if ("1".equals(pars.get("saves"))) {// 不是保存是发送
							pars.put("pushstatus", 2);// 保存
						}
					}
					
					if ("false".equals(istiming)) {// 不是定时消息，实时发送
						pars.put("istiming", 2);// 定时发送
					}else{
						pars.put("istiming", 1);// 定时发送
					}
					messagePushService.updateSendMessage(pars);
				}else{
					if ("false".equals(istiming)) {// 不是定时消息，实时发送
						pars.put("istiming", 2);// 定时发送
					}else{
						pars.put("istiming", 1);// 定时发送
					}
					messagePushService.insertSendMessage(pars);
				}
			}
			result = "1";
		} catch (Exception e) {
			System.out.println("图片审核通过更新失败!!");
			result = "0";
			e.printStackTrace();
		}
		try {
			System.out.println(result);
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return null;
	}
	@RequestMapping(value = "/getCompanyAndCity")
    @ResponseBody
	public List<LfMessagePushLimit>  getCompanyAndCity(HttpSession session,LfMessagePushLimit limit){
	    return messagePushService.getCompanyAndCity(limit);
	    
	}
	@RequestMapping(value = "/lookDetail")
	public String lookDetail(int id ,ModelMap map){
	    LfMessagePush push = messagePushService.getMessageById(id);
	    map.put("push", push);
	    return "message/lookmessagePage"; 
	}
	
	@RequestMapping(value = "/getCompanyAndCityByMsgId")
    @ResponseBody
	public List<LfMessagePushLimit> getCompanyAndCityByMsgId(int pushId){
	    return messagePushService.getCompanyAndCityByMsgId(pushId);
	}
	
	@RequestMapping(value = "/chooseCompanyPage")
	public String chooseCompanyPage(int pushId,ModelMap map){
	    map.put("pushId", pushId);
	    return "message/chooseCompanyPage";
	}
	
	@RequestMapping(value = "/saveData")
    @ResponseBody
	public Response saveData(@RequestParam Map<String, Object> pars){
	    return messagePushService.saveData(pars);
	}
}
