package com.lifang.agentsm.dao.read;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.model.BannerSet;
import com.lifang.agentsm.model.LfYfykAmoutSetLog;
import com.lifang.agentsm.model.TravelAllowanceSet;
import com.lifang.agentsm.model.TravelAllowanceSetDetail;

public interface TravelAllowanceSetReadMapper {

    List<TravelAllowanceSet> travelAllowSetList(TravelAllowanceSet req);

    List<TravelAllowanceSetDetail> travelAllowSetDetailList(int travelId);

    TravelAllowanceSetDetail getDetailById(int id);

    List<LfYfykAmoutSetLog> getFeeSetLogList(Pagination pagination);

    List<BannerSet> getBannerSetList();

    BannerSet getBannerSetById(int id);

    int travelAllowSetListTotal(TravelAllowanceSet req);

    @Select("SELECT COUNT(*) FROM lf_travel_allowance_set WHERE cityId = #{cityId}")
    int verifyCityIdHaveExist(@Param("cityId")Integer cityId);
}
