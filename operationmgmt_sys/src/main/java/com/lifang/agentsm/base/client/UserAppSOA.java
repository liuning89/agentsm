package com.lifang.agentsm.base.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leo.jackson.JsonTool;
import com.lifang.agentsm.entity.LfUserappHomeImg;
import com.lifang.client.BaseClient;


public class UserAppSOA extends BaseClient {

    public UserAppSOA(String serverURL) {
        super(serverURL);
        setActionSuffix(".out");
    }
    
    public UserAppSOA(String serverURL,int soTimeout) {
        super(serverURL, soTimeout);
        setActionSuffix(".out");
    }
    
    // 获取首页的信息
    public List<LfUserappHomeImg>  getUserAppTopImg() {
        Map<String, Object> map = new HashMap<String, Object>();
        String resultJson = httpPost(map,"userApp/getUserAppTopImg");
        System.out.println(resultJson);
        LfUserappHomeImg img = JsonTool.readValue(resultJson, LfUserappHomeImg.class);
        List<LfUserappHomeImg> imgs = new ArrayList<LfUserappHomeImg>();
        imgs.add(img);
        return imgs;
    }
    
    
    public static void main(String[] args) {
        UserAppSOA soa = new UserAppSOA("http://10.0.18.192:8118/agentsm/");
        soa.setApp("test");
        soa.getUserAppTopImg();
    }
     
}
