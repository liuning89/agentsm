package com.lifang.agentsm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lifang.agentcommsoa.enums.RewardEnum;
import com.lifang.agentcommsoa.service.AgentCommonService;
import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.enums.MessageTypeEnum;
import com.lifang.agentsm.service.HouseImageService;
import com.lifang.agentsm.service.HouseVideoService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.housesoa.facade.service.HouseService;
import com.lifang.mipushsoa.facade.MiPushSOAServer;
import com.lifang.model.enums.MessageEnum;
import com.lifang.mqservice.client.MsgQueueSenderClient;
import com.lifang.mqservice.model.MqMessage;
import com.lifang.sms.model.SmsSendRequest;
import com.lifang.sms.service.SmsDubboService;
import com.lifang.utils.WebTool;

/**
 * Function: TODO ADD FUNCTION
 *
 * @author   luogq
 * @Date	 2015年12月10日		下午9:22:50
 *
 * @see 	  
 */
@Controller
@RequestMapping("/houseVideo")
public class HouseVideoController extends BaseController {

    @Autowired
    private HouseService houseSOAClient;
    @Autowired
    private MiPushSOAServer pc;
    
    @Autowired
    private HouseVideoService houseVideoService;
    @Autowired
    private SmsDubboService smsClient;
    
    @Autowired
    private MsgQueueSenderClient mqSenderClient;

	@Autowired
	private HouseImageService houseImageService;
    @Autowired
	private AgentSOAServer agentSOA;
	@Value("${videourl}")
	private String videourl;

	@Autowired
	private AgentCommonService agentCommonService;
 // 跳转到视频审核页面
    @RequestMapping(value = "/gotoHouseVideo.action")
    public String gotoEmployeeList(HttpServletRequest request, HttpServletResponse response) {
        return "house/houseVideoAudit";
    }
    
    // 跳转到视频审核页面 -- 房东
    @RequestMapping(value = "/gotoLandlordHouseVideo.action")
    public String gotoLandlordHouseVideo(HttpServletRequest request, HttpServletResponse response) {
        return "house/landlordVideoAudit";
    }
    
 // 跳转到视频播放页面
    @RequestMapping(value = "/openToShow.action")
    public String openToShow(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
    	String key = videourl + pars.get("key");
    	request.setAttribute("key", key);

    	request.setAttribute("mobile", pars.get("mobile"));
    	request.setAttribute("png", videourl + pars.get("imagekey"));
        return "house/playVideoPage";
    }
    
