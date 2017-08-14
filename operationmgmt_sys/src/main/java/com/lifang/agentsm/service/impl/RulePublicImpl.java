package com.lifang.agentsm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.RulePublicReadMapper;
import com.lifang.agentsm.dao.write.RulePublicWriteMapper;
import com.lifang.agentsm.model.CustomerRequirement;
import com.lifang.agentsm.model.HouseCountTown;
import com.lifang.agentsm.model.LfPubCustomer;
import com.lifang.agentsm.model.PublicReport;
import com.lifang.agentsm.model.RuleModel;
import com.lifang.agentsm.model.RulePublic;
import com.lifang.agentsm.service.RulePublicService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.bzsm.console.report.write.HouseCountWriteMapper;

@Service
public class RulePublicImpl implements RulePublicService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RulePublicReadMapper rulePublicReadMapper;
    @Autowired
    private RulePublicWriteMapper rulePublicWriteMapper;
    @Autowired
    private AgentSOAServer agentSoa;
	@Autowired
	private HouseCountWriteMapper houseCountWriteMapper;
    @Override
    public String savePublicRule(Map<String, Object> pars) {
        String result = "";
        try {

        	RulePublic rp = rulePublicReadMapper.selectRulePublic();
            if(rp != null){
                rulePublicWriteMapper.updatePublicRule(pars);
            }else{

                rulePublicWriteMapper.insertPublicRule(pars);
            }

            result = "1";
        }catch (Exception e){
            result = "0";
        }
        return result;
    }
	@Override
	public RulePublic findRulePublic() {
		
		return rulePublicReadMapper.selectRulePublic();
	}
	@Override
	public void runMain() {
		//读取私客转公客的天数
		RulePublic rpPublic = rulePublicReadMapper.selectRulePublic();
		logger.info("私客转公客的天数为:"+rpPublic);
		//计算私客转店公客数据时间范围
		String storeTime = getDay(Integer.parseInt(rpPublic.getPublicToStore()));
		String areaTime = getAddDay(Integer.parseInt(rpPublic.getStoreToArea()));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storeTime", storeTime);
		map.put("areaTime", areaTime);
		logger.info("私客转店公客数据时间范围为:storeTime="+storeTime+"---areaTime="+areaTime);
		//查询这个时间段的店公客
//		List<LfPubCustomer> pubList = rulePublicReadMapper.findPubCustomerByTime(map);
		
		map.put("time", areaTime);
		//查询这个时间段的带看数量
//		List<HouseSeeRequest> houseSeeList = rulePublicReadMapper.findHouseSeeByTime(map);
	      List<LfPubCustomer> houseSeeList = rulePublicReadMapper.findHouseSeeAreaByTime(map);
	      
		logger.info("带看数量:"+houseSeeList.size());
		//只有几个ID都为空的数据才是需要转移的问题.
	      Iterator<LfPubCustomer> ruleIterator = houseSeeList.iterator();
	      while (ruleIterator.hasNext()) { 
	          com.lifang.agentsm.model.LfPubCustomer rm = ruleIterator.next();
	          
	          long dayTime = getTime(rm.getCreateTime(),new Date());
	            if (dayTime < Long.parseLong(rpPublic.getStoreToArea())) {
	                ruleIterator.remove();
	                continue;
	            }
	          
	          
	          if ( rm.getBidOrderId() == null && rm.getFollowUpId() == null && rm.getHouseSeeId() == null) {
                
	          }else {
                ruleIterator.remove();
            }
	      }

		//开始店公客转移到区公客
		if (houseSeeList.size() > 0) {
			logger.info("开始店转区***************************");
			//首先把店公客数据标记被无效
			map.put("pubList", houseSeeList);
			rulePublicWriteMapper.updatePubCustomer(map);
			
			//开始插入区公客数据
			rulePublicWriteMapper.insertPubCustomer(map);
			logger.info("店转区完成***************");
		}
		
/**********************************************************私客转公客******************************************************************************************************/
		logger.info("开始私客转店公客***************");
		//查询这个时间段的产生的客户数量
//		List<Customer> customerList = rulePublicReadMapper.findCustomerByTime(map);
		
//		logger.info("客户数量:"+customerList.size());
		//查找店公客时间时间段带看数
		map.put("time", storeTime);
		//查询这个时间段的带看数量
		List<RuleModel> houseSeeStoreList = rulePublicReadMapper.findHouseSeeByTime(map);
		Iterator<RuleModel> ruleModeIterator = houseSeeStoreList.iterator();
        while (ruleModeIterator.hasNext()) { 
            RuleModel rm = ruleModeIterator.next();
            if (rm.getBidOrderId() == null && rm.getFollowUpId() == null && rm.getHouseSeeId() == null) {

//            if (rm.getBidOrderId() == null && rm.getFollowUpId() == null && rm.getHouseSeeId() == null) {
              
            }else {
                ruleModeIterator.remove();
          }
        }
		
		//跟进
//		List<RuleModel> rmList = rulePublicReadMapper.findFollowByTime(map);
		
		logger.info("店公客带看数量:"+houseSeeStoreList.size());
		
		Map<String, RuleModel> ruleMap = new HashMap<String, RuleModel>();
		for(RuleModel rModel : houseSeeStoreList){
		    
		    if (rModel.getStoreId() == null) {
                rModel.setStoreId(-1);
            }
		    
		    ruleMap.put(rModel.getStoreId()+rModel.getMobile(), rModel);
		}
		
	System.out.println("去重前的列表大小:"+houseSeeStoreList.size());
	houseSeeStoreList.clear();
	for(Map.Entry<String, RuleModel> rEntry : ruleMap.entrySet()){
	    houseSeeStoreList.add(rEntry.getValue());
	}
	System.out.println("去重后列表大小:"+houseSeeStoreList.size());
	
	   //查找区域ID
	    List<String> areaList = rulePublicReadMapper.findAreaIdBySH();
//	    String[] strings = ;
//	    String[] arr = (String[])areaList.toArray(new String[areaList.size()]);
	    
	int k = 0;
		//开始将客户类型装换成公客表类型
		List<LfPubCustomer> lfpcList = new ArrayList<LfPubCustomer>();
		int kk = 0;
		for(RuleModel c : houseSeeStoreList){
			LfPubCustomer lfp = new LfPubCustomer();
			//判断是否新客户
//			String areaTime = getAddDay(Integer.parseInt(rpPublic.getStoreToArea()));
			long dayTime = getTime(c.getCreateTime(),new Date());
			if (dayTime < Long.parseLong(rpPublic.getPublicToStore())) {
                continue;
            }
			//cus表客户
			if (c.getCustime() != null) {
                
			    long cusDayTime = getTime(c.getCustime(),new Date());
			    if (cusDayTime < Long.parseLong(rpPublic.getPublicToStore())) {
			        continue;
			    }
            }
			
			//查找次客户是否已经存在店公客
			int flag = rulePublicReadMapper.findPubCustomerByMobile(c.getMobile());
			if (flag > 0) {
				continue;
			}
			
			lfp.setCustomerName(c.getName());//客户姓名      
			            
			lfp.setCustomerMobile(c.getMobile());//客户手机号        
			            
			lfp.setCustomerGender(Integer.parseInt(c.getGender() == null?"-1":c.getGender()));//客户性别 
			//根据客户id查出客户需求
			CustomerRequirement cr = rulePublicReadMapper.findCustomerRequirementById(Long.parseLong(c.getCustomerId()));
			if (cr != null) {
				
				lfp.setCustomerReq(cr.getCustomerReq());
			}
			//查找客户的总带看次数
			int count = rulePublicReadMapper.findCountSee(Long.parseLong(c.getCustomerId())); 
			lfp.setHouseSeeCount(count);//'带看次数',
			
			//查找客户最后带看时间
			Date date = rulePublicReadMapper.findDateSee(Long.parseLong(c.getCustomerId()));
			lfp.setLastSeeTime(date==null?new Date():date);//'最后带看时间',
			            
			if ("25".equals(c.getSource())) {
				lfp.setSourceFrom(1);//客户来源1悟空找房2网络客户3其它客户',

			}else if ("1".equals(c.getIsCallBackCus())) {
				lfp.setSourceFrom(2);//客户来源1悟空找房2网络客户3其它客户',

			}else {
				lfp.setSourceFrom(3);//客户来源1悟空找房2网络客户3其它客户',

			}
			if ("13681104345".equals(c.getMobile())) {
                System.out.println("++++++++++++++++++++++++++13681104345");
            }
			//查找经纪人门店和区域
			Agent agent = agentSoa.getAgent(Integer.parseInt(c.getAgentId()));
			if (agent == null) {
			    continue;
//			    if (k > arr.length-1) {
//                    k = 0;
//                }
//			    lfp.setAreaId(Integer.parseInt(arr[k]));//'区域ID',
//			    k++;
//			    
//			    lfp.setStatus(1);//'状态0无效1有效',
//                
//                lfp.setCustomerType(2);// '客户类型1店公客2区功课',
//                            
//                lfp.setChangeType(0);//'更改类型0失效1认领2转为区公客',
			}else {
//                System.out.println("**************"+c.getAgentId());
                lfp.setStoreId(agent.getStoreId());//'门店ID',
                            
                lfp.setAreaId(agent.getAreaOrgId());//'区域ID',
                            
                lfp.setStatus(1);//'状态0无效1有效',
                            
                lfp.setCustomerType(1);// '客户类型1店公客2区功课',
                            
                lfp.setChangeType(0);//'更改类型0失效1认领2转为区公客',
            }
//			System.out.println(agent);
			
			lfpcList.add(lfp);
			if (kk == 5000) {
			    logger.info("数据组装完毕,开始插入**********lfpcList大小:"+lfpcList.size());
		        map.put("lfpcList", lfpcList);
		        if (lfpcList != null && lfpcList.size() > 0) {
		            System.out.println("lfpcList大小为:"+lfpcList.size()+"********************内层");
		            rulePublicWriteMapper.insertPubCustomerStore(map);
		            lfpcList.clear();
		            kk = 0;
		        }
            }
			kk ++;
		}
		logger.info("数据组装完毕,开始插入**********lfpcList大小:"+lfpcList.size());
		map.put("lfpcList", lfpcList);
		if (lfpcList != null && lfpcList.size() > 0) {
		    System.out.println("lfpcList大小为:"+lfpcList.size()+"********************外层");
		    rulePublicWriteMapper.insertPubCustomerStore(map);
        }
		
		logger.info("店公客转移完毕,时间:"+(new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss")).format(new Date()));
	}
	
	public static String getDay(int day){
		Date date = new Date(); 
		   System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(date)); 
		  Calendar cal = Calendar.getInstance(); 
		  cal.setTime(date); 
		  cal.add(Calendar.DATE, -day); 
		  System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime())); 
		  
		  return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
    }
	public static String getAddDay(int day){
        Date date = new Date(); 
           System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(date)); 
          Calendar cal = Calendar.getInstance(); 
          cal.setTime(date); 
          cal.add(Calendar.DATE, day); 
          System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime())); 
          
          return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
    }
	
	public static long getTime(Date startTime,Date endTime){

        try {

            long diff = endTime.getTime() - startTime.getTime();   

            long days = diff / (1000 * 60 * 60 * 24);

            return days;

        } catch (Exception e) {

            // TODO: handle exception

        }
        return -1;
    }
    @Override
    public void runReport() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", sdf.format(d));
