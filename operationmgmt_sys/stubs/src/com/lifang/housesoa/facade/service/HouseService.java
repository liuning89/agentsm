package com.lifang.housesoa.facade.service;

import java.util.List;
import com.lifang.housesoa.model.HouseDetailInfo;
import com.lifang.housesoa.model.HouseInfoRequest;
import com.lifang.housesoa.model.ReloadEnum;
import com.lifang.housesoa.model.Response;

public interface HouseService {
    Response<HouseDetailInfo> getHouseDetailInfoByHouseId(Integer houseId, String resource);
    Response houseDown(Integer houseId, Integer status, String reason, String memo);
    Response updateHouseInfoByHouseId(HouseInfoRequest request);
    void reloadMemcachedDetail(ReloadEnum reloadEnum, List<Integer> houseIds);
    Response resourceTransfer(int publishAgentIn, int franchiseeIdIn, int agentIdOut, int franchiseeIdOut);
}
