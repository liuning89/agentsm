package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.dao.read.ReportReadMapper;
import com.lifang.agentsm.model.EstateInfoAboutData;
import com.lifang.agentsm.service.ReportService;
import com.lifang.model.PageResponse;
import com.lifang.model.Response;

@Service
public class ReportServiceImpl  implements ReportService {

    @Autowired
    private ReportReadMapper reportReadMapper;
    @Override
    public Response getAllData(EstateInfoAboutData req) throws Exception {
        //取板块和小区
        EstateInfoAboutData pagReq = new EstateInfoAboutData();
        pagReq= (EstateInfoAboutData) req.clone();
        if (pagReq.getPageIndex() != null) {
            pagReq.setPageIndex(pagReq.getPageIndex() + 1);
        }
        Pagination pagination = new Pagination(pagReq, "pageSize", "pageIndex");
        Map paraMap = new HashMap();
        paraMap.put("pageIndex",req.getPageIndex() * req.getPageSize());
        paraMap.put("pageSize", req.getPageSize());
        paraMap.put("cityId",req.getCityId());
        paraMap.put("districtId", req.getDistrictId());
        paraMap.put("townId", req.getTownId());
        paraMap.put("estateName", req.getEstateName());

        List<EstateInfoAboutData>  allData = new ArrayList<EstateInfoAboutData>();
        List<EstateInfoAboutData>  listData = reportReadMapper.getEstateAndDic(pagination);
        List<EstateInfoAboutData>  yxkcList= reportReadMapper.getyxkcNum(paraMap); //有效库存数
        List<EstateInfoAboutData>  llList= reportReadMapper.getllNum(paraMap);//浏览数
        List<EstateInfoAboutData>  sjList= reportReadMapper.getsjNum(paraMap);//实景数
        List<EstateInfoAboutData>  sxList= reportReadMapper.getsxNum(paraMap);//速销数
        List<EstateInfoAboutData>  ysList= reportReadMapper.getysNum(paraMap);//钥匙数
        List<EstateInfoAboutData>  msList= reportReadMapper.getMsNum(paraMap);//描述数
        List<EstateInfoAboutData>  dtList= reportReadMapper.getDtNum(paraMap);//店推数
        List<EstateInfoAboutData>  openList= reportReadMapper.getOpenNum(paraMap);//openHouse
        List<EstateInfoAboutData>  dkList= reportReadMapper.getDkNum(paraMap);//带看数
        List<EstateInfoAboutData>  gjList= reportReadMapper.getGjNum(paraMap);//跟进数
        List<EstateInfoAboutData>  scList= reportReadMapper.getScNum(paraMap);//收藏数
        System.out.println(scList.size());
        for(int i=0;i<listData.size();i++){
            EstateInfoAboutData data = listData.get(i);
            data.setYxkcNum(yxkcList.get(i).getYxkcNum());
           data.setLlNum(llList.get(i).getLlNum()==null?"0":llList.get(i).getLlNum());
           data.setSjNum(sjList.get(i).getSjNum());
           data.setSxNum(sxList.get(i).getSxNum());
           data.setYsNum(ysList.get(i).getYsNum());
           data.setMsNum(msList.get(i).getMsNum());
           data.setDtNum(dtList.get(i).getDtNum());
           data.setOpenHouse(openList.get(i).getOpenHouse());
           data.setDkNum(dkList.get(i).getDkNum());
           data.setGjNum(gjList.get(i).getGjNum());
           data.setScNum(scList.get(i).getScNum()==null?"0":scList.get(i).getScNum());
            Double totalScore=Integer.valueOf(data.getYxkcNum())+Integer.valueOf(data.getSjNum())+Integer.valueOf(data.getSxNum())*10
                    +Integer.valueOf(data.getDkNum())+Integer.valueOf(data.getDtNum())*0.5+Integer.valueOf(data.getLlNum())*0.5+
                    Integer.valueOf(data.getMsNum())*0.5+Integer.valueOf(data.getOpenHouse())*0.5+Integer.valueOf(data.getGjNum())*0.5+
                    Integer.valueOf(data.getScNum())*0.5;
            data.setTotalScore(totalScore);
           allData.add(data);
        }
        PageResponse response = new PageResponse("success", 1, allData);
        response.setTotal(pagination.getTotal());
        return response;
    }
    
    

    @Override
    public List<EstateInfoAboutData> getAllDataAndNotPage(EstateInfoAboutData req) {
        Map paraMap = new HashMap();
        paraMap.put("cityId",req.getCityId());
        paraMap.put("districtId", req.getDistrictId());
        paraMap.put("townId", req.getTownId());
        paraMap.put("estateName", req.getEstateName());
        paraMap.put("startTime",req.getStartTime());
        paraMap.put("endTime",req.getEndTime());
        List<EstateInfoAboutData>  allData = new ArrayList<EstateInfoAboutData>();
        List<EstateInfoAboutData>  listData =  reportReadMapper.getEstateAndDic(paraMap);
        List<EstateInfoAboutData>  yxkcList= reportReadMapper.getyxkcNum(paraMap); //有效库存数
        List<EstateInfoAboutData>  llList= reportReadMapper.getllNum(paraMap);//浏览数
        List<EstateInfoAboutData>  sjList= reportReadMapper.getsjNum(paraMap);//实景数
        List<EstateInfoAboutData>  sxList= reportReadMapper.getsxNum(paraMap);//速销数
        List<EstateInfoAboutData>  ysList= reportReadMapper.getysNum(paraMap);//钥匙数
        List<EstateInfoAboutData>  msList= reportReadMapper.getMsNum(paraMap);//描述数
        List<EstateInfoAboutData>  dtList= reportReadMapper.getDtNum(paraMap);//店推数
        List<EstateInfoAboutData>  openList= reportReadMapper.getOpenNum(paraMap);//openHouse
        List<EstateInfoAboutData>  dkList= reportReadMapper.getDkNum(paraMap);//带看数
        List<EstateInfoAboutData>  gjList= reportReadMapper.getGjNum(paraMap);//跟进数
        List<EstateInfoAboutData>  scList= reportReadMapper.getScNum(paraMap);//收藏数
        System.out.println(scList.size());
        for(int i=0;i<listData.size();i++){
            EstateInfoAboutData data = listData.get(i);
            data.setYxkcNum(yxkcList.get(i).getYxkcNum());
           data.setLlNum(llList.get(i).getLlNum()==null?"0":llList.get(i).getLlNum());
           data.setSjNum(sjList.get(i).getSjNum());
           data.setSxNum(sxList.get(i).getSxNum());
           data.setYsNum(ysList.get(i).getYsNum());
           data.setMsNum(msList.get(i).getMsNum());
           data.setDtNum(dtList.get(i).getDtNum());
           data.setOpenHouse(openList.get(i).getOpenHouse());
           data.setDkNum(dkList.get(i).getDkNum());
           data.setGjNum(gjList.get(i).getGjNum());
           data.setScNum(scList.get(i).getScNum()==null?"0":scList.get(i).getScNum());
           allData.add(data);
        }
        return allData;
    }
}
