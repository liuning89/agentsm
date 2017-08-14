package com.lifang.agentsm.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifang.agentsm.dao.read.LfUserappHomeImgReadMapper;
import com.lifang.agentsm.dao.write.LfUserappHomeImgWriteMapper;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.entity.LfUserappHomeImg;
import com.lifang.agentsm.service.UserAppService;
import com.lifang.agentsm.utils.ImageUtils;
import com.lifang.imgsoa.facade.ImageService;
import com.lifang.imgsoa.model.ImageKeyObject;

@Service
public class UserAppServiceImpl implements UserAppService {

    @Autowired
    private LfUserappHomeImgReadMapper lfUserappHomeImgReadMapper;
    
    @Autowired
    private LfUserappHomeImgWriteMapper lfUserappHomeImgWriteMapper;
    
    @Autowired
    private ImageService imgSOAClient;

    @Override
    public LfUserappHomeImg getUserAppTopImg() {
        return lfUserappHomeImgReadMapper.selectByTypeLimitOne(1);
    }
    
    @Override
    public int addAndUpdateUserAppTopImg(byte[] img, LfEmployee employee, String url) {
        
        //如果数据存在那么更改数据
        LfUserappHomeImg homeImgAdd = lfUserappHomeImgReadMapper.selectByTypeLimitOne(1);
        if(homeImgAdd == null)
        {
            homeImgAdd = new LfUserappHomeImg();
        }
        else
        {
            //更新现有top为不可用
            LfUserappHomeImg homeImgParam = new LfUserappHomeImg();
            homeImgParam.setStauts(0);
            homeImgParam.setType(1);
            lfUserappHomeImgWriteMapper.updateByType(homeImgParam);
        }
        
        //上传并添加数据
        if(img != null)
        {
            ImageKeyObject imgObj =  uploadImg(img);
            String key  = imgObj.getKey();
            homeImgAdd.setImgKey(key);
        }
        
        homeImgAdd.setId(null);
        homeImgAdd.setType(1);
        homeImgAdd.setStauts(1);
        homeImgAdd.setCreateTime(new Date());
        homeImgAdd.setCreateBy(employee.getId());
        
        if(url != null && url != "")
        {
            homeImgAdd.setUrl(url);
        }
        
        //url和key不允许为空
        if(homeImgAdd.getUrl() == null)
        {
            homeImgAdd.setUrl("");;
        }
        
        if(homeImgAdd.getImgKey() == null)
        {
            homeImgAdd.setImgKey("");
        }
        
        return lfUserappHomeImgWriteMapper.insertSelective(homeImgAdd);
        
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
    public LfUserappHomeImg selectByTypeLimitOne(int type) {
        return lfUserappHomeImgReadMapper.selectByTypeLimitOne(type);
    }

}
