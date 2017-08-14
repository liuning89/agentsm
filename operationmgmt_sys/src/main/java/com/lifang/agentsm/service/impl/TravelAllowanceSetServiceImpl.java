package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.dao.read.TravelAllowanceSetReadMapper;
import com.lifang.agentsm.dao.write.LfYfykAmoutSetWriteMapper;
import com.lifang.agentsm.dao.write.TravelAllowanceSetWriteMapper;
import com.lifang.agentsm.entity.HouseImage;
import com.lifang.agentsm.model.BannerSet;
import com.lifang.agentsm.model.LfYfykAmoutSetLog;
import com.lifang.agentsm.model.TravelAllowanceSet;
import com.lifang.agentsm.model.TravelAllowanceSetDetail;
import com.lifang.agentsm.service.TravelAllowanceSetService;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.agentsoa.model.Employee;
import com.lifang.housesoa.model.ReloadEnum;
import com.lifang.imgsoa.client.ImgSOAClient;
import com.lifang.imgsoa.facade.ImageService;
import com.lifang.imgsoa.model.ImageKeyObject;
import com.lifang.model.PageResponse;
import com.lifang.model.Response;
import com.lifang.sso.SsoClientUtil;

@Service
public class TravelAllowanceSetServiceImpl implements TravelAllowanceSetService {

    @Autowired
    private TravelAllowanceSetReadMapper travelAllowanceSetReadMapper;
    @Autowired
    private TravelAllowanceSetWriteMapper travelAllowanceSetWriteMapper;
    @Autowired
    private LfYfykAmoutSetWriteMapper lfYfykAmoutSetWriteMapper;
    @Autowired
    private SsoClientUtil ssoClientUtil;
    @Autowired
    private AgentSOAServer agentSoa;
    
    @Autowired
    private ImageService imgSOAClient;
    @Override
    public Response travelAllowSetList(TravelAllowanceSet req) {
        if (req.getPageIndex() != null) {
            req.setPageIndex(req.getPageIndex() + 1);
        }
        req.setOffset((req.getPageIndex()-1)*req.getPageSize());
        System.out.println(req.getCityId());
        List<TravelAllowanceSet> setList = travelAllowanceSetReadMapper.travelAllowSetList(req);
        int total= travelAllowanceSetReadMapper.travelAllowSetListTotal(req);
        if(!setList.isEmpty()){
            for(TravelAllowanceSet set:setList){
                List<TravelAllowanceSetDetail> setDetailList = travelAllowanceSetReadMapper.travelAllowSetDetailList(set.getId());
                if(!setDetailList.isEmpty()){
                    for(TravelAllowanceSetDetail d:setDetailList){
                        switch (d.getType()) {//'1.发布房源 2.实景（非现拍） 3.实景（现拍）4.视频（非现拍） 5.视频（现拍）6.好评 7.带盘 8 房源无效成功'
                            case 1:
                                set.setPublishHouse(d.getWkCoin());
                                break;
                            case 2:
                                set.setImageNotNow(d.getWkCoin());
                                break;
                            case 3:
                                set.setImageNow(d.getWkCoin());
                                break;
                            case 4:
                                set.setVideoNotNow(d.getWkCoin());
                                break;
                            case 5:
                                set.setVideoNow(d.getWkCoin());
                                break;
                            case 6:
                                set.setFavorableComment(d.getWkCoin());
                                break;
                            case 7:
                                set.setTakeHouse(d.getWkCoin());
                                break;
                            case 8:
                                set.setInvalidHouse(d.getWkCoin());
                                break;
                        }
                    }
                }
            }
        }
        PageResponse pageResponse = new PageResponse("success", 1,setList);
        pageResponse.setTotal(total);
        return pageResponse;
    
    }

    @Override
    public Response updateStatus(int status,int id) {
        Map map = new HashMap();
        map.put("status", status);
        map.put("id", id);
        int i = travelAllowanceSetWriteMapper.updateStatus(map);
        if(i>0){
            String bfV = "";
            if(status==1){
                bfV="0";
            }else{
                bfV="1";
            }
            saveLog(bfV,status+"",id,"3","修改状态");
            return new Response("success",1);
        }else{
            return new Response("fail",0);
        }
    }

    @Override
    public TravelAllowanceSet getTravelDetailByTravelId(int tid) {
        TravelAllowanceSet set = new TravelAllowanceSet();
        List<TravelAllowanceSetDetail> setDetailList = travelAllowanceSetReadMapper.travelAllowSetDetailList(set.getId());
        if(!setDetailList.isEmpty()){
            for(TravelAllowanceSetDetail d:setDetailList){
                set.setId(d.getId());
            }
        }
        return set;
    }

