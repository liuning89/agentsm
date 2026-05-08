package com.lifang.housesoa.facade.service;

import java.util.List;
import com.lifang.housesoa.model.HouseResourceConcact;
import com.lifang.housesoa.model.Response;

public interface HouseConcatService {
    List<HouseResourceConcact> getHostInfo4SellByHouseId(Integer houseId);
    List<HouseResourceConcact> getAllHostInfoByHouseId(Integer houseId);
    Response deleteHouseSellConcat(Integer contactId);
    Response addHouseSellConcat(HouseResourceConcact concact);
    Response updateHouseSellConcatById(Integer contactId, String hostName, String hostMobile);
}
