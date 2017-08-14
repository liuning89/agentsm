package com.lifang.agentsm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lifang.agentsm.dao.read.LfAgentActivityReadMapper;
import com.lifang.agentsm.dao.write.LfAgentActivityWriteMapper;
import com.lifang.agentsm.entity.LfAgentActivity;
import com.lifang.agentsm.service.MarketActivityService;
import com.lifang.agentsm.utils.ImageUtils;
import com.lifang.imgsoa.facade.ImageService;
import com.lifang.imgsoa.model.ImageKeyObject;
import com.lifang.utils.DateUtil;


/**
 * 
* @ClassName:  
* @Description: TODO(活动管理service) 
* @author luogq
* @date 2015年9月6日 下午2:38:55 
*
 */
@Service
public class MarketActivityServiceImpl implements MarketActivityService {
	@Autowired
	LfAgentActivityReadMapper lfaaRead;
	@Autowired
	private ImageService imgSOAClient;
	@Autowired
	private LfAgentActivityWriteMapper lfaaWrite;
	@Override
	public List<LfAgentActivity> selectByList() {
		List<LfAgentActivity> laaList = lfaaRead.selectByList();
		
		if(laaList != null && laaList.size() > 0){
			 //获取图像地址
			for(LfAgentActivity lf : laaList){
				if(lf.getActivityImgUrl() != null){
					ImageKeyObject obj = ImageUtils.getPublicUrlByKey(imgSOAClient, lf.getActivityImgUrl());
		            if (obj != null)
		            {
			            lf.setActivityImgUrl(obj.getOriginal());
			        }
				}
			}
		}
		
		return laaList;
	}
	@Override
	@Transactional
	public int addAndEditUserAppTopUrl(Map<String, Object> pars) {
		//id,url
		//根据id查找出对象.
		LfAgentActivity laa = lfaaRead.findById(pars);
		
		if(laa != null){
			
			//将数据改成无效
//			lfaaWrite.updateLfAgentActivity(pars); 
//			//新插入一条数据,将原来的数据插入到新的数据
			String url = pars.get("url")+"";
			if(!"null".equals(url) && !"".equals(url) && url != null){
				laa.setActivityLinkUrl(url);
			}
			if(!"null".equals(pars.get("date")) && !"".equals(pars.get("date")) && pars.get("date") != null){
				laa.setEndTime(DateUtil.stringToDate(pars.get("date")+"","yyyy-MM-dd HH:mm:ss"));
			}
			String activityName = pars.get("activityName")+"";
			if(!"null".equals(activityName) && !"".equals(activityName) && activityName != null){
				laa.setActivityName(activityName);
			}
//			lfaaWrite.insertAgentActivity(laa);
			lfaaWrite.updateByPrimaryKeySelective(laa);
			
		}else{
			
			LfAgentActivity la = new LfAgentActivity();
			la.setActivityName(pars.get("activityName")+""); 
			la.setStatus(1);
			la.setEndTime(DateUtil.stringToDate(pars.get("date")+"","yyyy-MM-dd HH:mm:ss"));
			la.setActivityLinkUrl(pars.get("url")+"");
			
			la.setActivityImgUrl("");
			
			lfaaWrite.insertAgentActivity(la);
		}
		return 0;
	}
	@Override
	public int addAndUpdateUserAppTopImg(byte[] img, Long id) {
		LfAgentActivity la = new LfAgentActivity();
		//上传并添加数据
        if(img != null)
        {
            ImageKeyObject imgObj =  uploadImg(img);
            String key  = imgObj.getKey();
            la.setActivityImgUrl(key);
        }
        la.setId(id);
		lfaaWrite.updateAgentActivity(la);
		return 0;
	}

    /***
     * 
    * @Title: uploadImg 
    * @Description: 上传图片
    * @param @param img
    * @param @return    设定文件 
    * @return ImageKeyObject    返回类型 
    * @throws
     */
    private ImageKeyObject uploadImg(byte[] img) {
        ImageKeyObject imgObj = null;
        if (img != null && img.length > 0)
            imgObj = ImageUtils.uploadImgToPublicBucket(imgSOAClient, img);
        return imgObj;
    }
	@Override
	public int updateOut(Map<String, Object> pars) {
		lfaaWrite.updateLfAgentActivity(pars);
		return 0;
	}
}