    @Override
    public  List<TravelAllowanceSetDetail> getDetailList(int travelId) {
        List<TravelAllowanceSetDetail> setDetailList = travelAllowanceSetReadMapper.travelAllowSetDetailList(travelId);
        if(!setDetailList.isEmpty()){
            for(TravelAllowanceSetDetail d:setDetailList){
                switch (d.getType()) {//'1.发布房源 2.实景（非现拍） 3.实景（现拍）4.视频（非现拍） 5.视频（现拍）6.好评 7.带盘 8 房源无效成功'
                    case 1:
                        d.setTypeName("发布房源");
                        break;
                    case 2:
                        d.setTypeName("实景（非现拍）");
                        break;
                    case 3:
                        d.setTypeName("实景（现拍）");
                        break;
                    case 4:
                        d.setTypeName("视频（非现拍）");
                        break;
                    case 5:
                        d.setTypeName("视频（现拍）");
                        break;
                    case 6:
                        d.setTypeName("好评");
                        break;
                    case 7:
                        d.setTypeName("带盘");
                        break;
                    case 8:
                        d.setTypeName("房源无效成功");
                        break;
                }
            }
        }
        return setDetailList;
    }

    @Override
    public Response saveTravelDetail(TravelAllowanceSetDetail setDetail) {
        TravelAllowanceSetDetail d = travelAllowanceSetReadMapper.getDetailById(setDetail.getId());
        int i = travelAllowanceSetWriteMapper.saveTravelDetail(setDetail);
        if(i>0){
            //保存操作记录
            switch (d.getType()) {//'1.发布房源 2.实景（非现拍） 3.实景（现拍）4.视频（非现拍） 5.视频（现拍）6.好评 7.带盘 8 房源无效成功'
                case 1:
                    d.setTypeName("发布房源");
                    break;
                case 2:
                    d.setTypeName("实景（非现拍）");
                    break;
                case 3:
                    d.setTypeName("实景（现拍）");
                    break;
                case 4:
                    d.setTypeName("视频（非现拍）");
                    break;
                case 5:
                    d.setTypeName("视频（现拍）");
                    break;
                case 6:
                    d.setTypeName("好评");
                    break;
                case 7:
                    d.setTypeName("带盘");
                    break;
                case 8:
                    d.setTypeName("房源无效成功");
                    break;
            }
            
            saveLog(d.getWkCoin()+"",setDetail.getWkCoin()+"",d.getTravelId(),"3","修改悟空币额="+d.getTypeName());
            return new Response("success",1);
        }else{
            return new Response("fail",0);
        }
    }

