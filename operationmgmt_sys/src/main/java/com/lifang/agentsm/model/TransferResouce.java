package com.lifang.agentsm.model;

import lombok.Data;
@Data
public class TransferResouce {
	private String createBy;//创建人
//转出 h.name townname,
//	h.id townid,
//	f.houseId,
//	f.id 
	private String publishAgentHouseId;//发布经纪人对应的houseId
	private String publishAgentId;//发布经纪人对应的主键ID
    private String publishAgentIn;//发布人经纪人ID
    private String publishAgentTownidIn;//板块ID
    private Integer franchiseeAreaOrgId;//id

    private String pictureAgentHouseId;//实景人 对应的房源ID
    private String pictureAgentId;//实景人 对应的主键ID
    private String pictureAgentIn;//实景人
    private String pictureAgentTownidIn;//板块ID

    private String keyAgentHouseId;//钥匙人房源houseid
    private String keyAgentId;//钥匙人房源主键id
    private String keyAgentIn;//钥匙人经纪人ID
    private String keyAgentTownidIn;//板块ID
    
    private String commAgentHouseId;//速消人 对应房源id
    private String commAgentId;//速消人 对应主键ID
    private String commAgentIn;//速消人
    private String commAgentTownidIn;//板块ID
    
    private String maintainAgentHouseId;//维护人 对应的houseID
    private String maintainAgentId;//维护人 对应的主键ID
    private String maintainAgentIn;//维护人
    private String maintainAgentTownidIn;//板块ID
    
    private String customerId;//客源 主键ID
    private String customerName;//客源 姓名
    private String customerIn;//客源 对应的agentId
    private String customerTownidIn;//板块ID
    
    private String estateId;//小区 lf_agent_estate 对应的主键ID
    private String subEstateId;//小区 lf_agent_estate 表对应的subEstateId
    private String estateAgent;//小区  对应的agentId
    
    private String cusSeeId;//未带看 对应cus_see_house_order表中的ID
    private String cusSeeHouseId;//未带看 cus_see_house_order 中houseId
    private String cusSeeAgent;//未带看 cus_see_house_order 表中agentId
    
    private String cusGuestId;//专属经纪人 对应cus_guest_appoint_agent 表中Id
    private String cusGuestAgent;// 专属经纪人 agentid
    
    private String houseSeeId;//带看 lf_customer_house_see 表中id 
    private String houseSeeAgent;//带看,agentId
    
    private String requirementId;//客户需求 lf_customer_requirement 中的id
    private String requirementAgent;//客户需求 lf_customer_requirement agentid

//转入
    
    private String publishAgent;//发布人
    private String publishAgentTownid;//板块ID

    private String pictureAgent;//实景人
    private String pictureAgentTownid;//实景人

    private String keyAgent;//钥匙人
    private String keyAgentTownid;//钥匙人

    private String commAgent;//速消人
    private String commAgentTownid;//速消人

    private String maintainAgent;//维护人
    private String maintainAgentTownid;

    private String customerTownid;//客源
    private String customerAgentId;//
    private String customer;//
    
    
    
    private String estate;//小区
    private String cusSee;//未带看
    
    private String cusGuest;//专属经济人
    private String houseSee;//带看
    private String requirement;//客户需求
    
  
}