    //加载视频审核列表
    @RequestMapping(value = "/getVideoAuditList.action")
  	@ResponseBody
  	public Object getVideoAuditList(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars){
    	processMiniParam(pars);
    	LfEmployee employee= new LfEmployee();
		try {
			employee = getLoginEmployeeInfo(request.getSession());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pars.put("agentCode",employee.getId());
		pars.put("agentCity", employee.getCityId());
    	
  		return houseVideoService.getVideoAuditList(pars);
  	}
    //加载视屏审核列表--房东
    @RequestMapping(value = "/getLandlordVideoAuditList.action")
  	@ResponseBody
  	public Object getLandlordVideoAuditList(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars){
    	processMiniParam(pars);
    	pars.put("agentCode","");
    	
  		return houseVideoService.getVideoAuditList(pars);
  	}
  //视频审核
    @RequestMapping(value = "/updateAudit.action")
    @ResponseBody
    public Object updateAudit(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> pars)
    {
        logger.info(pars.toString());
    	String result = null;
    	try{
			//房源无效后不允许审核通过
			//审核通过的时候判断
			String flag = pars.get("flag")+"";
			if("0".equals(flag)){
				int counts = houseImageService.findHouseById(pars);
				if (counts > 0){
					result = "1000";
					try {
						System.out.println(result);
						response.getWriter().write(result);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
					return null;
				}
			}


    		LfEmployee employee = getLoginEmployeeInfo(request.getSession());
    		pars.put("auditorid", employee.getId());
//    		String rooms = pars.get("rooms")+"";
    		String rejectreason = pars.get("rejectreason")+"";
    		String estateName = pars.get("estateName")+"";
    		try {
//    			rooms = java.net.URLDecoder.decode(rooms , "UTF-8");
    			rejectreason = java.net.URLDecoder.decode(rejectreason , "UTF-8");
    			
    			estateName = java.net.URLDecoder.decode(estateName , "UTF-8");
    		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
    		} 
    		rejectreason = "null".equals(rejectreason)  ? "":rejectreason;
    		pars.put("rejectreason",rejectreason);
    		//图片审核更新逻辑
//    		houseVideoService.updateAudit(pars);
    		
    		houseVideoService.updateAudit(pars);
    		String mobile = pars.get("mobile")+"";
    		if("[null]".equals(mobile) || "null".equals(mobile) || mobile == null ){
    		    mobile = null;
    		}
    		
    		List<String> mobileList = new ArrayList<String>();
    		String token = pars.get("token")+"";
    		List<String> tokenList = new ArrayList<String>();
    		tokenList.add(token);
    		if(mobile != null){
    		    mobileList.add(mobile);
    		}
    		String title = "视频审核通知";
    		String message = null;
    		if("null".equals(estateName)){
    			estateName = "";
    		}
    		if("0".equals(flag)){
    			//发送mq

    			if("1".equals(pars.get("videoType"))){//房源视频
    				int[] houseId = {Integer.parseInt(pars.get("houseId")+"")};
        			sendHouseRentPrice(houseId,"1");
        			Agent agent = agentSOA.getAgent(pars.get("mobile").toString());
        			if(agent!=null){
        			    agentCommonService.rewardWuKongCoin(agent.getId(), RewardEnum.videoChecked, Integer.valueOf(pars.get("houseId").toString()));
        			}

    			}
    			
    			message = "您上传的 "+estateName+"视频审核通过，快去查看吧";
    		}else{
    			message = "您上传的"+estateName+"视频因为"+rejectreason+"审核失败，请重新上传";
    			if(!"[null]".equals(pars.get("phoneNum")) && !"null".equals(pars.get("phoneNum")) && pars.get("phoneNum") != null && !"".equals(pars.get("phoneNum"))){
    			    SmsSendRequest sms = new SmsSendRequest();
                    sms.setMobile(pars.get("phoneNum")+"");
                    sms.setMsgSource(2);
                    sms.setModelId("37939");
                    sms.setParam("");
                    smsClient.sendBusiness(sms);
    			    
    			}
				if("1".equals(pars.get("videoType"))){//房源视频
					int[] houseId = {Integer.parseInt(pars.get("houseId")+"")};
					logger.info("房东视频审核失败开发发送mq消息...");
					sendHouseRentPrice(houseId, "0");
					logger.info("mq发送成功*******************");
				}
    			
    		}
    		//图片审核通过发送消息给android
    		if(!"1".equals(pars.get("ownerId"))){
    			if(mobileList.size() > 0){
					pc.sendMessage(mobileList, title, message, null, 2, 0, MessageTypeEnum.AUDIT.getCode());//.sendAndroidNotification(mobileList, title,
        		//	pc.sendYFYKMsg(mobileList, title, message, MessageTypeEnum.AUDIT.getCode(), 0, "", 0, null);
        			
        		}
    		}
    		result = "1";
    	}catch(Exception e){
    		System.out.println("视频审核通过更新失败!!");
    		result = "0";
    		e.printStackTrace();
    	}
    	try {
    		System.out.println(result);
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
        return null;
    }
    // 跳转到视频驳回弹窗
    @RequestMapping(value = "/openRejectedPage")
    public String openRejectedPage(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> pars) {
    	
    	request.setAttribute("videoType", pars.get("videoType"));
    	request.setAttribute("id", pars.get("id"));
    	request.setAttribute("mobile", pars.get("mobile"));
    	request.setAttribute("phoneNum", pars.get("phoneNum"));
		request.setAttribute("houseId", pars.get("houseId"));

		String estatename = pars.get("estatename") + "";

    	try {
//			rooms = java.net.URLDecoder.decode(rooms , "UTF-8");
    		estatename = java.net.URLDecoder.decode(estatename , "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	request.setAttribute("estatename", estatename);
        return "house/rejectedVideoPage";
    }

    //发送mq
    public void sendHouseRentPrice(int [] houseId,String isSuccess) {
        
        MqMessage msg = new MqMessage();
        msg.setMsgTopic(MessageEnum.MsgSubTopics.house_video);
        msg.setMsgType(MessageEnum.MsgType.update);
        try {
            msg.setIp(WebTool.ip2Int(InetAddress.getLocalHost().getHostAddress()));
        } catch (UnknownHostException e) {
            logger.error("获取本机ip异常！",e);
        }
        JSONObject json = new JSONObject();
        json.put("ids", houseId);
        json.put("dtype", "houseId");
		json.put("isSuccess",isSuccess);//1审核成功,0审核失败
        msg.setMemo(json.toString());
		logger.info("MQ消息发送成功消息内容为:"+json.toString()+"*******");
        try {
            mqSenderClient.sendMessage(msg);
        } catch (Exception e) {
            logger.error("调用发消息接口失败：",e);
        }
 
    }
    
}
