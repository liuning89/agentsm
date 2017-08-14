package com.lifang.agentsm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.log4j.Log4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lifang.agentsm.base.controller.BaseController;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.model.EstateInfoAboutData;
import com.lifang.agentsm.service.ReportService;
import com.lifang.agentsm.utils.ViewExcel;
import com.lifang.model.Response;
/**
 * 
 * Function: 楼盘信息数据显示
 *
 * @author   ln
 * @Date	 2015年11月2日		下午2:08:46
 *
 * @see
 */

@Controller
@RequestMapping("/report")
@Log4j
public class ReportController extends BaseController {
    @Autowired
    private ReportService reportService;

    @RequestMapping("/getData")
    @ResponseBody
    public  Response  getAllData(EstateInfoAboutData req,HttpServletRequest request) throws Exception{
        LfEmployee lf;
        Integer city_Id =0;
        try {
            lf = getLoginEmployeeInfo(request.getSession());
            city_Id = lf.getCityId();
            if(req.getCityId()==null){
                req.setCityId(String.valueOf(city_Id));
            }
            if ("1".equals(req.getCityId())) {
                req.setCityId(null);
            }
           
           
        } catch (Exception e) {
            log.error("【获取城市列表出错：】", e);
            e.printStackTrace();
        }
        
        return reportService.getAllData(req);
    }
    
    @RequestMapping("/export")
    public ModelAndView  exportData(EstateInfoAboutData req,ModelMap model, HttpServletRequest request){
        LfEmployee lf;
        Integer city_Id =0;
        try {
            lf = getLoginEmployeeInfo(request.getSession());
            city_Id = lf.getCityId();
            if(req.getCityId()==null){
                req.setCityId(String.valueOf(city_Id));
            }
            if ("1".equals(req.getCityId())) {
                req.setCityId(null);
            }
            
        } catch (Exception e) {
            log.error("【获取城市列表出错：】", e);
            e.printStackTrace();
        }
        List<EstateInfoAboutData> allData =  reportService.getAllDataAndNotPage(req);
        ViewExcel viewExcel = new ViewExcel(allData);    
        return new ModelAndView(viewExcel, model); 
    }
    
}
