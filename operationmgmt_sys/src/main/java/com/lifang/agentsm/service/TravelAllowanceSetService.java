package com.lifang.agentsm.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.lifang.agentsm.model.BannerSet;
import com.lifang.agentsm.model.TravelAllowanceSet;
import com.lifang.agentsm.model.TravelAllowanceSetDetail;
import com.lifang.model.Response;

public interface TravelAllowanceSetService {

    Response travelAllowSetList(TravelAllowanceSet req);

    Response updateStatus(int status,int id);

    TravelAllowanceSet getTravelDetailByTravelId(int tid);

    List<TravelAllowanceSetDetail> getDetailList(int id);

    Response saveTravelDetail(TravelAllowanceSetDetail setDetail);

    Response updateDetailStatus(int id, int status);

    Response getSetLogList(TravelAllowanceSet req);

    List<BannerSet> getBannerSetList();

    Object uploadSingleFile(MultipartFile imgFile1, Integer id);

    Response saveBannerData(BannerSet set);


    Response showImage(int id, int isShow);

    Response removeImage(int id);

    int saveTravelData(TravelAllowanceSet set);

    void saveInsertTravelDetail(TravelAllowanceSetDetail setDetail);
    
    int verifyCityIdHaveExist(Integer cityId);
}