    @Override
    public Response updateDetailStatus(int id, int status) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("status", status);
        int i = travelAllowanceSetWriteMapper.updateDetailStatus(map);
        if(i>0){
            String bStatus = "";
            if(status==1){
                bStatus="0";
            }else{
                bStatus="1";
            }
            TravelAllowanceSetDetail d = travelAllowanceSetReadMapper.getDetailById(id);
            saveLog(bStatus+"",status+"",d.getTravelId(),"3","状态");
        
            return new Response("success",1);
        }else{
            return new Response("fail",0);
        }
    }

    public void saveLog(String beforeValue,String afterValue,int id,String type,String desc){
        LfYfykAmoutSetLog log =null;
        try {
            log = new LfYfykAmoutSetLog();
            log.setAfterValue(afterValue);
            log.setBeforeValue(beforeValue);
            log.setSetId(id);
            log.setContent(desc);
            log.setOperatorId(ssoClientUtil.getCurrentUser().getId());
            log.setType(type);
        }
        catch (Exception e) {
           throw new RuntimeException("SSO取用户信息出错！",e);
        }
        lfYfykAmoutSetWriteMapper.saveLog(log);
    }
    @Override
    public Response getSetLogList(TravelAllowanceSet req) {
        req.setPageIndex(req.getPageIndex()+1);
        Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
        List<LfYfykAmoutSetLog> list = travelAllowanceSetReadMapper.getFeeSetLogList(pagination);
        for(LfYfykAmoutSetLog log : list){
            Employee em =  agentSoa.getEmployee(log.getOperatorId());
            if(em!=null){
                log.setAgentName(em.getName());
            }
        }
        PageResponse pageResponse = new PageResponse("success", 1,list);
        pageResponse.setTotal(pagination.getTotal());
        return pageResponse;
    }

    @Override
    public List<BannerSet> getBannerSetList() {
        return travelAllowanceSetReadMapper.getBannerSetList();
    }

    @Override
    public Response uploadSingleFile(MultipartFile file, Integer id) {
        ImageKeyObject imgKey = null;
       
        try {
            imgKey = imgSOAClient.uploadSingleFile(file.getBytes(), 0);
            if(imgKey !=null){
                if(id==null){//新增
                    BannerSet set = new BannerSet();
                    set.setImageKey(imgKey.getKey());
                    int i = travelAllowanceSetWriteMapper.saveBannerSetAddImage(set);
                    if(i>0){
                        saveLog(null,imgKey.getKey()+"",set.getId(),"4","增加图片");
                        return new Response("success",1);
                    }
                }else{//修改
                    BannerSet set = travelAllowanceSetReadMapper.getBannerSetById(id);
                    set.setImageKey(imgKey.getKey());
                    int i = travelAllowanceSetWriteMapper.saveBannerSetUpdateImage(set);
                    if(i>0){
                        saveLog(set.getImageKey(),imgKey.getKey()+"",id,"4","修改图片");
                        return new Response("success",1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response("fail",1);
      }

    @Override
    public Response saveBannerData(BannerSet set) {
        if(null==set.getId()||set.getId()==0){//新增
            BannerSet aset =new BannerSet();
            aset.setDescription(set.getDescription());
            aset.setEndTime(set.getEndTime());
            aset.setIsShow(set.getIsShow());
            aset.setStartTime(set.getStartTime());
            aset.setH5path(set.getH5path());
            int i = travelAllowanceSetWriteMapper.saveBannerSetAddImage(aset);
            if(i>0){
                saveLog(null,set.getDescription(),aset.getId(),"4","新增描述");
                saveLog(null,set.getEndTime(),aset.getId(),"4","新增结束时间");
                saveLog(null,set.getStartTime(),aset.getId(),"4","新增起始时间");
                saveLog(null,set.getStatus()+"",aset.getId(),"4","新增状态");
                saveLog(null,set.getH5path()+"",aset.getId(),"4","新增h5路径");
//                saveLog(null,set.getH5path()+"",aset.getId(),"4","新增h5路径");
                return new Response("success",1);
            }
        }else{//修改
            BannerSet aset = travelAllowanceSetReadMapper.getBannerSetById(set.getId());
            if(set.getDescription()!=null&&aset.getDescription()!=null){
                if(!aset.getDescription().equals(set.getDescription())){
                    saveLog(aset.getDescription(),set.getDescription(),set.getId(),"4","修改描述");
                }
            }
            if(set.getEndTime()!=null&&aset.getEndTime()!=null){
                if(!aset.getEndTime().equals(set.getEndTime())){
                    saveLog(aset.getEndTime(),set.getEndTime(),set.getId(),"4","修改结束时间");
                }
            }
            if(set.getStartTime()!=null&&aset.getStartTime()!=null){
                if(!aset.getStartTime().equals(set.getStartTime())){
                    saveLog(aset.getStartTime(),set.getStartTime(),set.getId(),"4","修改起始时间");
                }
            }
            if(set.getStatus()!=0&&aset.getStatus()!=0){
                if(aset.getStatus()!=set.getStatus()){
                    saveLog(aset.getStatus()+"",set.getStatus()+"",set.getId(),"4","状态");
                }
            }
            if(set.getH5path()!=null&&aset.getH5path()!=null){
                if(!aset.getH5path().equals(set.getH5path())){
                    saveLog(aset.getH5path()+"",set.getH5path()+"",set.getId(),"4","h5路径");
                }
            }
            aset.setDescription(set.getDescription());
            aset.setEndTime(set.getEndTime());
            aset.setIsShow(set.getIsShow());
            aset.setStartTime(set.getStartTime());
            aset.setH5path(set.getH5path());
            int i = travelAllowanceSetWriteMapper.saveBannerSetUpdateImage(aset);
            if(i>0){
                return new Response("success",1);
            }
        }
        return new Response("fail",0);
    }

    @Override
    public Response showImage(int id, int isShow) {
        BannerSet aset = travelAllowanceSetReadMapper.getBannerSetById(id);
        int show ;
        if(isShow==1){
            show=0;
        }else{
            show=1;
        }
        aset.setIsShow(show);
        int i = travelAllowanceSetWriteMapper.saveBannerSetUpdateImage(aset);
        if(i>0){
            saveLog(isShow+"",show+"",id,"4","是否展示");
            return new Response("success",1);
        }
        return new Response("fail",0);
    }

    @Override
    public Response removeImage(int id) {
        BannerSet aset = travelAllowanceSetReadMapper.getBannerSetById(id);
        aset.setImageKey(null);
        int i = travelAllowanceSetWriteMapper.saveBannerSetUpdateImage(aset);
        if(i>0){
            saveLog(aset.getImageKey()+"",null+"",id,"4","删除图片");
            return new Response("success",1);
        }
        return new Response("fail",0);
    
    }

    @Override
    public int saveTravelData(TravelAllowanceSet set) {
        return travelAllowanceSetWriteMapper.saveTravelData(set);
    }

    @Override
    public void saveInsertTravelDetail(TravelAllowanceSetDetail setDetail) {
         travelAllowanceSetWriteMapper.saveInsertTravelDetail(setDetail);
    }

	@Override
	public int verifyCityIdHaveExist(Integer cityId) {
		return travelAllowanceSetReadMapper.verifyCityIdHaveExist(cityId);
	}
}
