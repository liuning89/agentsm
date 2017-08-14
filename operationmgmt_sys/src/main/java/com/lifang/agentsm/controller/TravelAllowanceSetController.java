package com.lifang.agentsm.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.model.BannerSet;
import com.lifang.agentsm.model.TravelAllowanceSet;
import com.lifang.agentsm.model.TravelAllowanceSetDetail;
import com.lifang.agentsm.service.TravelAllowanceSetService;
import com.lifang.imgsoa.facade.ImageService;
import com.lifang.imgsoa.model.ImageKeyObject;
import com.lifang.model.Response;

/**
 * 
 *
 * @author   ln
 * @Date	 2016年4月5日		上午11:22:54
 *
 * @see 行程补贴配置
 */
@RequestMapping(value="travelallow")
@Controller
public class TravelAllowanceSetController extends BaseController {

    @Autowired
    private TravelAllowanceSetService travelAllowanceSetService;
    @Autowired
    private ImageService imageSOA;
    //页面加载
    @RequestMapping(value = "travelSetPage")
    public String travelSetPage(){
        return "travelallowanceset/travel_allowance_set";
    }
  
    //数据加载
    @RequestMapping(value="travelAllowSetList")
    @ResponseBody
    public Response travelAllowSetList(TravelAllowanceSet req){
        return travelAllowanceSetService.travelAllowSetList(req);
    }
    
    //修改状态
    @RequestMapping(value="updateStatus")
    @ResponseBody
    public Response updateStatus(int status,int id){
        int statusValue = status==1?0:1;
        return travelAllowanceSetService.updateStatus(statusValue,id);
    }
    
    //修改页面
    @RequestMapping(value="updateDetailPage")
    public String updateDetailPage(int id,ModelMap map){
        map.put("id", id);
        return "travelallowanceset/travel_allowance_detail";
    }
    //数据加载
    @RequestMapping(value="getDetailList")
    @ResponseBody
    public  List<TravelAllowanceSetDetail> getDetailList(int id){
        return travelAllowanceSetService.getDetailList(id);
    }
    //页面保存
    @RequestMapping(value="saveTravelDetail")
    @ResponseBody
    public Response saveTravelDetail(@RequestParam("data") String data){
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList rows;
        Response r= null;
        try {
            rows = objectMapper.readValue(data, ArrayList.class);
            for(int i=0,l=rows.size(); i<l; i++){
                HashMap row = (HashMap)rows.get(i);
                TravelAllowanceSetDetail setDetail = new TravelAllowanceSetDetail();
                String state = row.get("_state") != null ? row.get("_state").toString() : "";
               if (state.equals("modified") || state.equals(""))    //更新：_state为空，或modified
                {
                   setDetail.setId(Integer.valueOf(row.get("id").toString()));
                   setDetail.setWkCoin(Integer.valueOf(row.get("wkCoin").toString()));
                    r=travelAllowanceSetService.saveTravelDetail(setDetail);
                }
            }
        }
        catch (Exception e) {
            logger.info("解析出错",e);
        }
       
       return r;
   }
   //详细页面更改状态
    @RequestMapping(value="updateDetailStatus")
    @ResponseBody
    public Response updateDetailStatus(int id,int status){
        return travelAllowanceSetService.updateDetailStatus(id,status);
    }
    
    //日志
    @RequestMapping("/lookUpdateRecordList")
    @ResponseBody
    public Response lookUpdateRecordList(TravelAllowanceSet req){
        return travelAllowanceSetService.getSetLogList(req);
    }
    
    //日志页面
    @RequestMapping("/lookUpdateRecordPage")
    public String lookUpdateRecordPage(int id,ModelMap map){
        map.put("id",id);
        return "travelallowanceset/travel_set_log";
    }
    
    //banner工作台配置
    @RequestMapping("/bannerSetPage")
    public String bannerSetPage(ModelMap map){
        List<BannerSet> setList = travelAllowanceSetService.getBannerSetList();
        map.addAttribute("setList", setList);
        return "travelallowanceset/banner_set";
    }
    
    @RequestMapping(value = "uploadBannerSetImage", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object uploadHouseImage(HttpServletRequest request,HttpServletResponse  response)
    {
       //转型为MultipartHttpRequest(重点的所在)  
        MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
        //  获得第1张图片（根据前台的name名称得到上传的文件）   
        MultipartFile imgFile1  =  multipartRequest.getFile("file");
        Integer id =null;
        if(StringUtils.isNotEmpty(request.getParameter("id").toString())){
            id = Integer.parseInt(request.getParameter("id"));
        }
        return travelAllowanceSetService.uploadSingleFile(imgFile1, id);
    }
    @RequestMapping(value = "saveBannerData")
    @ResponseBody
    public Response saveBannerData(BannerSet set){
        return travelAllowanceSetService.saveBannerData(set);
    }
    
    @RequestMapping(value = "showImage")
    @ResponseBody
    public Response showImage(int id,int isShow){
        return travelAllowanceSetService.showImage(id,isShow);
    }
    
    @RequestMapping(value = "removeImage")
    @ResponseBody
    public Response removeImage(int id){
        return travelAllowanceSetService.removeImage(id);

    }
    
    @RequestMapping("/downloadImageKeyObject")
    public void downloadImage(@RequestParam String imgKey, @RequestParam String type, HttpServletResponse response) {
        try {
            ImageKeyObject imageKeyObject = imageSOA.getImageKeyObject(imgKey);
            if (imageKeyObject != null) {
                response.setContentType("application/x-msdownload");
                String fileName = new Date().getTime()+".jpg";
                response.setHeader("Content-Disposition","attachment; filename="+ fileName);
                HttpClient client = new HttpClient();
                String url = imageKeyObject.getOriginal();
                if ("water_cl".equals(type)) {
                    url = imageKeyObject.getWater_cl();
                } else if ("water_ml".equals(type)) {
                    url = imageKeyObject.getWater_ml();
                } else if ("water_al".equals(type)) {
                    url = imageKeyObject.getWater_al();
                } else if ("water_dl".equals(type)) {
                    url = imageKeyObject.getWater_dl();
                } else if ("water_dj".equals(type)) {
                    url = imageKeyObject.getWater_dj();
                }
                GetMethod method = new GetMethod(url);
                int statusCode = client.executeMethod(method);
                if (statusCode == HttpStatus.SC_OK) {
                    InputStream input = method.getResponseBodyAsStream();
                    BufferedInputStream in = new BufferedInputStream(input);
                    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                    byte[] buffer = new byte[1024];
                    int temp = -1;
                    while ((temp = in.read(buffer)) != -1) {
                        out.write(buffer, 0, temp);
                        out.flush();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
