package com.lifang.agentsm.dao.write;

import java.util.Map;

import com.lifang.agentsm.model.BannerSet;
import com.lifang.agentsm.model.TravelAllowanceSet;
import com.lifang.agentsm.model.TravelAllowanceSetDetail;

public interface TravelAllowanceSetWriteMapper {

    int updateStatus(Map map);

    int saveTravelDetail(TravelAllowanceSetDetail setDetail);

    int updateDetailStatus(Map map);

    int saveBannerSetUpdateImage(BannerSet set);

    int saveBannerSetAddImage(BannerSet set);

    int saveTravelData(TravelAllowanceSet set);

    void saveInsertTravelDetail(TravelAllowanceSetDetail setDetail);

}
