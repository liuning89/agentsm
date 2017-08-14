package com.lifang.agentsm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lifang.agentsm.base.model.WkCoinConsumeExport;
import com.lifang.agentsm.base.model.WkCoinPayExport;
import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.dao.read.LfAreaOrgReadMapper;
import com.lifang.agentsm.dao.read.LfYfykAmoutSetReadMapper;
import com.lifang.agentsm.dao.write.LfYfykAmoutSetWriteMapper;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.ComboModel;
import com.lifang.agentsm.model.LfAgentAccountDetail;
import com.lifang.agentsm.model.LfAgentFeeSet;
import com.lifang.agentsm.model.LfFranchiseeInfo;
import com.lifang.agentsm.model.LfYfykAmoutSet;
import com.lifang.agentsm.model.LfYfykAmoutSetLog;
import com.lifang.agentsm.model.WkCoinDetailModel;
import com.lifang.agentsm.model.WkCoinGiveExport;
import com.lifang.agentsm.model.WkCoinGivelModel;
import com.lifang.agentsm.model.WkCoinReportExport;
import com.lifang.agentsm.model.WkcoinReport;
import com.lifang.agentsm.service.LfYfykAmoutSetService;
import com.lifang.agentsm.utils.OrgCodeUtil;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Agent;
import com.lifang.agentsoa.model.Employee;
import com.lifang.bzsm.console.report.read.WkCoinReportReadMapper;
import com.lifang.model.PageResponse;
import com.lifang.model.Response;
import com.lifang.paysoa.facade.PayWXFacadeService;
import com.lifang.paysoa.model.DonateWkbRequest;
import com.lifang.sso.SsoClientUtil;
@Service
public class LfYfykAmoutSetServiceImpl implements LfYfykAmoutSetService {

    @Autowired
    private LfYfykAmoutSetReadMapper lfYfykAmoutSetReadMapper;
    @Autowired
    private LfYfykAmoutSetWriteMapper lfYfykAmoutSetWriteMapper;
    @Autowired
    private SsoClientUtil ssoClientUtil;
    @Autowired
    private LfAreaOrgReadMapper lfAreaOrgReadMapper;
    @Autowired
    private AgentSOAServer agentSoa;
    @Autowired
    private PayWXFacadeService payWXFacadeService;
    @Autowired
    private WkCoinReportReadMapper wkCoinReportReadMapper;
    @Override
    public List<LfYfykAmoutSet> getList(LfYfykAmoutSet amoutSet) {
        if(StringUtils.isNotBlank(amoutSet.getAgentName())){
            List<com.lifang.agentsoa.model.Employee> list = agentSoa.getEmployeeByName(amoutSet.getAgentName());
            String agentIds="";
            if(list!=null && !list.isEmpty()){
                for(int i=0;i<list.size();i++){
                    if(i!=list.size()-1){
                        agentIds +=list.get(i).getId()+ ",";   
                    }else{
                        agentIds +=list.get(i).getId();
                    }
                }
            }
            if(StringUtils.isNotBlank(agentIds)){
                amoutSet.setAgentIds(agentIds);
            }else{
                amoutSet.setAgentIds("0");
            }
        }
        List<LfYfykAmoutSet> setList = lfYfykAmoutSetReadMapper.getList(amoutSet);
        for(LfYfykAmoutSet set:setList){
           Employee em =  agentSoa.getEmployee(set.getAgentId());
           if(em!=null){
               set.setAgentName(em.getName());
           }
        }
        return setList;
    }