//        map.put("date", "2016-01-22");
        List<PublicReport> prList = rulePublicReadMapper.selectPubReport(sdf.format(d));
//		List<PublicReport> prList = rulePublicReadMapper.selectPubReport("2016-01-22");
        map.put("prList", prList);
        logger.info("公客数据prList大小为:"+prList.size()+"***********");
        if(prList != null && prList.size() > 0){


//			int count = rulePublicReadMapper.findisfalgpubReport(map);
//			if (count > 0){
//
//			}else{
				//插入区公客认领,区公客查看,店公客认领,店公客查看, 公客表id,经纪人id
            logger.info("开始插入数据***********");
            logger.info(prList.get(0).getViewstore()+"店公客查看数");
			rulePublicWriteMapper.insertPubreport(map);
//			}


        }else{
            logger.info("prList为空************");
        }

		List<PublicReport> storeCountList = rulePublicReadMapper.selectStoreCount(sdf.format(d));
		Map<String, Object> pars = new HashMap<String, Object>();
		for (PublicReport publicReport : storeCountList){
			pars.put("dates",sdf.format(d));
			pars.put("agentId",publicReport.getAgentId());
			pars.put("storepub",publicReport.getStorepub());
			int count = rulePublicReadMapper.findpubReport(pars);
			if (count > 0){
				rulePublicWriteMapper.updatePubReport(pars);//店公客
			}else{
				rulePublicWriteMapper.insertPubreportrl(pars);
			}
		}

		List<PublicReport> areaCountList = rulePublicReadMapper.selectAreaCount(sdf.format(d));
		Map<String, Object> areapars = new HashMap<String, Object>();
		for (PublicReport publicReport : areaCountList){
			areapars.put("dates",sdf.format(d));
			areapars.put("agentId",publicReport.getAgentId());
			areapars.put("areapub",publicReport.getAreapub());
			int count = rulePublicReadMapper.findpubReport(areapars);
			if (count > 0){
				rulePublicWriteMapper.updateAreaReport(areapars);//区公客
			}else{
				rulePublicWriteMapper.insertAreaReportrl(areapars);
			}
		}



    }

	@Override
	public void runHouseCountMain() {

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", sdf.format(d));
		Set<HouseCountTown> hctSet = new HashSet<HouseCountTown>();//用来存放城市,区域,


		List<HouseCountTown> allTempList = new ArrayList<HouseCountTown>();
//		List<HouseCountTown> allList = new ArrayList<HouseCountTown>();
//		HouseCountTown houseCountTown = new HouseCountTown();
//		houseCountTown.setCityId(-1000);
//		allList.add(houseCountTown);//增加一条初始数据
		//开始查询数据
//		List<HouseCountTown> hctList = rulePublicReadMapper.findHouseSellCount(sdf.format(d));
		List<HouseCountTown> sellcountList = rulePublicReadMapper.findHouseSellCount();//有效房源数
		List<HouseCountTown> auditcountList = rulePublicReadMapper.findHouseAuditcount();//待审批下架房源数
		List<HouseCountTown> picturecountList = rulePublicReadMapper.findHousePicturecount();//实景房源数
		List<HouseCountTown> videocountList = rulePublicReadMapper.findHouseVideocount();//视频房源数
		List<HouseCountTown> sellPointcountList = rulePublicReadMapper.findHouseSellPointcount();//描述数
		List<HouseCountTown> commcountList = rulePublicReadMapper.findHouseCommcount();//速消房源数
		List<HouseCountTown> keycountList = rulePublicReadMapper.findHouseKeycount();//钥匙房源数
		List<HouseCountTown> houseStatecountList = rulePublicReadMapper.findHouseStatecount();//无效房源


//		if (sellcountList != null && sellcountList.size() > 0) {
//			allList.addAll(sellcountList);
//		}

//		if (allList != null && allList.size() > 0){
//			for (HouseCountTown sl1 : allList) {//有效房源
//				for (HouseCountTown sl2 : sellcountList) {
//					if (sl1.getCityId().intValue() == sl2.getCityId().intValue() && sl1.getAreaId().intValue() == sl2.getAreaId().intValue() && sl1.getTownId().intValue() == sl2.getTownId().intValue()) {
//						sl1.setAuditcount(sl2.getAuditcount());
//					}else{
//						allList.add(sl2);
//					}
//				}
//			}
//		}
		HouseCountTown hct = null;
		for (HouseCountTown sl1 : auditcountList) {//待审批


			hct = new HouseCountTown();
			hct.setCityId(sl1.getCityId());
			hct.setAreaId(sl1.getAreaId());
			hct.setTownId(sl1.getTownId());

			hct.setCityName(sl1.getCityName());
			hct.setAreaName(sl1.getAreaName());
			hct.setTownName(sl1.getTownName());

			hctSet.add(hct);
		}

		for (HouseCountTown sl1 : picturecountList) {// 实景房源数
			hct = new HouseCountTown();
			hct.setCityId(sl1.getCityId());
			hct.setAreaId(sl1.getAreaId());
			hct.setTownId(sl1.getTownId());
			hct.setCityName(sl1.getCityName());
			hct.setAreaName(sl1.getAreaName());
			hct.setTownName(sl1.getTownName());
			hctSet.add(hct);
		}


		for (HouseCountTown sl1 : videocountList) {//视频房源数
			hct = new HouseCountTown();
			hct.setCityId(sl1.getCityId());
			hct.setAreaId(sl1.getAreaId());
			hct.setTownId(sl1.getTownId());
			hct.setCityName(sl1.getCityName());
			hct.setAreaName(sl1.getAreaName());
			hct.setTownName(sl1.getTownName());
			hctSet.add(hct);
		}


		for (HouseCountTown sl1 : sellPointcountList) {//描述数
			hct = new HouseCountTown();
			hct.setCityId(sl1.getCityId());
			hct.setAreaId(sl1.getAreaId());
			hct.setTownId(sl1.getTownId());
			hct.setCityName(sl1.getCityName());
			hct.setAreaName(sl1.getAreaName());
			hct.setTownName(sl1.getTownName());
			hctSet.add(hct);
		}

		for (HouseCountTown sl1 : commcountList) {//速消房源数
			hct = new HouseCountTown();
			hct.setCityId(sl1.getCityId());
			hct.setAreaId(sl1.getAreaId());
			hct.setTownId(sl1.getTownId());
			hct.setCityName(sl1.getCityName());
			hct.setAreaName(sl1.getAreaName());
			hct.setTownName(sl1.getTownName());
			hctSet.add(hct);
		}


		for (HouseCountTown sl1 : keycountList) {//钥匙
			hct = new HouseCountTown();
			hct.setCityId(sl1.getCityId());
			hct.setAreaId(sl1.getAreaId());
			hct.setTownId(sl1.getTownId());
			hct.setCityName(sl1.getCityName());
			hct.setAreaName(sl1.getAreaName());
			hct.setTownName(sl1.getTownName());
			hctSet.add(hct);
		}
		for (HouseCountTown sl1 : houseStatecountList) {//无效
			hct = new HouseCountTown();
			hct.setCityId(sl1.getCityId());
			hct.setAreaId(sl1.getAreaId());
			hct.setTownId(sl1.getTownId());
			hct.setCityName(sl1.getCityName());
			hct.setAreaName(sl1.getAreaName());
			hct.setTownName(sl1.getTownName());
			hctSet.add(hct);
		}

/************************************************************************************************************************************************************************************************************************************************/
		for (HouseCountTown sl1 : hctSet) {//有效房源数
			if(sl1.getCityId() == null || sl1.getAreaId() == null || sl1.getTownId() == null ) {
				continue;
			}
			for (HouseCountTown sl2 : sellcountList) {

				if(sl2.getCityId() == null || sl2.getAreaId() == null || sl2.getTownId() == null ) {
					continue;
				}
				if (sl1.getCityId().intValue() == sl2.getCityId().intValue() && sl1.getAreaId().intValue() == sl2.getAreaId().intValue() && sl1.getTownId().intValue() == sl2.getTownId().intValue()) {
					sl1.setSellcount(sl2.getSellcount());
				}

			}
		}

		for (HouseCountTown sl1 : hctSet) {//待审批
			if(sl1.getCityId() == null || sl1.getAreaId() == null || sl1.getTownId() == null ) {
				continue;
			}
			for (HouseCountTown sl2 : auditcountList) {

				if(sl2.getCityId() == null || sl2.getAreaId() == null || sl2.getTownId() == null ) {
					continue;
				}
				if (sl1.getCityId().intValue() == sl2.getCityId().intValue() && sl1.getAreaId().intValue() == sl2.getAreaId().intValue() && sl1.getTownId().intValue() == sl2.getTownId().intValue()) {
					sl1.setAuditcount(sl2.getAuditcount());
				}
			}
		}

		for (HouseCountTown sl1 : hctSet) {// 实景房源数
			if(sl1.getCityId() == null || sl1.getAreaId() == null || sl1.getTownId() == null ) {
				continue;
			}
			for (HouseCountTown sl2 : picturecountList) {
				if(sl2.getCityId() == null || sl2.getAreaId() == null || sl2.getTownId() == null ) {
					continue;
				}
				if (sl1.getCityId().intValue() == sl2.getCityId().intValue() && sl1.getAreaId().intValue() == sl2.getAreaId().intValue() && sl1.getTownId().intValue() == sl2.getTownId().intValue()) {
					sl1.setPicturecount(sl2.getPicturecount());
				}
			}
		}


		for (HouseCountTown sl1 : hctSet) {//视频房源数
			if(sl1.getCityId() == null || sl1.getAreaId() == null || sl1.getTownId() == null ) {
				continue;
			}
			for (HouseCountTown sl2 : videocountList) {
				if(sl2.getCityId() == null || sl2.getAreaId() == null || sl2.getTownId() == null ) {
					continue;
				}
				if (sl1.getCityId().intValue() == sl2.getCityId().intValue() && sl1.getAreaId().intValue() == sl2.getAreaId().intValue() && sl1.getTownId().intValue() == sl2.getTownId().intValue()) {
					sl1.setVideocount(sl2.getVideocount());
				}
			}
		}


		for (HouseCountTown sl1 : hctSet) {//描述数
			if(sl1.getCityId() == null || sl1.getAreaId() == null || sl1.getTownId() == null ) {
				continue;
			}
			for (HouseCountTown sl2 : sellPointcountList) {
				if(sl2.getCityId() == null || sl2.getAreaId() == null || sl2.getTownId() == null ) {
					continue;
				}
				if (sl1.getCityId().intValue() == sl2.getCityId().intValue() && sl1.getAreaId().intValue() == sl2.getAreaId().intValue() && sl1.getTownId().intValue() == sl2.getTownId().intValue()) {
					sl1.setSellPointcount(sl2.getSellPointcount());
				}
			}
		}


		for (HouseCountTown sl1 : hctSet) {//速消房源数
			if(sl1.getCityId() == null || sl1.getAreaId() == null || sl1.getTownId() == null ) {
				continue;
			}
			for (HouseCountTown sl2 : commcountList) {
				if(sl2.getCityId() == null || sl2.getAreaId() == null || sl2.getTownId() == null ) {
					continue;
				}
				if (sl1.getCityId().intValue() == sl2.getCityId().intValue() && sl1.getAreaId().intValue() == sl2.getAreaId().intValue() && sl1.getTownId().intValue() == sl2.getTownId().intValue()) {
					sl1.setCommcount(sl2.getCommcount());
				}
			}
		}


		for (HouseCountTown sl1 : hctSet) {//钥匙
			if(sl1.getCityId() == null || sl1.getAreaId() == null || sl1.getTownId() == null ) {
				continue;
			}
			for (HouseCountTown sl2 : keycountList) {
				if(sl2.getCityId() == null || sl2.getAreaId() == null || sl2.getTownId() == null ) {
					continue;
				}
				if (sl1.getCityId().intValue() == sl2.getCityId().intValue() && sl1.getAreaId().intValue() == sl2.getAreaId().intValue() && sl1.getTownId().intValue() == sl2.getTownId().intValue()) {
					sl1.setKeycount(sl2.getKeycount());
				}
			}
		}
		for (HouseCountTown sl1 : hctSet) {//无效
			if(sl1.getCityId() == null || sl1.getAreaId() == null || sl1.getTownId() == null ) {
				continue;
			}
			for (HouseCountTown sl2 : houseStatecountList) {
				if(sl2.getCityId() == null || sl2.getAreaId() == null || sl2.getTownId() == null ) {
					continue;
				}
				if (sl1.getCityId().intValue() == sl2.getCityId().intValue() && sl1.getAreaId().intValue() == sl2.getAreaId().intValue() && sl1.getTownId().intValue() == sl2.getTownId().intValue()) {
					sl1.setHouseStatecount(sl2.getHouseStatecount());
				}
			}
		}
		allTempList.addAll(hctSet);

		Iterator<HouseCountTown> iterator = allTempList.iterator();

		while(iterator.hasNext()){
			HouseCountTown htc = iterator.next();
			if (htc.getCityId() == null) {
				iterator.remove();
				break;
			}
		}

		if (allTempList != null && allTempList.size() > 0){
			//开始插入
			int count = houseCountWriteMapper.insertHouseSellCount(allTempList);
			System.out.print(count);
		}

	}
}
