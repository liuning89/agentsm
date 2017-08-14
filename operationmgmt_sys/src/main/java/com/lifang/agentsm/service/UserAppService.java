package com.lifang.agentsm.service;

import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.entity.LfUserappHomeImg;

public interface UserAppService {
    public LfUserappHomeImg getUserAppTopImg();
    
    public int addAndUpdateUserAppTopImg(byte[] img, LfEmployee employee, String url);
    
    public LfUserappHomeImg selectByTypeLimitOne(int type);
}