    @Override
    public Response updateStatus(int id, int yfykStatus) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("yfykStatus",yfykStatus);
        int result = lfYfykAmoutSetWriteMapper.updateStatus(map);
        if(result==1){
            //插入修改日志
            LfYfykAmoutSetLog log = new LfYfykAmoutSetLog();
            try {
                log.setOperatorId(ssoClientUtil.getCurrentUser().getId());
            }catch (Exception e) {
                throw new RuntimeException("sso获取登录人出错",e);
            }
            if(yfykStatus==0){
                log.setAfterValue("0");
                log.setBeforeValue("1");
                log.setContent("显示");
            }else{
                log.setAfterValue("1");
                log.setBeforeValue("0");
                log.setContent("隐藏");
            }
            log.setType("1");
            
           log.setSetId(id);
            lfYfykAmoutSetWriteMapper.insertLog(log);
            return new Response("success",1);
        }
        return new Response("fail",0);
    }

    @Override
    public LfYfykAmoutSet getAmoutSetById(int id) {
        return lfYfykAmoutSetReadMapper.getAmoutSetById(id);
    }

    @Override
    public Response save(LfYfykAmoutSet amoutSet) {
        LfYfykAmoutSet set = lfYfykAmoutSetReadMapper.getAmoutSetById(amoutSet.getId());
        int result = lfYfykAmoutSetWriteMapper.save(amoutSet);
        if (result > 0) {
            LfYfykAmoutSetLog log =null;
            if(set.getWkCoinDenomination()!=amoutSet.getWkCoinDenomination()){
               log = new LfYfykAmoutSetLog();
                try {
                    log.setOperatorId(ssoClientUtil.getCurrentUser().getId());
                    log.setSetId(amoutSet.getId());
                    log.setContent("悟空币面额");
                    log.setBeforeValue(set.getWkCoinDenomination()+"");
                    log.setAfterValue(amoutSet.getWkCoinDenomination()+"");
                    log.setType("1");
                    lfYfykAmoutSetWriteMapper.insertLog(log);
                }catch (Exception e) {
                    throw new RuntimeException("sso获取登录人出错",e);
                }
            }
            if(set.getPrice()!=amoutSet.getPrice()){
                log = new LfYfykAmoutSetLog();
                 try {
                     log.setOperatorId(ssoClientUtil.getCurrentUser().getId());
                     log.setSetId(amoutSet.getId());
                     log.setContent("金额");
                     log.setBeforeValue(set.getPrice()+"");
                     log.setAfterValue(amoutSet.getPrice()+"");
                     log.setType("1");
                     lfYfykAmoutSetWriteMapper.insertLog(log);
                 }catch (Exception e) {
                     throw new RuntimeException("sso获取登录人出错",e);
                 }
             }
            return new Response("success", 1);
        }
        return new Response("fail",0);
    }

    @Override
    public Response add(LfYfykAmoutSet amoutSet) {
        try {
            amoutSet.setAgentId(ssoClientUtil.getCurrentUser().getId());
        }
        catch (Exception e) {
           throw new RuntimeException("SSO取登录人Id出错",e);
        }
        int i=lfYfykAmoutSetWriteMapper.add(amoutSet);
        if(i>0){
           return new Response("success",1); 
        }
        return new Response("fail",0);
    }

    @Override
    public List<LfAgentFeeSet> getFeeSetList(int id) {
        List<LfAgentFeeSet> feeSetList = lfYfykAmoutSetReadMapper.getFeeSetList(id);
        for(LfAgentFeeSet fee:feeSetList){ 
            Employee em =  agentSoa.getEmployee(fee.getAgentId());
            if(em!=null){
                fee.setAgentName(em.getName());
            }
        }
        return feeSetList;
    }

    @Override
    public Response saveFeeSet(LfAgentFeeSet set) {
        LfAgentFeeSet feeSet =lfYfykAmoutSetReadMapper.getFeeSetList(set.getId()).get(0);
        int i = lfYfykAmoutSetWriteMapper.saveFeeSet(set);
        if(i>0){
            if(set.getConsumecoin()!=feeSet.getConsumecoin()){
                //保存操作记录
                LfYfykAmoutSetLog log =null;
                try {
                    log = new LfYfykAmoutSetLog();
                    log.setAfterValue(set.getConsumecoin()+"");
                    log.setBeforeValue(feeSet.getConsumecoin()+"");
                    log.setSetId(set.getId());
                    log.setOperatorId(ssoClientUtil.getCurrentUser().getId());
                    log.setType("1");
                }
                catch (Exception e) {
                   throw new RuntimeException("SSO取用户信息出错！",e);
                }
                lfYfykAmoutSetWriteMapper.saveLog(log);
            }
        }
        return new Response("success",1);
    }

    @Override
    public PageResponse getFeeSetLogList(LfYfykAmoutSetLog req) {
        req.setPageIndex(req.getPageIndex()+1);
        Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
        List<LfYfykAmoutSetLog> LfAgentFeeSetLogList = lfYfykAmoutSetReadMapper.getFeeSetLogList(pagination);
        for(LfYfykAmoutSetLog log : LfAgentFeeSetLogList){
            Employee em =  agentSoa.getEmployee(log.getOperatorId());
            if(em!=null){
                log.setAgentName(em.getName());
            }
        }
        PageResponse pageResponse = new PageResponse("success", 1,LfAgentFeeSetLogList);
        pageResponse.setTotal(pagination.getTotal());
        return pageResponse;
    }

    @Override
    public Response getCountNum(int status) {
        int result =  lfYfykAmoutSetReadMapper.getCountNum(status);
        return new Response("success",1,result);
    }

    @Override
    public Response getWkCoinConsumeList(WkCoinDetailModel req,LfEmployee employee) {
        req.setPageIndex(req.getPageIndex()+1);

        Integer wpid=null;
       /* if(req.getCompanyName()!=null&&!"".equals(req.getCompanyName())){
            LfFranchiseeInfo franchisee = lfYfykAmoutSetReadMapper.selectFranchiseeByName(req.getCompanyName());
            if(franchisee!=null){
                wpid=Integer.valueOf(franchisee.getId().toString());
            }else{
                wpid=-1;
            }
        }*/
        if(req.getCompanyId()!=null){
            wpid=req.getCompanyId();
        }else{
            wpid=null;
        }
        List<Integer> agentList = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSoa, lfAreaOrgReadMapper,employee, wpid);
        String agentIds="0";
        if(agentList!=null && !agentList.isEmpty()){
            for(int i=0;i<agentList.size();i++){
                if(i!=agentList.size()-1){
                    agentIds+=agentList.get(i)+",";
                }else{
                    agentIds+=agentList.get(i);
                }
            }
        }
        if(req.getAgentId()!=null){
            req.setAgentIds(req.getAgentId()+"");
        }else{
            req.setAgentIds(agentIds);
        }
        Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
        List<WkCoinDetailModel> wkCoinList = lfYfykAmoutSetReadMapper.getWkCoinConsumeList(pagination);
        for(WkCoinDetailModel coin : wkCoinList){
            coin.setPrice(coin.getPrice().replace("-", ""));
            Agent agent = agentSoa.getAgent(Integer.valueOf(coin.getAgentId()));
            if(agent!=null){
                coin.setAgentName(agent.getName());
                coin.setStoreName(agent.getStoreName());
                coin.setCompanyName(agent.getFranchiseeCompanyName());
                coin.setCityName(agent.getCityName());
                coin.setAreaOrgName(agent.getAreaOrgName());                
            }
        }
        PageResponse pageResponse = new PageResponse("success", 1,wkCoinList);
        pageResponse.setTotal(pagination.getTotal());
        return pageResponse;
    
    
    }


    @Override
    public Response getWkCoinPayList(WkCoinDetailModel req,LfEmployee employee) {
        req.setPageIndex(req.getPageIndex()+1);

        Integer wpid=null;
        if(req.getCompanyId()!=null){
            wpid=req.getCompanyId();
        }else{
            wpid=null;
        }
        /*if(req.getCompanyName()!=null&&!"".equals(req.getCompanyName())){
            LfFranchiseeInfo franchisee = lfYfykAmoutSetReadMapper.selectFranchiseeByName(req.getCompanyName());
            if(franchisee!=null){
                wpid=Integer.valueOf(franchisee.getId().toString());
            }else{
                wpid=0;
            }
        }*/
        List<Integer> agentList = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSoa, lfAreaOrgReadMapper,employee, wpid);
        String agentIds="0";
        if(agentList!=null && !agentList.isEmpty()){
            for(int i=0;i<agentList.size();i++){
                if(i!=agentList.size()-1){
                    agentIds+=agentList.get(i)+",";
                }else{
                    agentIds+=agentList.get(i);
                }
            }
        }
        if(req.getAgentId()!=null){
            req.setAgentIds(req.getAgentId()+"");
        }else{
            req.setAgentIds(agentIds);
        }

        Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
        List<WkCoinDetailModel> wkCoinList = lfYfykAmoutSetReadMapper.getWkCoinPayList(pagination);
        for(WkCoinDetailModel coin : wkCoinList){
            Agent agent = agentSoa.getAgent(Integer.valueOf(coin.getAgentId()));
            if(agent!=null){
                coin.setAgentName(agent.getName());
                coin.setStoreName(agent.getStoreName());
                coin.setCompanyName(agent.getFranchiseeCompanyName());
                coin.setCityName(agent.getCityName());
                coin.setAreaOrgName(agent.getAreaOrgName());
            }
        }
        PageResponse pageResponse = new PageResponse("success", 1,wkCoinList);
        pageResponse.setTotal(pagination.getTotal());
        return pageResponse;
    
    
    }

    @Override
    public Response getWkCoinGiveList(WkCoinGivelModel req, LfEmployee employee) {
        req.setPageIndex(req.getPageIndex()+1);
        Integer wpid=null;
        if(req.getCompanyId()!=null){
            wpid=req.getCompanyId();
        }else{
            wpid=null;
        }
        /*if(req.getCompanyName()!=null&&!"".equals(req.getCompanyName())){
            LfFranchiseeInfo franchisee = lfYfykAmoutSetReadMapper.selectFranchiseeByName(req.getCompanyName());
            if(franchisee!=null){
                wpid=Integer.valueOf(franchisee.getId().toString());
            }else{
                wpid=0;
            }
        }*/
        List<Integer> agentList = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSoa, lfAreaOrgReadMapper,employee, wpid);
        String agentIds="0";
        if(agentList!=null && !agentList.isEmpty()){
            for(int i=0;i<agentList.size();i++){
                if(i!=agentList.size()-1){
                    agentIds+=agentList.get(i)+",";
                }else{
                    agentIds+=agentList.get(i);
                }
            }
        }
        if(req.getAgentId()!=null){
            req.setAgentIds(req.getAgentId()+"");
        }else{
            req.setAgentIds(agentIds);
        }
        Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
        List<WkCoinGivelModel> wkCoinList = lfYfykAmoutSetReadMapper.getWkCoinGiveList(pagination);
        for(WkCoinGivelModel coin : wkCoinList){
            Agent agent = agentSoa.getAgent(Integer.valueOf(coin.getAgentId()));
            if(agent!=null){
                coin.setAgentName(agent.getName());
                coin.setStoreName(agent.getStoreName());
                coin.setCompanyName(agent.getFranchiseeCompanyName());
                coin.setCityName(agent.getCityName());
                coin.setAreaOrgName(agent.getAreaOrgName());
            }
            Employee e = agentSoa.getEmployee(Integer.valueOf(coin.getOperatorId()));
            if(e!=null){
                coin.setOperatorName(e.getName());
            }
        }
        PageResponse pageResponse = new PageResponse("success", 1,wkCoinList);
        pageResponse.setTotal(pagination.getTotal());
        return pageResponse;
    }

    @Override
    public Response getFranchiseeInfoList(WkCoinGivelModel req,LfEmployee employee) {
        req.setPageIndex(req.getPageIndex()+1);
        Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
        List<WkCoinGivelModel> companyList = lfYfykAmoutSetReadMapper.getFranchiseeInfoList(pagination);
        for(WkCoinGivelModel coin : companyList){
            List<Agent> agent = agentSoa.getAgentList(employee.getCityId()==1?null:employee.getCityId(), null, null, null, null, coin.getId());
            if(agent!=null && !agent.isEmpty()){
                coin.setAgentCount(agent.size());
            }
        }
        PageResponse pageResponse = new PageResponse("success", 1,companyList);
        pageResponse.setTotal(pagination.getTotal());
        return pageResponse;
    
    }

    @Override
    @Transactional
    public Response addByCompanySave(WkCoinGivelModel wkgive) {
        WkCoinGivelModel model =null;
        String[] companyIds = wkgive.getCompanyIds().split(",");
        List<Integer> list = new ArrayList<Integer>();
        int result=0;
        //查询所有公司下的经纪人Id
        List<DonateWkbRequest> requests=new ArrayList<DonateWkbRequest>();
        for(int i=0;i<companyIds.length;i++){
            List<Agent> agent = agentSoa.getAgentList(null, null, null, null, null, Integer.valueOf(companyIds[i]));
            if(agent!=null && !agent.isEmpty()){
                for(Agent a:agent){
                    list.add(a.getId());
                    DonateWkbRequest dwr = new DonateWkbRequest();
                    dwr.setGuestId(Long.valueOf(a.getId()));
                    dwr.setPayAmount(wkgive.getCoinNum()*100);
                    requests.add(dwr);
                }
            }
        }
        
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
        //调用pay接口，成功之后保存到本地
        Response<Boolean> res=payWXFacadeService.donateWkb(requests);
        if(res.getStatus()==1){
            //分批保存
            for(int i=0;i<list.size();i++){
                try {
                    model = new WkCoinGivelModel();
                    model.setAgentId(list.get(i));
                    model.setCoinNum(wkgive.getCoinNum());
                    model.setOperatorId(ssoClientUtil.getCurrentUser().getId());
                    model.setGiveReason(wkgive.getGiveReason());
                    lfYfykAmoutSetWriteMapper.addByCompanySave(model);
                    LfAgentAccountDetail detail = new LfAgentAccountDetail();
                    detail.setAgentId(list.get(i));
                    //wk_cz_20160315162822_502955 //生成规则 wk_zs_时间+agentId
                    String paymentOrderNo = "wk_zs_"+df.format(new Date())+"_"+list.get(i);
                    detail.setPaymentOrderNo(paymentOrderNo);
                    detail.setWuKongCoin(wkgive.getCoinNum());
                    lfYfykAmoutSetWriteMapper.insertDetail(detail);
                    result++;
                }
                catch (Exception e) {
                    throw new RuntimeException("sso获取异常",e);
                }
            }
        }
        if(result==list.size()){
            return new Response("success",1);
        }else{
            return new Response("操作失败",0);
        }    }

    @Override
    public PageResponse getAgentList(WkCoinGivelModel req,LfEmployee employee) {
        //req.setPageIndex(req.getPageIndex()+1);
        Integer wpid=null;
        if(req.getCompanyName()!=null&&!"".equals(req.getCompanyName())){
            LfFranchiseeInfo franchisee = lfYfykAmoutSetReadMapper.selectFranchiseeByName(req.getCompanyName());
            if(franchisee!=null){
                wpid=Integer.valueOf(franchisee.getId().toString());
            }else{
                wpid=0;
            }
        }
        List<Integer> agentList = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSoa, lfAreaOrgReadMapper,employee, wpid);
        String agentIds="";
        if(agentList!=null && !agentList.isEmpty()){
            for(int i=0;i<agentList.size();i++){
                if(i!=agentList.size()-1){
                    agentIds+=agentList.get(i)+",";
                }else{
                    agentIds+=agentList.get(i);
                }
            }
            req.setAgentIds(agentIds);
        }else{
            req.setAgentIds("0");
        }
        if(req.getAgentId()!=null){
            req.setAgentIds(req.getAgentId()+"");
        }
        List<Agent> agentSOAList = agentSoa.getAgentList(null,null,null,null, null, null,req.getAgentIds(),req.getMobile(),req.getCompanyName(), req.getPageIndex(), req.getPageSize());
        Integer total = agentSoa.getAgentListCount(null,null,null, null, null, null,req.getAgentIds(),req.getMobile(),req.getCompanyName());
        List<WkCoinGivelModel> giveList = new ArrayList<WkCoinGivelModel>();
        for(Agent agent:agentSOAList){
            WkCoinGivelModel model = new WkCoinGivelModel();
            model.setCompanyName(agent.getFranchiseeCompanyName());
            model.setCityName(agent.getCityName());
            model.setAreaOrgName(agent.getAreaOrgName());
            model.setStoreName(agent.getStoreName());
            model.setAgentName(agent.getName());
            model.setId(agent.getId());
            giveList.add(model);
        }
        PageResponse pageResponse = new PageResponse("success", 1,giveList);
        pageResponse.setTotal(total);
        return pageResponse;
    }

    @Override
    public Response addByAgentSave(WkCoinGivelModel wkgive) {
        WkCoinGivelModel model =null;
        List<DonateWkbRequest> requests=new ArrayList<DonateWkbRequest>();
        String[] agentIds = wkgive.getAgentIds().split(",");
        List<Integer> list = new ArrayList<Integer>();
        int result=0;
        //查询所有公司下的经纪人Id
        for(int i=0;i<agentIds.length;i++){
              list.add(Integer.valueOf(agentIds[i]));
              DonateWkbRequest dwr = new DonateWkbRequest();
              dwr.setGuestId(Long.valueOf(agentIds[i]));
              dwr.setPayAmount(wkgive.getCoinNum()*100);
              requests.add(dwr);
        }
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
        //调用pay接口，成功之后保存到本地
        Response<?> res=payWXFacadeService.donateWkb(requests);
        if(res.getStatus()==1){
            //分批保存
            for(int i=0;i<list.size();i++){
                try {
                    model = new WkCoinGivelModel();
                    model.setAgentId(list.get(i));
                    model.setCoinNum(wkgive.getCoinNum());
                    model.setOperatorId(ssoClientUtil.getCurrentUser().getId());
                    model.setGiveReason(wkgive.getGiveReason());
                    lfYfykAmoutSetWriteMapper.addByCompanySave(model);
                    LfAgentAccountDetail detail = new LfAgentAccountDetail();
                    detail.setAgentId(list.get(i));
                    //wk_cz_20160315162822_502955 //生成规则 wk_zs_时间+agentId
                    String paymentOrderNo = "wk_zs_"+df.format(new Date())+"_"+list.get(i);
                    detail.setPaymentOrderNo(paymentOrderNo);
                    detail.setWuKongCoin(wkgive.getCoinNum());
                    lfYfykAmoutSetWriteMapper.insertDetail(detail);
                    result++;
                }
                catch (Exception e) {
                    throw new RuntimeException("sso获取异常",e);
                }
            }
        }
        if(result==list.size()){
            return new Response("success",1);
        }else{
            return new Response("操作失败",0);
        }
    }

    @Override
    public List<WkCoinGiveExport> getWkCoinGiveListNotPage(WkCoinGivelModel req, LfEmployee employee) {

        Integer wpid=null;
        String agentIds="";
        if(req.getCompanyId()!=null){
            wpid=req.getCompanyId();
        }else{
            wpid=null;
        }
       /* if(req.getCompanyName()!=null&&!"".equals(req.getCompanyName())){
            LfFranchiseeInfo franchisee = lfYfykAmoutSetReadMapper.selectFranchiseeByName(req.getCompanyName());
            if(franchisee!=null){
                wpid=Integer.valueOf(franchisee.getId().toString());
            }else{
                wpid=0;
            }
        }*/
        List<Integer> agentList = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSoa, lfAreaOrgReadMapper,employee, wpid);
        if(agentList!=null && !agentList.isEmpty()){
            for(int i=0;i<agentList.size();i++){
                if(i!=agentList.size()-1){
                    agentIds+=agentList.get(i)+",";
                }else{
                    agentIds+=agentList.get(i);
                }
            }
        }
        if(agentList.size()==0&&req.getCompanyId()!=null){
            agentIds="0";
        }
        if(req.getAgentId()!=null){
            req.setAgentIds(req.getAgentId()+"");
        }else{
            req.setAgentIds(agentIds);
        }
        List<WkCoinGivelModel> wkCoinList = lfYfykAmoutSetReadMapper.getWkCoinGiveList(req);
        List<WkCoinGiveExport> list = new ArrayList<WkCoinGiveExport>();
        for(WkCoinGivelModel coin : wkCoinList){
            Agent agent = agentSoa.getAgent(Integer.valueOf(coin.getAgentId()));
            WkCoinGiveExport g = new WkCoinGiveExport();
            if(agent!=null){
                g.setAgentName(agent.getName());
                g.setStoreName(agent.getStoreName());
                g.setCompanyName(agent.getFranchiseeCompanyName());
                g.setCityName(agent.getCityName());
                g.setAreaOrgName(agent.getAreaOrgName());
            }
            Employee operator = agentSoa.getEmployee(Integer.valueOf(coin.getOperatorId()));
            if(operator!=null){
                g.setOperatorName(operator.getName());
            }
            g.setCoinNum(coin.getCoinNum());
            g.setCreateTime(coin.getCreateTime());
            g.setGiveReason(coin.getGiveReason());
            list.add(g);
        }
      
        return list;
        
    
    
    }

    @Override
    public List<WkCoinConsumeExport> getWkCoinConsumeListNotPage(WkCoinDetailModel req, LfEmployee employee) {
        Integer wpid=null;
        if(req.getCompanyId()!=null){
            wpid=req.getCompanyId();
        }else{
            wpid=null;
        }
       /* if(req.getCompanyName()!=null&&!"".equals(req.getCompanyName())){
            LfFranchiseeInfo franchisee = lfYfykAmoutSetReadMapper.selectFranchiseeByName(req.getCompanyName());
            if(franchisee!=null){
                wpid=Integer.valueOf(franchisee.getId().toString());
            }else{
                wpid=0;
            }
        }*/
        List<Integer> agentList = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSoa, lfAreaOrgReadMapper,employee, wpid);
        String agentIds="";
        if(agentList!=null && !agentList.isEmpty()){
            for(int i=0;i<agentList.size();i++){
                if(i!=agentList.size()-1){
                    agentIds+=agentList.get(i)+",";
                }else{
                    agentIds+=agentList.get(i);
                }
            }
        }
        if(agentList.size()==0&&req.getCompanyId()!=null){
            agentIds="0";
        }
        if(req.getAgentId()!=null){
            req.setAgentIds(req.getAgentId()+"");
        }else{
            req.setAgentIds(agentIds);
        }
        
        List<WkCoinConsumeExport> list = new ArrayList<WkCoinConsumeExport>();
        List<WkCoinDetailModel> wkCoinList = lfYfykAmoutSetReadMapper.getWkCoinConsumeList(req);
        for(WkCoinDetailModel coin : wkCoinList){
            WkCoinConsumeExport c = new WkCoinConsumeExport();
            Agent agent = agentSoa.getAgent(Integer.valueOf(coin.getAgentId()));
            if(agent!=null){
                c.setAgentName(agent.getName());
                c.setStoreName(agent.getStoreName());
                c.setCompanyName(agent.getFranchiseeCompanyName());
                c.setCityName(agent.getCityName());
                c.setAreaOrgName(agent.getAreaOrgName());                
            }
            c.setCreateTime(coin.getCreateTime());
            if("2".equals(coin.getBusinessType())){//  2抢单（约看）3抢单（微聊）4抢单(悬赏)
                c.setBusinessType("抢单（约看）");
            }else if("3".equals(coin.getBusinessType())){
                c.setBusinessType("抢单（微聊）");
            }else if("4".equals(coin.getBusinessType())){
                c.setBusinessType("抢单（悬赏）");
            }
            c.setPrice(coin.getPrice().replace("-", ""));
            list.add(c);
        }
        return list;
    
    }

    @Override
    public List<WkCoinPayExport> getWkCoinPayListNotPage(WkCoinDetailModel req, LfEmployee employee) {
        Integer wpid=null;
        String agentIds="";
        if(req.getCompanyId()!=null){
            wpid=req.getCompanyId();
        }else{
            wpid=null;
        }
        /*if(req.getCompanyName()!=null&&!"".equals(req.getCompanyName())){
            LfFranchiseeInfo franchisee = lfYfykAmoutSetReadMapper.selectFranchiseeByName(req.getCompanyName());
            if(franchisee!=null){
                wpid=Integer.valueOf(franchisee.getId().toString());
            }else{
                wpid=0;
            }
        }*/
        List<Integer> agentList = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSoa, lfAreaOrgReadMapper,employee, wpid);
        if(agentList!=null && !agentList.isEmpty()){
            for(int i=0;i<agentList.size();i++){
                if(i!=agentList.size()-1){
                    agentIds+=agentList.get(i)+",";
                }else{
                    agentIds+=agentList.get(i);
                }
            }
        }
        if(agentList.size()==0&&req.getCompanyId()!=null){
            agentIds="0";
        }
        if(req.getAgentId()!=null){
            req.setAgentIds(req.getAgentId()+"");
        }else{
            req.setAgentIds(agentIds);
        }
        List<WkCoinPayExport> list = new ArrayList<WkCoinPayExport>();
        List<WkCoinDetailModel> wkCoinList = lfYfykAmoutSetReadMapper.getWkCoinPayList(req);
        for(WkCoinDetailModel coin : wkCoinList){
            WkCoinPayExport p = new WkCoinPayExport();
            Agent agent = agentSoa.getAgent(Integer.valueOf(coin.getAgentId()));
            if(agent!=null){
                p.setAgentName(agent.getName());
                p.setStoreName(agent.getStoreName());
                p.setCompanyName(agent.getFranchiseeCompanyName());
                p.setCityName(agent.getCityName());
                p.setAreaOrgName(agent.getAreaOrgName());
            }
            p.setPayId(coin.getPayId());
            if("1".equals(coin.getPlatform())){// 1微信 2支付宝 3悟空币
                p.setPlatform("微信");
            }else if("2".equals(coin.getPlatform())){
                p.setPlatform("支付宝");
            }else if("3".equals(coin.getPlatform())){
                p.setPlatform("悟空币");
            }
            p.setCreateTime(coin.getCreateTime());
            p.setPrice(coin.getPrice());
            p.setWuKongCoin(coin.getWuKongCoin());
            list.add(p);
        }
        return list;
    
    
    }

    @Override
    public Response getWkCoinGiveTotal(WkCoinGivelModel req, LfEmployee employee) {
        Integer wpid=null;
        String agentIds="";
        if(req.getCompanyId()!=null){
            wpid=req.getCompanyId();
        }else{
            wpid=null;
        }
        /*if(req.getCompanyName()!=null&&!"".equals(req.getCompanyName())){
            LfFranchiseeInfo franchisee = lfYfykAmoutSetReadMapper.selectFranchiseeByName(req.getCompanyName());
            if(franchisee!=null){
                wpid=Integer.valueOf(franchisee.getId().toString());
            }else{
                wpid=-1;//设置为不存在的数字
                agentIds="0";////设置为不存在的数字
            }
        }else{
            wpid=0;
        }*/
        List<Integer> agentList = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSoa, lfAreaOrgReadMapper,employee, wpid);
        
        if(agentList!=null && !agentList.isEmpty()){
            for(int i=0;i<agentList.size();i++){
                if(i!=agentList.size()-1){
                    agentIds+=agentList.get(i)+",";
                }else{
                    agentIds+=agentList.get(i);
                }
            }
        }
        if(agentList.size()==0&&req.getCompanyId()!=null){
            agentIds="0";
        }
        if(req.getAgentId()!=null){
            req.setAgentIds(req.getAgentId()+"");
        }else{
            
            req.setAgentIds(agentIds);
        }
        Long total = Long.valueOf(lfYfykAmoutSetReadMapper.getWkCoinGiveTotal(req));
        
      
        return new Response("success",1,total);
    }

    @Override
    public Response getWkCoinPayTotal(WkCoinDetailModel req, LfEmployee employee) {
        Integer wpid=null;
        String agentIds="";
        if(req.getCompanyId()!=null){
            wpid=req.getCompanyId();
        }else{
            wpid=null;
        }
        /*if(req.getCompanyName()!=null&&!"".equals(req.getCompanyName())){
            LfFranchiseeInfo franchisee = lfYfykAmoutSetReadMapper.selectFranchiseeByName(req.getCompanyName());
            if(franchisee!=null){
                wpid=Integer.valueOf(franchisee.getId().toString());
            }else{
                wpid=-1;//设置为不存在的数字
                agentIds="0";////设置为不存在的数字
            }
        }else{
            wpid=0;
        }*/
        List<Integer> agentList = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSoa, lfAreaOrgReadMapper,employee, wpid);
        if(agentList!=null && !agentList.isEmpty()){
            for(int i=0;i<agentList.size();i++){
                if(i!=agentList.size()-1){
                    agentIds+=agentList.get(i)+",";
                }else{
                    agentIds+=agentList.get(i);
                }
            }
        }
        if(agentList.size()==0&&req.getCompanyId()!=null){
            agentIds="0";
        }
        if(req.getAgentId()!=null){
            req.setAgentIds(req.getAgentId()+"");
        }else{
            req.setAgentIds(agentIds);
        }
       Long total = Long.valueOf(lfYfykAmoutSetReadMapper.getWkCoinPayTotal(req));
        return new Response("success",1,total);
    
    }

    @Override
    public Response getWkCoinConsumeTotal(WkCoinDetailModel req, LfEmployee employee) {
        Integer wpid=null;
        String agentIds="";
        if(req.getCompanyId()!=null){
            wpid=req.getCompanyId();
        }else{
            wpid=null;
        }
        /*if(req.getCompanyName()!=null&&!"".equals(req.getCompanyName())){
            LfFranchiseeInfo franchisee = lfYfykAmoutSetReadMapper.selectFranchiseeByName(req.getCompanyName());
            if(franchisee!=null){
                wpid=Integer.valueOf(franchisee.getId().toString());
            }else{
                wpid=-1;//设置为不存在的数字
                agentIds="0";////设置为不存在的数字
            }
        }else{
            wpid=0;
        }*/
        List<Integer> agentList = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSoa, lfAreaOrgReadMapper,employee, wpid);
        if(agentList!=null && !agentList.isEmpty()){
            for(int i=0;i<agentList.size();i++){
                if(i!=agentList.size()-1){
                    agentIds+=agentList.get(i)+",";
                }else{
                    agentIds+=agentList.get(i);
                }
            }
        }
        if(agentList.size()==0&&req.getCompanyId()!=null){
            agentIds="0";
        }
        if(req.getAgentId()!=null){
            req.setAgentIds(req.getAgentId()+"");
        }else{
            req.setAgentIds(agentIds);
        }
        List<WkCoinConsumeExport> list = new ArrayList<WkCoinConsumeExport>();
        Long total = Long.valueOf(lfYfykAmoutSetReadMapper.getWkCoinConsumeTotal(req));
      
        return new Response("success",1,String.valueOf(total).replace("-", ""));
    
    }

    @Override
    public List<ComboModel> getFranchiseeListByCityId() {
        return lfYfykAmoutSetReadMapper.getFranchiseeListByCityId();
    }

    @Override
    public Response getWkCoinReportList(WkcoinReport req, LfEmployee employee) {
        req.setPageIndex(req.getPageIndex()+1);
        req.setPageSize(req.getPageSize()-1);
        Integer wpid=null;
        Agent agent=null;
        if(req.getCompanyId()!=null){
            wpid=req.getCompanyId();
        }else{
            wpid=null;
        }
        List<Integer> agentList = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSoa, lfAreaOrgReadMapper,employee, wpid);
        String agentIds="0";
        if(agentList!=null && !agentList.isEmpty()){
            for(int i=0;i<agentList.size();i++){
                if(i!=agentList.size()-1){
                    agentIds+=agentList.get(i)+",";
                }else{
                    agentIds+=agentList.get(i);
                }
            }
        }
        if(StringUtils.isNotBlank(req.getMobile())){
            agent = agentSoa.getAgent(req.getMobile());
        }
        
        
        if(req.getAgentId()!=null&& req.getMobile()!=null){
            if(agent!=null){
                if(agent.getId()!=req.getAgentId()){
                    req.setAgentIds("0");
                }else{
                    req.setAgentIds(req.getAgentId()+"");
                }
            }else{
                req.setAgentIds("0");
            }
        }else if(req.getAgentId()==null&& req.getMobile()!=null){
            if(agent!=null){
                if(!"0".equals(agentIds)&&agentList.contains(agent.getId())){
                    agentIds=agent.getId()+"";
                    req.setAgentIds(agentIds);
                }else{
                    req.setAgentIds("0");
                }
            }else{
                req.setAgentIds("0");
            }
        }else if(req.getAgentId()==null&& req.getMobile()==null){
            req.setAgentIds(agentIds);
        }else if(req.getAgentId()!=null&& req.getMobile()==null){
            req.setAgentIds(req.getAgentId()+"");
        }
        req.setOffset((req.getPageIndex()-1)*req.getPageSize());
        List<WkcoinReport> wkCoinList = wkCoinReportReadMapper.getWkCoinReportList(req);
        int total = wkCoinReportReadMapper.getWkCoinReportListTotal(req);
        WkcoinReport r = new WkcoinReport();
        double sumxc=0D;
        double sumgiveCoin=0D;
        double sumpayCoin=0D;
        double sumconsumeCoin=0D;
        for(WkcoinReport coin : wkCoinList){
            coin.setXcCoin(coin.getXcCoin()/100);
            coin.setGiveCoin(coin.getGiveCoin()/100);
            coin.setPayCoin(coin.getPayCoin()/100);
            coin.setConsumeCoin(coin.getConsumeCoin()/100);
            sumxc +=coin.getXcCoin();
            sumgiveCoin +=coin.getGiveCoin();
            sumpayCoin +=coin.getPayCoin();
            sumconsumeCoin +=coin.getConsumeCoin();
            Agent a = agentSoa.getAgent(Integer.valueOf(coin.getAgentId()));
            if(a!=null){
                coin.setAgentName(a.getName());
                coin.setStoreName(a.getStoreName());
                coin.setCompanyName(a.getFranchiseeCompanyName());
                coin.setCityName(a.getCityName());
                coin.setAreaOrgName(a.getAreaOrgName());
            }
        }
        r.setCompanyName("合计");
        r.setXcCoin(sumxc);
        r.setGiveCoin(sumgiveCoin);
        r.setPayCoin(sumpayCoin);
        r.setConsumeCoin(sumconsumeCoin);
        if(!wkCoinList.isEmpty()){
            wkCoinList.add(r);
        }
        PageResponse pageResponse = new PageResponse("success", 1,wkCoinList);
        pageResponse.setTotal(total);
        return pageResponse;
    }

    @Override
    public List<WkCoinReportExport> getWkCoinReportListNotPage(WkcoinReport req, LfEmployee employee) {
        Integer wpid=null;
        String agentIds="";
        Agent agent=null;
        if(req.getCompanyId()!=null){
            wpid=req.getCompanyId();
        }else{
            wpid=null;
        }
        List<Integer> agentList = OrgCodeUtil.getEmployeeIdsByOrgIds(req.getCityId(), req.getAreaId(), req.getStoreId(), agentSoa, lfAreaOrgReadMapper,employee, wpid);
        if(agentList!=null && !agentList.isEmpty()){
            for(int i=0;i<agentList.size();i++){
                if(i!=agentList.size()-1){
                    agentIds+=agentList.get(i)+",";
                }else{
                    agentIds+=agentList.get(i);
                }
            }
        }
       
        if(StringUtils.isNotBlank(req.getMobile())){
            agent = agentSoa.getAgent(req.getMobile());
        }
        
        if(req.getAgentId()!=null&& req.getMobile()!=null){
            if(agent!=null){
                if(agent.getId()!=req.getAgentId()){
                    req.setAgentIds("0");
                }else{
                    req.setAgentIds(req.getAgentId()+"");
                }
            }else{
                req.setAgentIds("0");
            }
        }else if(req.getAgentId()==null&& req.getMobile()!=null){
            if(agent!=null){
                if(!"0".equals(agentIds)&&agentList.contains(agent.getId())){
                    agentIds=agent.getId()+"";
                    req.setAgentIds(agentIds);
                }else{
                    req.setAgentIds("0");
                }
            }else{
                req.setAgentIds("0");
            }
        }else if(req.getAgentId()==null&& req.getMobile()==null){
            req.setAgentIds(agentIds);
        }else if(req.getAgentId()!=null&& req.getMobile()==null){
            req.setAgentIds(req.getAgentId()+"");
        }
        List<WkCoinReportExport> list = new ArrayList<WkCoinReportExport>();
        List<WkcoinReport> wkCoinList = wkCoinReportReadMapper.getWkCoinReportList(req);
        for(WkcoinReport coin : wkCoinList){
            WkCoinReportExport wre = new WkCoinReportExport();
            wre.setXcCoin(coin.getXcCoin()/100);
            wre.setGiveCoin(coin.getGiveCoin()/100);
            wre.setPayCoin(coin.getPayCoin()/100);
            wre.setConsumeCoin(coin.getConsumeCoin()/100);
            Agent a = agentSoa.getAgent(Integer.valueOf(coin.getAgentId()));
            if(a!=null){
                wre.setAgentName(a.getName());
                wre.setStoreName(a.getStoreName());
                wre.setCompanyName(a.getFranchiseeCompanyName());
                wre.setCityName(a.getCityName());
                wre.setAreaOrgName(a.getAreaOrgName());
            }
            list.add(wre);
        }
        return list;
    
    
    }
    
    
}
